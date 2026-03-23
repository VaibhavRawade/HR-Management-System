package com.example.ReactJS_CRUD.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.ReactJS_CRUD.Entity.EmployeeMaster;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeMaster, Long> {

    List<EmployeeMaster> findByName(String name);

	Page<EmployeeMaster> findByNameContainingIgnoreCase(String name, Pageable pageable);
	
	  @Query("SELECT COUNT(e) FROM EmployeeMaster e")
	  long getTotalEmployees();
	  
	    EmployeeMaster findByPhoneNumberAndPassword(String phoneNumber, String password);

}