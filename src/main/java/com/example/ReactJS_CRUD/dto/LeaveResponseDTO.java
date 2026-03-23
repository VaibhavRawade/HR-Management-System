package com.example.ReactJS_CRUD.dto;
public class LeaveResponseDTO {
    private Long leaveId;
    private String status;
    private String message;

    public LeaveResponseDTO(Long leaveId, String status, String message) {
        this.leaveId = leaveId;
        this.status = status;
        this.message = message;
    }

    // Getters and setters
    public Long getLeaveId() { return leaveId; }
    public void setLeaveId(Long leaveId) { this.leaveId = leaveId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}