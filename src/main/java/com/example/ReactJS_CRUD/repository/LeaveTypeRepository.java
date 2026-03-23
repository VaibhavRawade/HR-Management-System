package com.example.ReactJS_CRUD.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ReactJS_CRUD.Entity.LeaveTypeMaster;

@Repository
public interface LeaveTypeRepository extends JpaRepository<LeaveTypeMaster, Long> {

    Page<LeaveTypeMaster> findByLeaveTypeNameContainingIgnoreCase(String name, Pageable pageable);

}