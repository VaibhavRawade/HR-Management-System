package com.example.ReactJS_CRUD.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ReactJS_CRUD.Entity.RoleMaster;

public interface RoleRepository extends JpaRepository<RoleMaster, Long> {

    Page<RoleMaster> findByRoleNameContainingIgnoreCase(String name, Pageable pageable);


}