package com.example.ReactJS_CRUD.controller;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.ReactJS_CRUD.Entity.EmployeeMaster;
import com.example.ReactJS_CRUD.Entity.LeaveApplication;
import com.example.ReactJS_CRUD.config.ApiResponse;
import com.example.ReactJS_CRUD.dto.AdminDashboardDTO;
import com.example.ReactJS_CRUD.dto.EmployeeDTO;
import com.example.ReactJS_CRUD.dto.EmployeeLeaveHolidayDTO;
import com.example.ReactJS_CRUD.dto.EmployeeLeaveInfoDTO;
import com.example.ReactJS_CRUD.dto.LeaveApplicationDTO;
import com.example.ReactJS_CRUD.dto.LeaveEmployeeDTO;
import com.example.ReactJS_CRUD.dto.LeaveResponseDTO;
import com.example.ReactJS_CRUD.service.AdminService;
import com.example.ReactJS_CRUD.service.EmailService;
import com.example.ReactJS_CRUD.service.EmployeeService;

import jakarta.annotation.Resource;
import jakarta.persistence.criteria.Path;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService service;
    
    @Autowired
    private AdminService adminService;
    
    @Autowired
    private EmailService emailService;
    

    // GET ALL (Pagination)
    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        logger.info("Fetching all employees. Page: {}, Size: {}", page, size);

        Page<EmployeeDTO> employees = service.getAll(page, size);

        if (employees.isEmpty()) {
            logger.warn("No employees found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No Employees Found");
        }

        logger.info("Employees fetched successfully");
        return ResponseEntity.ok(employees);
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {

        logger.info("Fetching employee with ID: {}", id);

        Optional<EmployeeDTO> employee = service.getByIdEmployee(id);

        if (employee.isPresent()) {
            logger.info("Employee found with ID: {}", id);
            return ResponseEntity.ok(employee.get());
        }

        logger.warn("Employee not found with ID: {}", id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Employee Not Found with ID : " + id);
    }

    // CREATE EMPLOYEE
 
    @PostMapping
    public ResponseEntity<EmployeeMaster> create(@RequestBody EmployeeMaster emp) 
    {

        logger.info("Creating new employee with name: {}", emp.getName());

        EmployeeMaster savedEmployee = service.save(emp);

        logger.info("Employee created successfully with ID: {}", savedEmployee.getId());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedEmployee);
    }
    
    
 
    
    //UPLOAD IMAGE API
    
    @PostMapping(value = "/upload-image/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<String>> uploadImage(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) 
    {

        service.uploadImage(id, file);
        ApiResponse<String> response = new ApiResponse<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Image uploaded successfully");

        return ResponseEntity.ok(response);
    }

 // UPDATE EMPLOYEE (Partial Update)
    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody EmployeeMaster emp) {

        logger.info("Updating employee with ID: {}", id);

        EmployeeMaster updatedEmployee = service.updateEmployee(id, emp);

        return ResponseEntity.ok(updatedEmployee);
    }
    
    // DELETE EMPLOYEE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        logger.info("Deleting employee with ID: {}", id);

        Optional<EmployeeDTO> employee = service.getByIdEmployee(id);

        if (employee.isEmpty()) {
            logger.warn("Employee not found for deletion with ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Employee Not Found with ID : " + id);
        }

        service.delete(id);

        logger.info("Employee deleted successfully with ID: {}", id);

        return ResponseEntity.ok("Employee Deleted Successfully");
    }

    // SEARCH EMPLOYEE (Pagination)
    @GetMapping("/search")
    public ResponseEntity<?> searchEmployee(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        logger.info("Searching employee with name: {}, Page: {}, Size: {}", name, page, size);

        Page<EmployeeDTO> employees = service.searchByName(name, page, size);

        if (employees.isEmpty()) {
            logger.warn("No employee found with name: {}", name);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No Employee Found with name : " + name);
        }

        logger.info("Employees found for search name: {}", name);
        return ResponseEntity.ok(employees);
    }
    
  
    
    
    
    @PostMapping("/employee-dashboard-leave-apply")
    public ResponseEntity<LeaveResponseDTO> applyLeave(@RequestBody LeaveApplicationDTO leave) 
    {
        leave.setAppliedDate(java.time.LocalDateTime.now());
        
        leave.setUpdatedDate(java.time.LocalDateTime.now());

        LeaveApplication savedLeave = service.saveLeave(leave);

        LeaveResponseDTO response = new LeaveResponseDTO(
                savedLeave.getId(),
                savedLeave.getStatus(),
                "Leave applied successfully"
        );

        return ResponseEntity.ok(response);
    }
    
    
    @GetMapping("/admin/leaves")
    public ResponseEntity<Page<LeaveEmployeeDTO>> getAllLeaves(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size)
    {

        PageRequest pageable = PageRequest.of(page, size);
        Page<LeaveEmployeeDTO> leavePage = service.getLeaveDetails(pageable);
        return ResponseEntity.ok(leavePage);
    }
    
    /*
    @PutMapping("/approve-leave-admin/{employeeId}")
    public ResponseEntity<ApiResponse<Void>> approveLeaves(@PathVariable Long employeeId) {
        boolean success = service.approveLeaves(employeeId);

        ApiResponse<Void> response = new ApiResponse<>();
        
        if (success) 
        {
        	 Optional<EmployeeDTO> employee = service.getByIdEmployee(employeeId);
        	 
        	 String email=employee.get().getEmail();
        	 
            response.setStatus(200);
            response.setMessage("Leave approved successfully for employee ID: " + employeeId);
            response.setData(null); // no extra data needed
            return ResponseEntity.ok(response); // HTTP 200
        } else {
            response.setStatus(404);
            response.setMessage("employee ID not Exist: " + employeeId);
            response.setData(null);
            return ResponseEntity.status(404).body(response); // HTTP 404
        }
    }
    */
    
    // ✅ Get leaves for an employee with pagination
    @GetMapping("/employee-dashboard-leave-list/{employeeId}")
    public ResponseEntity<ApiResponse<Page<EmployeeLeaveHolidayDTO>>> getLeavesByEmployee(
            @PathVariable Long employeeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) 
    {

        PageRequest pageable = PageRequest.of(page, size);
        Page<EmployeeLeaveHolidayDTO> leavesPage = service.getLeavesByEmployeeId(employeeId, pageable);

        ApiResponse<Page<EmployeeLeaveHolidayDTO>> response = new ApiResponse<>();
        if (!leavesPage.isEmpty()) 
        {
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Leaves fetched successfully");
            response.setData(leavesPage);
            return ResponseEntity.ok(response);
        }
        else 
        {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage("No leaves found for employee ID: " + employeeId);
            response.setData(null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
    
    
    
    // ✅ Get Employee Leave Info (department, joining date, total leaves)
    @GetMapping("/{employeeId}/employee-dashboard")
    public ResponseEntity<ApiResponse<EmployeeLeaveInfoDTO>> getEmployeeLeaveInfo(
            @PathVariable Long employeeId) 
    {
        EmployeeLeaveInfoDTO dto = service.getEmployeeLeaveInfo(employeeId);

        ApiResponse<EmployeeLeaveInfoDTO> response = new ApiResponse<>();

        if (dto != null) 
        {
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Employee leave info fetched successfully");
            response.setData(dto);
            return ResponseEntity.ok(response);
        } else {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage("No employee found with ID: " + employeeId);
            response.setData(null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
    //rawadae
  
       
       @GetMapping("/admin-dashboard-summary")
       public ResponseEntity<AdminDashboardDTO> getDashboardSummary() {
           AdminDashboardDTO dashboard = adminService.getAdminDashboardData();
           return ResponseEntity.ok(dashboard);
       }
       
       @PutMapping("/approve-leave-admin/{employeeId}/{leaveId}")
       public ResponseEntity<ApiResponse<Void>> approveLeaves(@PathVariable Long employeeId,@PathVariable Long leaveId) 
       {
           boolean success = service.approveLeave(employeeId,leaveId);

           ApiResponse<Void> response = new ApiResponse<>();

           if (success)
           {
               Optional<EmployeeDTO> employeeOpt = service.getByIdEmployee(employeeId);

               if (employeeOpt.isPresent()) 
               {
                   String email = employeeOpt.get().getEmail();
                   System.out.println("Email->"+email);
                   // ✅ Send email directly using the existing emailService
                   try
                   {
                       String subject = "Leave Approved";
                       String body = "Hello " + employeeOpt.get().getName() + ",\n\n" +
                                     "Your leave request has been approved.\n\n" +
                                     "Regards,\nCompany HR";
                       
                       emailService.sendSimpleEmail(email, subject, body);

                   }
                   catch (Exception e)
                   {
                       // Log error but don't fail the approval
                       logger.error("Failed to send approval email to {}: {}", email, e.getMessage());
                   }

                   response.setStatus(200);
                   response.setMessage("Leave approved successfully for employee ID: " + employeeId);
                   response.setData(null);
                   return ResponseEntity.ok(response);
               } 
               else 
               {
                   response.setStatus(404);
                   response.setMessage("Employee not found for ID: " + employeeId);
                   response.setData(null);
                   return ResponseEntity.status(404).body(response);
               }
           }
           else 
           {
               response.setStatus(404);
               response.setMessage("Employee ID not exist: " + employeeId);
               response.setData(null);
               return ResponseEntity.status(404).body(response);
           }
       }
       
     
       
}