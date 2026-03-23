package com.example.ReactJS_CRUD.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class UserMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mobile_no", updatable = false)
    private String mobileno;

    @Column(name = "name", updatable = false)
    private String name;

    
    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(nullable = false)
    private String password;

    private String email;

	@ManyToOne(fetch = FetchType.EAGER) // eager fetch to avoid proxy issues
    @JoinColumn(name = "role", referencedColumnName = "role_id")
    private RoleMaster role;

    private Boolean status = true; // default active

    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;
    
    // New fields for OTP
    @Column(name = "otp")
    private String otp;

    @Column(name = "otp_expiry")
    private LocalDateTime otpExpiry;

	public String getMobileno() {
		return mobileno;
	}
	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}
	public LocalDateTime getOtpExpiry() {
		return otpExpiry;
	}
	public void setOtpExpiry(LocalDateTime otpExpiry) {
		this.otpExpiry = otpExpiry;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getsetMobileNo() { return mobileno; }
    public void setMobileNo(String username) { this.mobileno = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }


    public RoleMaster getRole() { return role; }
    public void setRole(RoleMaster role) { this.role = role; }

    public Boolean getStatus() { return status; }
    public void setStatus(Boolean status) { this.status = status; }

    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }

    public LocalDateTime getUpdatedDate() { return updatedDate; }
    public void setUpdatedDate(LocalDateTime updatedDate) { this.updatedDate = updatedDate; }
}