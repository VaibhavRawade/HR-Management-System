package com.example.ReactJS_CRUD.dto;

import java.time.LocalDate;

public class LeaveEmployeeDTO {

	Long id;
    private String employeeName;
    private String departmentName;
    private String roleName;
    private String status;
    private LocalDate fromDate;
    private LocalDate toDate;
    private Long leaveId;

    public LeaveEmployeeDTO(Long id, String employeeName, String departmentName,String roleName, String status,
                            LocalDate fromDate, LocalDate toDate,Long leaveId) {
    	this.id=id;
        this.employeeName = employeeName;
        this.departmentName = departmentName;
        this.roleName=roleName;
        this.status = status;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.leaveId=leaveId;
    }

    
    
    public Long getLeaveId() {
		return leaveId;
	}



	public void setLeaveId(Long leaveId) {
		this.leaveId = leaveId;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getRoleName() {
		return roleName;
	}


	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}


	// Getters and setters
    public String getEmployeeName() { return employeeName; }
    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }
    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDate getFromDate() { return fromDate; }
    public void setFromDate(LocalDate fromDate) { this.fromDate = fromDate; }
    public LocalDate getToDate() { return toDate; }
    public void setToDate(LocalDate toDate) { this.toDate = toDate; }
}