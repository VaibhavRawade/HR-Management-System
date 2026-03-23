package com.example.ReactJS_CRUD.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import com.example.ReactJS_CRUD.Entity.EmployeeMaster;
import com.example.ReactJS_CRUD.Entity.UserMaster;
import com.example.ReactJS_CRUD.dto.UserDTO;
import com.example.ReactJS_CRUD.repository.EmployeeRepository;
import com.example.ReactJS_CRUD.repository.UserRepository;
import com.example.ReactJS_CRUD.security.JwtUtil;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;
    
    @Autowired
    private EmployeeRepository empRepo;
    
    @Autowired
    private JwtUtil jwtUtil;

    // LOGIN
    public UserMaster loginUser(String mobile, String password) 
    {
        UserMaster user = repo.findByMobilenoAndPassword(mobile, password);
        System.out.println("USER--->"+mobile+" "+password);
        return user; // return user if password is correct
    } 
    
    public EmployeeMaster loginEmployee(String mobile, String password) 
    {
    	EmployeeMaster emp = empRepo.findByPhoneNumberAndPassword(mobile, password);
        System.out.println("EMPLOYEE--->"+mobile+" "+password);
        return emp; // return user if password is correct
    } 
    
    // SAVE USER
    public UserMaster save(UserMaster user) {
    	user.setCreatedDate(LocalDateTime.now());
        return repo.save(user);
    }

    // GET ALL USERS (paginated) + Role
    public Page<UserDTO> getAll(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return repo.findAll(pageable)
                   .map(user -> new UserDTO(
                        user.getId(),
                        user.getsetMobileNo(),
                        user.getEmail(),
                        user.getRole() != null ? user.getRole().getRoleName() : null,
                        user.getStatus(),
                        user.getCreatedDate(),
                        user.getUpdatedDate(),
                        user.getName(),
                        user.getPassword(),
                        user.getRole().getRoleId()
                   ));
    }

    // GET USER BY ID + Role
    public Optional<UserDTO> getByIdUser(Long id){
        return repo.findById(id)
                   .map(user -> new UserDTO(
                        user.getId(),
                        user.getsetMobileNo(),
                        user.getEmail(),
                        user.getRole() != null ? user.getRole().getRoleName() : null,
                        user.getStatus(),
                        user.getCreatedDate(),
                        user.getUpdatedDate(),
                        user.getName(),
                        user.getPassword(),
                        user.getRole().getRoleId()
                   ));
    }

    // SEARCH USERS BY USERNAME (paginated) + Role
    public Page<UserDTO> searchUser(String username, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return repo.findByNameContainingIgnoreCase(username, pageable)
                   .map(user -> new UserDTO(
                        user.getId(),
                        user.getsetMobileNo(),
                        user.getEmail(),
                        user.getRole() != null ? user.getRole().getRoleName() : null,
                        user.getStatus(),
                        user.getCreatedDate(),
                        user.getUpdatedDate(),
                        user.getName(),
                        user.getPassword(),
                        user.getRole().getRoleId()
                   ));
    }

    // UPDATE USER
    public UserMaster update(Long id, UserMaster user) {
        UserMaster existingUser = repo.findById(id).orElseThrow();
        existingUser.setMobileNo(user.getsetMobileNo());
        existingUser.setPassword(user.getPassword());
        existingUser.setEmail(user.getEmail());
        existingUser.setRole(user.getRole());
        existingUser.setStatus(user.getStatus());
        existingUser.setUpdatedDate(user.getUpdatedDate());
        return repo.save(existingUser);
    }

    // DELETE USER
    public void delete(Long id) {
        repo.deleteById(id);
    }
    
    
 // PATCH-like update: only non-null fields are updated
    public UserMaster updateUser(Long id, UserMaster user) {
        UserMaster existingUser = repo.findById(id)
                                      .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));

        // ✅ Only update if field is not null
        if (user.getsetMobileNo() != null) existingUser.setMobileNo(user.getsetMobileNo());
        if (user.getPassword() != null) existingUser.setPassword(user.getPassword());
        if (user.getEmail() != null) existingUser.setEmail(user.getEmail());
        if (user.getRole() != null) existingUser.setRole(user.getRole());
        if (user.getStatus() != null) existingUser.setStatus(user.getStatus());

        existingUser.setUpdatedDate(LocalDateTime.now());

        return repo.save(existingUser);
    }
    
    // Return count of users with specific role
    public long countUsersByRole(String roleName) {
        return repo.countByRoleName(roleName);
    }
}