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

import com.example.ReactJS_CRUD.Entity.LeaveTypeMaster;
import com.example.ReactJS_CRUD.service.LeaveTypeService;

@RestController
@RequestMapping("/api/leave-types")
public class LeaveTypeController {

    private static final Logger logger = LoggerFactory.getLogger(LeaveTypeController.class);

    @Autowired
    private LeaveTypeService service;

    // GET ALL
    @GetMapping
    public ResponseEntity<?> getAllLeaveTypes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        logger.info("Fetching all leave types. Page: {}, Size: {}", page, size);

        Page<LeaveTypeMaster> leaveTypes = service.getAll(page, size);

        if (leaveTypes.isEmpty()) {
            logger.warn("No leave types found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No Leave Types Found");
        }

        logger.info("Leave types fetched successfully");
        return ResponseEntity.ok(leaveTypes);
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {

        logger.info("Fetching leave type with ID: {}", id);

        Optional<LeaveTypeMaster> leaveType = service.getByIdLeave(id);

        if (leaveType.isPresent()) {
            logger.info("Leave type found with ID: {}", id);
            return ResponseEntity.ok(leaveType.get());
        }

        logger.warn("Leave type not found with ID: {}", id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Leave Type Not Found with ID : " + id);
    }

    // CREATE
    @PostMapping
    public ResponseEntity<LeaveTypeMaster> create(@RequestBody LeaveTypeMaster leaveType) {

        logger.info("Creating leave type with name: {}", leaveType.getLeaveTypeName());

        LeaveTypeMaster savedLeaveType = service.save(leaveType);

        logger.info("Leave type created successfully with ID: {}", savedLeaveType.getLeaveTypeId());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedLeaveType);
    }

 // UPDATE LEAVE TYPE (Partial Update)
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody LeaveTypeMaster leaveType) {

        logger.info("Updating leave type with ID: {}", id);

        Optional<LeaveTypeMaster> existingLeaveType = service.getByIdLeave(id);

        if (existingLeaveType.isEmpty()) {
            logger.warn("Leave type not found for update with ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Leave Type Not Found with ID : " + id);
        }

        LeaveTypeMaster l = existingLeaveType.get();

        // ✅ Only update fields if they are not null
        if (leaveType.getLeaveTypeName() != null) l.setLeaveTypeName(leaveType.getLeaveTypeName());
        if (leaveType.getLeaveTypeCode() != null) l.setLeaveTypeCode(leaveType.getLeaveTypeCode());
        if (leaveType.getDescription() != null) l.setDescription(leaveType.getDescription());
        if (leaveType.getStatus() != null) l.setStatus(leaveType.getStatus());

        l.setUpdatedDate(LocalDateTime.now()); // always update timestamp

        LeaveTypeMaster updatedLeaveType = service.save(l);

        logger.info("Leave type updated successfully with ID: {}", id);

        return ResponseEntity.ok(updatedLeaveType);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        logger.info("Deleting leave type with ID: {}", id);

        Optional<LeaveTypeMaster> leaveType = service.getByIdLeave(id);

        if (leaveType.isEmpty()) {
            logger.warn("Leave type not found for deletion with ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Leave Type Not Found with ID : " + id);
        }

        service.delete(id);

        logger.info("Leave type deleted successfully with ID: {}", id);

        return ResponseEntity.ok("Leave Type Deleted Successfully");
    }

    // SEARCH
    @GetMapping("/search")
    public ResponseEntity<?> searchLeaveType(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        logger.info("Searching leave types with name: {}, Page: {}, Size: {}", name, page, size);

        Page<LeaveTypeMaster> leaveTypes = service.searchLeaveType(name, page, size);

        if (leaveTypes.isEmpty()) {
            logger.warn("No leave types found with name: {}", name);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No Leave Type Found");
        }

        logger.info("Leave types found for search name: {}", name);
        return ResponseEntity.ok(leaveTypes);
    }
}