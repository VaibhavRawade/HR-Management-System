package com.example.ReactJS_CRUD.dto;

import java.time.LocalDateTime;

public class UserDTO {
    private Long id;
    private String mobile;
    private String email;
    private String roleName;   // RoleMaster role info
    private Boolean status;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String name;
    private String password;
    private Long roleId;

    public UserDTO(Long id, String mobile, String email, String roleName, Boolean status, LocalDateTime createdDate, LocalDateTime updatedDate,String name,String password,Long roleId) {
        this.id = id;
        this.mobile = mobile;
        this.email = email;
        this.roleName = roleName;
        this.status = status;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.name = name;
        this.password=password;
        this.roleId=roleId;
    }

    
	public Long getRoleId() {
		return roleId;
	}


	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", mobile=" + mobile + ", email=" + email + ", roleName=" + roleName + ", status="
				+ status + ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + ", name=" + name + "]";
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
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

    
    // Getters and setters
    // (or use Lombok @Data/@Getter/@Setter)
}