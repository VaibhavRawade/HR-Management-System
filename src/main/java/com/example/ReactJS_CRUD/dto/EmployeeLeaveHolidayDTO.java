package com.example.ReactJS_CRUD.dto;

import java.time.LocalDate;

public class EmployeeLeaveHolidayDTO {

    private String leaveType;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String status;

    public  EmployeeLeaveHolidayDTO(String leaveType, LocalDate fromDate, LocalDate toDate, String status) {
        this.leaveType = leaveType;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.status = status;
    }

    // Getters and setters
    public String getLeaveType() { return leaveType; }
    public void setLeaveType(String leaveType) { this.leaveType = leaveType; }
    public LocalDate getFromDate() { return fromDate; }
    public void setFromDate(LocalDate fromDate) { this.fromDate = fromDate; }
    public LocalDate getToDate() { return toDate; }
    public void setToDate(LocalDate toDate) { this.toDate = toDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}