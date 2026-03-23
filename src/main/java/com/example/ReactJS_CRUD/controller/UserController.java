package com.example.ReactJS_CRUD.controller;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ReactJS_CRUD.Entity.EmployeeMaster;
import com.example.ReactJS_CRUD.Entity.UserMaster;
import com.example.ReactJS_CRUD.config.ApiResponse;
import com.example.ReactJS_CRUD.dto.UserDTO;
import com.example.ReactJS_CRUD.dto.UserLoginDTO;
import com.example.ReactJS_CRUD.msgconfig.OtpService;
import com.example.ReactJS_CRUD.security.JwtUtil;
import com.example.ReactJS_CRUD.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService service;
    
    @Autowired
    private OtpService serviceOTP;
    
    
    @Autowired
    private JwtUtil jwtUtil; // ✅ Inject the component
    

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Map<String, Object>>> login(@RequestBody UserLoginDTO user)
    {
        try 
        {
            // Validate user credentials
            UserMaster dbUser = service.loginUser(user.getMobileno(), user.getPassword());

            Map<String, Object> responseData = new LinkedHashMap<>();
            
            
            if (dbUser != null)
            {
                // Generate JWT token
                String token = jwtUtil.generateToken(dbUser.getMobileno());

                responseData.put("token", token);
                responseData.put("empId", dbUser.getId());
                responseData.put("roleId", dbUser.getRole().getRoleId());
                responseData.put("roleName", dbUser.getRole().getRoleName());

                ApiResponse<Map<String, Object>> response = new ApiResponse<>(200, "Login successful", responseData);
                return ResponseEntity.ok(response);
            }

            // Check EmployeeMaster login
            EmployeeMaster empMaster = service.loginEmployee(user.getMobileno(), user.getPassword());

            if (empMaster != null) 
            {
                String token = jwtUtil.generateToken(empMaster.getPhoneNumber());

                responseData.put("token", token);
                responseData.put("empId", empMaster.getId());
                responseData.put("roleId", empMaster.getRole().getRoleId());
                responseData.put("roleName", empMaster.getRole().getRoleName());

                ApiResponse<Map<String, Object>> response = new ApiResponse<>(200, "Login successful", responseData);
                return ResponseEntity.ok(response);
            }

            // Invalid credentials
            ApiResponse<Map<String, Object>> response = new ApiResponse<>(401, "Invalid Mobile or Password", null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);

        }
        catch (Exception e)
        {
            // Catch all other server errors (avoid exposing stack trace)
            ApiResponse<Map<String, Object>> response = new ApiResponse<>(500, "Internal Server Error", null);
            // Log the exception internally
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


 
    
    
    // CREATE USER
    @PostMapping("/create")
    public ResponseEntity<UserMaster> createUser(@RequestBody UserMaster user){

        logger.info("Creating new user with username: {}", user.getsetMobileNo());

        UserMaster savedUser = service.save(user);

        logger.info("User created successfully with ID: {}", savedUser.getId());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedUser);
    }

    // GET ALL USERS (Pagination)
    @GetMapping
    public ResponseEntity<?> getAllUsers(
            @RequestParam(defaultValue="0") int page,
            @RequestParam(defaultValue="10") int size){

        logger.info("Fetching all users. Page: {}, Size: {}", page, size);

        Page<UserDTO> users = service.getAll(page,size);
        System.out.println(users.getContent());
        if(users.isEmpty()){
            logger.warn("No users found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No Users Found");
        }

        logger.info("Users fetched successfully");
        return ResponseEntity.ok(users);
    }

    // GET USER BY ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id)
    {

        logger.info("Fetching user with ID: {}", id);

        Optional<UserDTO> user = service.getByIdUser(id);

        if(user.isPresent()){
            logger.info("User found with ID: {}", id);
            return ResponseEntity.ok(user.get());
        }

        logger.warn("User not found with ID: {}", id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("User Not Found with ID : "+id);
    }

 // UPDATE USER (Partial Update)
    @PatchMapping("/{id}")  // Use PATCH instead of PUT
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserMaster user) 
    {

        logger.info("Partially updating user with ID: {}", id);

        UserMaster updatedUser;
        try 
        {
            updatedUser = service.update(id, user); // partial update handled in service
        }
        catch (RuntimeException e) 
        {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

        // Convert to DTO for response
        UserDTO dto = new UserDTO(
            updatedUser.getId(),
            updatedUser.getsetMobileNo(),
            updatedUser.getEmail(),
            updatedUser.getRole() != null ? updatedUser.getRole().getRoleName() : null,
            updatedUser.getStatus(),
            updatedUser.getCreatedDate(),
            updatedUser.getUpdatedDate(),
            updatedUser.getName(),
            user.getPassword(),
            user.getRole().getRoleId()
        );

        logger.info("User partially updated successfully with ID: {}", id);

        return ResponseEntity.ok(dto);
    }
    
    
    // DELETE USER
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){

        logger.info("Deleting user with ID: {}", id);

        Optional<UserDTO> user = service.getByIdUser(id);

        if(user.isEmpty()){
            logger.warn("User not found for deletion with ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User Not Found with ID : "+id);
        }

        service.delete(id);

        logger.info("User deleted successfully with ID: {}", id);

        return ResponseEntity.ok("User Deleted Successfully");
    }

    // SEARCH USER (Pagination)
    @GetMapping("/search")
    public ResponseEntity<?> searchUser(
            @RequestParam String username,
            @RequestParam(defaultValue="0") int page,
            @RequestParam(defaultValue="5") int size){

        logger.info("Searching users with username: {}, Page: {}, Size: {}", username, page, size);

        Page<UserDTO> users = service.searchUser(username,page,size);

        if(users.isEmpty()){
            logger.warn("No users found with username: {}", username);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No User Found with username : "+username);
        }

        logger.info("Users found for search username: {}", username);
        return ResponseEntity.ok(users);
    }
    
    
    /*
     ===============================================================
     				OTP LOGIN API
	================================================================

     				
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO user) 
    {

        UserMaster dbUser = service.login(user.getMobileno(), user.getPassword());

        if (dbUser != null) {

            boolean otpSent = serviceOTP.sendOtp(user.getMobileno());

            if (!otpSent) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("OTP sending failed");
            }

            Map<String,Object> response = new HashMap<>();
            response.put("message", "Credentials verified. OTP sent to mobile.");

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Invalid Mobile or Password");
    }
    
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody OtpDTO otpRequest) 
    {

        boolean verified = serviceOTP.verifyOtpWithMessageCentral(
                otpRequest.getMobileno(),
                otpRequest.getOtp()
        );

        if (verified) {

            String token = jwtUtil.generateToken(otpRequest.getMobileno());

            Map<String,Object> response = new HashMap<>();
            response.put("message", "OTP verified successfully");
            response.put("token", token);

            return ResponseEntity.ok(response);

        } else {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid or expired OTP");
        }
    }
    
    */
    
    
}