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

import com.example.ReactJS_CRUD.Entity.RoleMaster;
import com.example.ReactJS_CRUD.service.RoleService;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService service;

    //ahssdsdkjddkjxsAJDjdk;sfl;fd;slfd;lfsn;;
    
    // GET ALL
    @GetMapping
    public ResponseEntity<?> getAllRoles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        logger.info("Fetching all roles. Page: {}, Size: {}", page, size);

        Page<RoleMaster> roles = service.getAll(page, size);

        if (roles.isEmpty()) {
            logger.warn("No roles found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No Roles Found");
        }

        logger.info("Roles fetched successfully");
        return ResponseEntity.ok(roles);
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {

        logger.info("Fetching role with ID: {}", id);

        Optional<RoleMaster> role = service.getByIdRole(id);

        if (role.isPresent()) {
            logger.info("Role found with ID: {}", id);
            return ResponseEntity.ok(role.get());
        }

        logger.warn("Role not found with ID: {}", id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Role Not Found with ID : " + id);
    }

    // CREATE
    @PostMapping
    public ResponseEntity<RoleMaster> createRole(@RequestBody RoleMaster role) {

        logger.info("Creating role with name: {}", role.getRoleName());

        RoleMaster savedRole = service.save(role);

        logger.info("Role created successfully with ID: {}", savedRole.getRoleId());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedRole);
    }

 // UPDATE ROLE (Partial Update)
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRole(@PathVariable Long id, @RequestBody RoleMaster role) {

        logger.info("Updating role with ID: {}", id);

        Optional<RoleMaster> existingRole = service.getByIdRole(id);

        if (existingRole.isEmpty()) {
            logger.warn("Role not found for update with ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Role Not Found with ID : " + id);
        }

        RoleMaster r = existingRole.get();

        // ✅ Only update fields if they are not null
        if (role.getRoleName() != null) r.setRoleName(role.getRoleName());
        if (role.getRoleCode() != null) r.setRoleCode(role.getRoleCode());
        if (role.getSequenceNo() != null) r.setSequenceNo(role.getSequenceNo());
        if (role.getStatus() != null) r.setStatus(role.getStatus());

        r.setUpdatedDate(LocalDateTime.now()); // always update timestamp

        RoleMaster updatedRole = service.save(r);

        logger.info("Role updated successfully with ID: {}", id);

        return ResponseEntity.ok(updatedRole);
    }
    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable Long id) {

        logger.info("Deleting role with ID: {}", id);

        Optional<RoleMaster> role = service.getByIdRole(id);

        if (role.isEmpty()) {
            logger.warn("Role not found for deletion with ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Role Not Found with ID : " + id);
        }

        service.delete(id);

        logger.info("Role deleted successfully with ID: {}", id);

        return ResponseEntity.ok("Role Deleted Successfully");
    }

    // SEARCH
    @GetMapping("/search")
    public ResponseEntity<?> searchRole(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        logger.info("Searching roles with name: {}, Page: {}, Size: {}", name, page, size);

        Page<RoleMaster> roles = service.searchRole(name, page, size);

        if (roles.isEmpty()) {
            logger.warn("No roles found with name: {}", name);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No Role Found with name : " + name);
        }

        logger.info("Roles found for search name: {}", name);
        return ResponseEntity.ok(roles);
    }
}