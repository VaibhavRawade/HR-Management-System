package com.example.ReactJS_CRUD.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.ReactJS_CRUD.Entity.DepartmentMaster;

public interface DepartmentRepository 
        extends JpaRepository<DepartmentMaster, Long> {
    Page<DepartmentMaster> findByDepartmentNameContainingIgnoreCase(String name, Pageable pageable);

    // ✅ JPA query to get total count of departments
    @Query("SELECT COUNT(d) FROM DepartmentMaster d")
    long getTotalDepartments();
}