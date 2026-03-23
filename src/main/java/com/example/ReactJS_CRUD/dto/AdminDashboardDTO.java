package com.example.ReactJS_CRUD.dto;
public class AdminDashboardDTO {
    private long totalEmployees;
    private long pendingLeaves;
    private long totalHRs;
    private long totalDepartments;

    // Constructors
    public AdminDashboardDTO() {}

    public AdminDashboardDTO(long totalEmployees, long pendingLeaves, long totalHRs, long totalDepartments) {
        this.totalEmployees = totalEmployees;
        this.pendingLeaves = pendingLeaves;
        this.totalHRs = totalHRs;
        this.totalDepartments = totalDepartments;
    }

    // Getters and Setters
    public long getTotalEmployees() {
        return totalEmployees;
    }

    public void setTotalEmployees(long totalEmployees) {
        this.totalEmployees = totalEmployees;
    }

    public long getPendingLeaves() {
        return pendingLeaves;
    }

    public void setPendingLeaves(long pendingLeaves) {
        this.pendingLeaves = pendingLeaves;
    }

    public long getTotalHRs() {
        return totalHRs;
    }

    public void setTotalHRs(long totalHRs) {
        this.totalHRs = totalHRs;
    }

    public long getTotalDepartments() {
        return totalDepartments;
    }

    public void setTotalDepartments(long totalDepartments) {
        this.totalDepartments = totalDepartments;
    }
}