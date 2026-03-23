package com.example.ReactJS_CRUD.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.ReactJS_CRUD.Entity.DepartmentMaster;
import com.example.ReactJS_CRUD.service.DepartmentService;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private static final Logger logger = LoggerFactory.getLogger(DepartmentController.class);

    @Autowired
    private DepartmentService service;

    // GET ALL
    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        logger.info("Fetching all departments. Page: {}, Size: {}", page, size);

        Page<DepartmentMaster> departments = service.getAll(page, size);

        if (departments.isEmpty()) {
            logger.warn("No departments found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No Departments Found");
        }

        logger.info("Departments fetched successfully");
        return ResponseEntity.ok(departments);
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {

        logger.info("Fetching department with ID: {}", id);

        Optional<DepartmentMaster> dept = service.getByIdDepartment(id);

        if (dept.isPresent()) {
            logger.info("Department found with ID: {}", id);
            return ResponseEntity.ok(dept.get());
        }

        logger.warn("Department not found with ID: {}", id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Department not found with ID : " + id);
    }

    // CREATE
    @PostMapping
    public ResponseEntity<DepartmentMaster> create(@RequestBody DepartmentMaster dept) {

        logger.info("Creating new department with name: {}", dept.getDepartmentName());

        DepartmentMaster savedDept = service.save(dept);

        logger.info("Department created successfully with ID: {}", savedDept.getDepartmentId());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedDept);
    }

 // UPDATE DEPARTMENT (Partial Update)
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody DepartmentMaster dept) 
    {

        logger.info("Updating department with ID: {}", id);

        Optional<DepartmentMaster> existingDept = service.getByIdDepartment(id);

        if (existingDept.isEmpty()) {
            logger.warn("Department not found for update with ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Department not found with ID : " + id);
        }

        DepartmentMaster d = existingDept.get();

        // ✅ Only update fields if new value is not null
        if (dept.getDepartmentName() != null) {
            d.setDepartmentName(dept.getDepartmentName());
        }
        if (dept.getDepartmentCode() != null) {
            d.setDepartmentCode(dept.getDepartmentCode());
        }
        if (dept.getStatus() != null) {
            d.setStatus(dept.getStatus());
        }

        d.setUpdatedDate(LocalDateTime.now()); // update timestamp

        DepartmentMaster updatedDept = service.save(d);

        logger.info("Department updated successfully with ID: {}", id);

        return ResponseEntity.ok(updatedDept);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        logger.info("Deleting department with ID: {}", id);

        Optional<DepartmentMaster> dept = service.getByIdDepartment(id);

        if (dept.isEmpty()) {
            logger.warn("Department not found for deletion with ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Department not found with ID : " + id);
        }

        service.delete(id);

        logger.info("Department deleted successfully with ID: {}", id);

        return ResponseEntity.ok("Department deleted successfully");
    }

    // SEARCH
    @GetMapping("/search")
    public ResponseEntity<?> searchDepartment(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        logger.info("Searching department with name: {}, Page: {}, Size: {}", name, page, size);

        Page<DepartmentMaster> departments = service.searchDepartment(name, page, size);

        if (departments.isEmpty()) {
            logger.warn("No department found with name: {}", name);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No Department Found with name : " + name);
        }

        logger.info("Departments found for search name: {}", name);
        return ResponseEntity.ok(departments);
    }
    
}