package com.example.ReactJS_CRUD.service;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.ReactJS_CRUD.Entity.DepartmentMaster;
import com.example.ReactJS_CRUD.Entity.EmployeeMaster;
import com.example.ReactJS_CRUD.Entity.LeaveApplication;
import com.example.ReactJS_CRUD.Entity.LeaveTypeMaster;
import com.example.ReactJS_CRUD.Entity.RoleMaster;
import com.example.ReactJS_CRUD.dto.EmployeeDTO;
import com.example.ReactJS_CRUD.dto.EmployeeLeaveHolidayDTO;
import com.example.ReactJS_CRUD.dto.EmployeeLeaveInfoDTO;
import com.example.ReactJS_CRUD.dto.LeaveApplicationDTO;
import com.example.ReactJS_CRUD.dto.LeaveEmployeeDTO;
import com.example.ReactJS_CRUD.repository.DepartmentRepository;
import com.example.ReactJS_CRUD.repository.EmployeeRepository;
import com.example.ReactJS_CRUD.repository.LeaveApplicationRepository;
import com.example.ReactJS_CRUD.repository.RoleRepository;
import com.example.ReactJS_CRUD.utils.Snippet;
import com.example.ReactJS_CRUD.utils.WhatsAppService;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repo;
    
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private LeaveApplicationRepository leaveRepository;
    
    
    @Value("${file.upload-dir}")
    private String uploadDir;
    
    // Inject from properties
    @Value("${employee.image.base-url}")
    private String employeeImageBaseUrl;

    // GET ALL EMPLOYEES (paginated)
    public Page<EmployeeDTO> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repo.findAll(pageable)
                   .map(emp -> new EmployeeDTO(
                        emp.getId(),
                        emp.getName(),
                        emp.getEmail(),
                        emp.getPhoneNumber(),
                        emp.getSalary(),
                        emp.getDepartment() != null ? emp.getDepartment().getDepartmentName() : null,
                        emp.getRole() != null ? emp.getRole().getRoleName() : null,
                        emp.getCreatedDate(),
                        emp.getUpdatedDate(),
                        emp.getDepartment().getDepartmentId(),
                        emp.getRole().getRoleId(),
                        emp.getJoingDate(),
                        employeeImageBaseUrl + emp.getImagePath() // ✅ Dynamic URL from properties
                   ));
    }

 // GET EMPLOYEE ENTITY BY ID
    public Optional<EmployeeMaster> getByIdMaster(Long id) {
        return repo.findById(id);
    }

    

    @Autowired
    private Snippet snippet; // inject the PDF generator
    
    @Autowired
    private WhatsAppService whatsAppService;
    
    public EmployeeMaster save(EmployeeMaster employee) 
    {

        // ✅ Fetch department from DB
        DepartmentMaster dept = departmentRepository
                .findById(employee.getDepartment().getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        // ✅ Fetch role from DB
        RoleMaster role = roleRepository
                .findById(employee.getRole().getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        // ✅ Set managed entities
        employee.setDepartment(dept);
        employee.setRole(role);
        employee.setCreatedDate(LocalDateTime.now());  
        EmployeeMaster savedEmployee= repo.save(employee);
        return savedEmployee;
        
        /*
        // Generate PDF
        String pdfPath;
        try 
        {
            pdfPath = snippet.generatePdf(savedEmployee);
        }
        catch (Exception e)
        {
            throw new RuntimeException("PDF generation failed", e);
        }

        String baseUrl = "https://hot-masks-jump.loca.lt";
        
        String fileName = new File(pdfPath).getName();
        String pdfLink = baseUrl + "/uploads/employees/" + fileName;
        // Format phone number
        String phone = savedEmployee.getPhoneNumber();
        if (!phone.startsWith("91")) 
        {
            phone = "91" + phone;
        }

        // Send WhatsApp automatically
        whatsAppService.sendPdfLink(phone, pdfLink);

        // Store link in DB (optional)
        savedEmployee.setWhatsappLink(pdfLink);

        return savedEmployee;  
        */
    }
    
    
    
    
    public void uploadImage(Long id, MultipartFile file)
    {

        try 
        {
            EmployeeMaster employee = repo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Employee not found"));

            if (file != null && !file.isEmpty())
            {

            	System.out.println("Directory->"+uploadDir);
                File dir = new File(uploadDir);
                if (!dir.exists())
                {
                    dir.mkdirs();
                }

                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

                Path filePath = Paths.get(uploadDir, fileName);

                Files.write(filePath, file.getBytes());

                // ✅ store ONLY filename (better)
                employee.setImagePath(fileName);

                repo.save(employee);
            }

        }
        catch (IOException e)
        {
            throw new RuntimeException("File upload failed", e);
        }
    }
    

   
    
    // GET BY ID + department + role
    public Optional<EmployeeDTO> getByIdEmployee(Long id) {
        return repo.findById(id)
                   .map(emp -> new EmployeeDTO(
                        emp.getId(),
                        emp.getName(),
                        emp.getEmail(),
                        emp.getPhoneNumber(),
                        emp.getSalary(),
                        emp.getDepartment() != null ? emp.getDepartment().getDepartmentName() : null,
                        emp.getRole() != null ? emp.getRole().getRoleName() : null,
                        emp.getCreatedDate(),
                        emp.getUpdatedDate(),
                        emp.getDepartment().getDepartmentId(),
                        emp.getRole().getRoleId(),
                        emp.getJoingDate(),
                        employeeImageBaseUrl + emp.getImagePath() // ✅ Dynamic URL from properties
                   ));
    }

    // DELETE EMPLOYEE
    public void delete(Long id) 
    {
        leaveRepository.deleteByEmployeeId(id);

        repo.deleteById(id);
    }

    // SEARCH BY NAME + department + role (paginated)
    public Page<EmployeeDTO> searchByName(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repo.findByNameContainingIgnoreCase(name, pageable)
                   .map(emp -> new EmployeeDTO(
                        emp.getId(),
                        emp.getName(),
                        emp.getEmail(),
                        emp.getPhoneNumber(),
                        emp.getSalary(),
                        emp.getDepartment() != null ? emp.getDepartment().getDepartmentName() : null,
                        emp.getRole() != null ? emp.getRole().getRoleName() : null,
                        emp.getCreatedDate(),
                        emp.getUpdatedDate(),
                        emp.getDepartment().getDepartmentId(),
                        emp.getRole().getRoleId(),
                        emp.getJoingDate(),
                        emp.getImagePath()
                   ));
    }
    
    // Return total employee count
    public long getTotalEmployees() {
        return repo.getTotalEmployees();
    }
    
    public LeaveApplication x(LeaveApplication leave) 
    {
    	
    	leave.setStatus("Pending");
    	leave.setAppliedDate(LocalDateTime.now());
        return leaveRepository.save(leave);
    }

    
    public Page<LeaveEmployeeDTO> getLeaveDetails(Pageable pageable) 
    {
        return leaveRepository.getLeaveEmployeeData(pageable);
    }
    
 // ✅ Count pending leaves
    public Long getPendingLeaveCount() {
        return leaveRepository.countPendingLeaves();
    }
    
 // LeaveService.java
    public boolean approveLeave(Long employeeId, Long leaveId) {
        int updated = leaveRepository.approveLeaveByIdAndEmployee(leaveId, employeeId);
        return updated > 0;
    }
    
    public Page<EmployeeLeaveHolidayDTO> getLeavesByEmployeeId(Long employeeId, Pageable pageable) {
        return leaveRepository.findLeavesByEmployeeId(employeeId, pageable);
    }
    

    public EmployeeLeaveInfoDTO getEmployeeLeaveInfo(Long employeeId) {
        // Fetch employee info + total leaves using custom query
        return leaveRepository.findEmployeeLeaveInfo(employeeId);
    }
    
    
    public LeaveApplication saveLeave(LeaveApplicationDTO dto) 
    {
        LeaveApplication leave = new LeaveApplication();

        // ✅ Set only IDs for ManyToOne references
        EmployeeMaster employeeRef = new EmployeeMaster();
        employeeRef.setId(dto.getEmployeeId());
        leave.setEmployee(employeeRef);

        LeaveTypeMaster leaveTypeRef = new LeaveTypeMaster();
        leaveTypeRef.setLeaveTypeId(dto.getLeaveTypeId());
        leave.setLeaveType(leaveTypeRef);
        // Map other fields
        leave.setFromDate(dto.getFromDate());
        leave.setToDate(dto.getToDate());
        leave.setReason(dto.getReason());
        leave.setStatus(dto.getStatus() != null ? dto.getStatus() : "Pending");
        leave.setAppliedDate(LocalDateTime.now());
        leave.setUpdatedDate(LocalDateTime.now());

        return leaveRepository.save(leave);
    }
    


        public EmployeeMaster updateEmployee(Long id, EmployeeMaster emp) 
        {

            EmployeeMaster e = repo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Employee Not Found with ID: " + id));

            // ✅ Basic fields
            if (emp.getName() != null) e.setName(emp.getName());
            if (emp.getEmail() != null) e.setEmail(emp.getEmail());
            if (emp.getPhoneNumber() != null) e.setPhoneNumber(emp.getPhoneNumber());
            if (emp.getSalary() != null) e.setSalary(emp.getSalary());

            // ✅ Department mapping
            if (emp.getDepartment() != null && emp.getDepartment().getDepartmentId() != null) {
                DepartmentMaster dept = departmentRepository
                        .findById(emp.getDepartment().getDepartmentId())
                        .orElseThrow(() -> new RuntimeException("Department not found"));

                e.setDepartment(dept);
            }

            // ✅ Role mapping
            if (emp.getRole() != null && emp.getRole().getRoleId() != null) {
                RoleMaster role = roleRepository
                        .findById(emp.getRole().getRoleId())
                        .orElseThrow(() -> new RuntimeException("Role not found"));

                e.setRole(role);
            }

            // ✅ Date
            if (emp.getJoingDate() != null) {
                e.setJoingDate(emp.getJoingDate());
            }

            e.setUpdatedDate(LocalDateTime.now());

            EmployeeMaster master= repo.save(e);
            
         // Only keep IDs to return (optional)
            DepartmentMaster dept = new DepartmentMaster();
            dept.setDepartmentId(master.getDepartment().getDepartmentId());

            RoleMaster role = new RoleMaster();
            role.setRoleId(master.getRole().getRoleId());
            
            master.setDepartment(dept);
            master.setRole(role);
            return master;
        }

}