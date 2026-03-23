package com.example.ReactJS_CRUD.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class EmployeeDTO {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private Double salary;
    private String departmentName;
    private String roleName;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Long deptId;
    private Long roleId;
    private LocalDate joingDate ;
    private String imagePath;
    
    public String getImagePath() {
		return imagePath;
	}



	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}



	public EmployeeDTO(Long id, String name, String email, String phoneNumber, Double salary,
            String departmentName, String roleName,
            LocalDateTime createdDate, LocalDateTime updatedDate,
            Long deptId, Long roleId, LocalDate joingDate,
            String imagePath) {

this.id = id;
this.name = name;
this.email = email;
this.phoneNumber = phoneNumber;
this.salary = salary;
this.departmentName = departmentName;
this.roleName = roleName;
this.createdDate = createdDate;
this.updatedDate = updatedDate;
this.deptId = deptId;
this.roleId = roleId;
this.joingDate = joingDate;
this.imagePath = imagePath;
}
    
    

	public LocalDate getJoingDate() {
		return joingDate;
	}



	public void setJoingDate(LocalDate joingDate) {
		this.joingDate = joingDate;
	}



	public Long getDeptId() {
		return deptId;
	}



	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}



	public Long getRoleId() {
		return roleId;
	}



	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}

    
    
    // Getters and setters (or use Lombok @Data)
}