package com.example.ReactJS_CRUD.dto;

import java.time.LocalDate;

public class EmployeeLeaveInfoDTO {
    private String departmentName;
    private LocalDate joiningDate;
    private Long totalLeaves;

    public EmployeeLeaveInfoDTO(String departmentName, LocalDate joiningDate, Long totalLeaves) {
        this.departmentName = departmentName;
        this.joiningDate = joiningDate;
        this.totalLeaves = totalLeaves;
    }

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public LocalDate getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(LocalDate joiningDate) {
		this.joiningDate = joiningDate;
	}

	public Long getTotalLeaves() {
		return totalLeaves;
	}

	public void setTotalLeaves(Long totalLeaves) {
		this.totalLeaves = totalLeaves;
	}

    
    // getters and setters
}