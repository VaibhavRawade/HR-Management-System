package com.example.ReactJS_CRUD.dto;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class LeaveApplicationDTO {

    private Long id;

    private Long leaveTypeId;       // only keep the key
    private Long employeeId;        // only keep the key

    private LocalDate fromDate;
    private LocalDate toDate;
    private String reason;
    private String status;

    private LocalDateTime appliedDate;
    private LocalDateTime updatedDate;

    // ✅ Default constructor
    public LeaveApplicationDTO() {}

    // ✅ Parameterized constructor
    public LeaveApplicationDTO(Long id, Long leaveTypeId, Long employeeId,
                               LocalDate fromDate, LocalDate toDate,
                               String reason, String status,
                               LocalDateTime appliedDate, LocalDateTime updatedDate) {
        this.id = id;
        this.leaveTypeId = leaveTypeId;
        this.employeeId = employeeId;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.reason = reason;
        this.status = status;
        this.appliedDate = appliedDate;
        this.updatedDate = updatedDate;
    }

    // ✅ Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLeaveTypeId() {
        return leaveTypeId;
    }

    public void setLeaveTypeId(Long leaveTypeId) {
        this.leaveTypeId = leaveTypeId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getAppliedDate() {
        return appliedDate;
    }

    public void setAppliedDate(LocalDateTime appliedDate) {
        this.appliedDate = appliedDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }
}