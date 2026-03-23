package com.example.ReactJS_CRUD.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;


@Entity
@Table(name = "leave_application")
public class LeaveApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "leave_app_seq")
    @SequenceGenerator(name = "leave_app_seq", sequenceName = "leave_app_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leave_type_id")
    private LeaveTypeMaster leaveType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private EmployeeMaster employee;

    @Column(name = "from_date", nullable = false)
    private LocalDate fromDate;

    @Column(name = "to_date", nullable = false)
    private LocalDate toDate;

    @Column(length = 500)
    private String reason;

    @Column(nullable = false)
    private String status = "Pending";  // default status

    @Column(name = "applied_date", updatable = false)
    private LocalDateTime appliedDate = LocalDateTime.now();

    @Column(name = "updated_date")
    private LocalDateTime updatedDate = LocalDateTime.now();

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LeaveTypeMaster getLeaveType() { return leaveType; }
    public void setLeaveType(LeaveTypeMaster leaveType) { this.leaveType = leaveType; }

    public EmployeeMaster getEmployee() { return employee; }
    public void setEmployee(EmployeeMaster employee) { this.employee = employee; }

    public LocalDate getFromDate() { return fromDate; }
    public void setFromDate(LocalDate fromDate) { this.fromDate = fromDate; }

    public LocalDate getToDate() { return toDate; }
    public void setToDate(LocalDate toDate) { this.toDate = toDate; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getAppliedDate() { return appliedDate; }
    public void setAppliedDate(LocalDateTime appliedDate) { this.appliedDate = appliedDate; }

    public LocalDateTime getUpdatedDate() { return updatedDate; }
    public void setUpdatedDate(LocalDateTime updatedDate) { this.updatedDate = updatedDate; }
}