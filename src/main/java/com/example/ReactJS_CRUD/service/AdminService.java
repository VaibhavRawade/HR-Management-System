package com.example.ReactJS_CRUD.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ReactJS_CRUD.dto.AdminDashboardDTO;
import com.example.ReactJS_CRUD.repository.DepartmentRepository;
import com.example.ReactJS_CRUD.repository.EmployeeRepository;
import com.example.ReactJS_CRUD.repository.LeaveApplicationRepository;
import com.example.ReactJS_CRUD.repository.RoleRepository;
import com.example.ReactJS_CRUD.repository.UserRepository;

@Service
public class AdminService {

    @Autowired
    private EmployeeRepository repo;

    @Autowired
    private DepartmentRepository departmentRepo;

    @Autowired
    private LeaveApplicationRepository leaveRepository;

    @Autowired
    private RoleRepository roleRepo;
    

    @Autowired
    private UserRepository userRepo;


    public AdminDashboardDTO getAdminDashboardData() {
        long totalEmployees = repo.getTotalEmployees();
        long pendingLeaves = leaveRepository.countPendingLeaves();
        long totalHRs = userRepo.countByRoleName("HR");
        long totalDepartments = departmentRepo.getTotalDepartments();

        return new AdminDashboardDTO(totalEmployees, pendingLeaves, totalHRs, totalDepartments);
    }
}