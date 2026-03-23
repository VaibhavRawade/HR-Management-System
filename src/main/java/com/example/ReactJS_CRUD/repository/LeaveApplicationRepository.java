package com.example.ReactJS_CRUD.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.ReactJS_CRUD.Entity.LeaveApplication;
import com.example.ReactJS_CRUD.dto.EmployeeLeaveHolidayDTO;
import com.example.ReactJS_CRUD.dto.EmployeeLeaveInfoDTO;
import com.example.ReactJS_CRUD.dto.LeaveEmployeeDTO;

import jakarta.transaction.Transactional;

@Repository
public interface LeaveApplicationRepository extends JpaRepository<LeaveApplication, Long> {
   
//	// You can add custom queries if needed later
//	@Query("SELECT new com.example.ReactJS_CRUD.dto.LeaveEmployeeDTO(" +
//	           "e.name, e.department.departmentName, l.status, l.fromDate, l.toDate) " +
//	           "FROM LeaveApplication l " +
//	           "JOIN l.employee e")	
//	    Page<LeaveEmployeeDTO> fetchLeaveDetails(Pageable pageable);
	
	@Query("SELECT new com.example.ReactJS_CRUD.dto.LeaveEmployeeDTO(" +
		       "e.id, e.name, d.departmentName, r.roleName, l.status, l.fromDate, l.toDate, l.id) " +
		       "FROM LeaveApplication l " +
		       "JOIN l.employee e " +
		       "JOIN e.department d " +
		       "JOIN e.role r")
		Page<LeaveEmployeeDTO> getLeaveEmployeeData(Pageable pageable);
	
	
	 @Query("SELECT COUNT(l) FROM LeaveApplication l WHERE l.status = 'Pending'")
	  Long countPendingLeaves();
	 
	// LeaveRepository.java
	 @Modifying
	 @Transactional
	 @Query("UPDATE LeaveApplication l SET l.status = 'Approved', l.updatedDate = CURRENT_TIMESTAMP " +
	        "WHERE l.id = :leaveId AND l.employee.id = :employeeId AND l.status <> 'Approved'")
	 int approveLeaveByIdAndEmployee(Long leaveId, Long employeeId);
	    
	    
	   
	    @Query(
	            value = "SELECT new com.example.ReactJS_CRUD.dto.EmployeeLeaveHolidayDTO(" +
	                    "lt.leaveTypeName, l.fromDate, l.toDate, l.status) " +
	                    "FROM LeaveApplication l " +
	                    "JOIN l.leaveType lt " +
	                    "WHERE l.employee.id = :employeeId",
	            countQuery = "SELECT COUNT(l) FROM LeaveApplication l WHERE l.employee.id = :employeeId"
	        )
	        Page<EmployeeLeaveHolidayDTO> findLeavesByEmployeeId(Long employeeId, Pageable pageable);
	    
	   
	    @Query("SELECT new com.example.ReactJS_CRUD.dto.EmployeeLeaveInfoDTO(" +
	    	       "e.department.departmentName, e.joingDate, " +
	    	       "(SELECT COUNT(l) FROM LeaveApplication l WHERE l.employee.id = e.id)) " +
	    	       "FROM EmployeeMaster e " +
	    	       "WHERE e.id = :employeeId")
	    	EmployeeLeaveInfoDTO findEmployeeLeaveInfo(@Param("employeeId") Long employeeId);


	    
	    @Modifying
	    @Transactional
	    @Query("DELETE FROM LeaveApplication l WHERE l.employee.id = :empId")
	    void deleteByEmployeeId(@Param("empId") Long empId);
}