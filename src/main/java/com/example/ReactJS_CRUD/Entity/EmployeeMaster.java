package com.example.ReactJS_CRUD.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "employee_master")
public class EmployeeMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_seq")
    @SequenceGenerator(name = "employee_seq", sequenceName = "employee_id_seq", allocationSize = 1)
    private Long id;

    private String name;

    private String email;

    @Column(name = "phone_number",unique = true)
    private String phoneNumber;

    private Double salary;


    @Column(name = "joining_date")
    private LocalDate joingDate ;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
    
    @Column(name = "updated_date")
    private LocalDateTime updatedDate = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_id",referencedColumnName = "department_id")  // foreign key column
    private DepartmentMaster department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id",referencedColumnName = "role_id")  // foreign key column
    private RoleMaster role;
    
    @Column(name = "image_path")
    private String imagePath;
    
    @Column(name="password")
    private String password;
    
    @Column(name = "whatsapp_link")
    private String whatsappLink;

    // Getter
    public String getWhatsappLink() {
        return whatsappLink;
    }

    // Setter
    public void setWhatsappLink(String whatsappLink) {
        this.whatsappLink = whatsappLink;
    }
    
    public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public LocalDate getJoingDate() {
		return joingDate;
	}
	public void setJoingDate(LocalDate joingDate) {
		this.joingDate = joingDate;
	}
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	// Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public Double getSalary() { return salary; }
    public void setSalary(Double salary) { this.salary = salary; }


    public DepartmentMaster getDepartment() { return department; }
    public void setDepartment(DepartmentMaster department) { this.department = department; }

    public RoleMaster getRole() { return role; }
    public void setRole(RoleMaster role) { this.role = role; }
    

    public LocalDateTime getUpdatedDate() { return updatedDate; }
    public void setUpdatedDate(LocalDateTime updatedDate) { this.updatedDate = updatedDate; }{
		// TODO Auto-generated method stub
		
	}
}