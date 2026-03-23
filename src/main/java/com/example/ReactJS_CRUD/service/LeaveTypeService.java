package com.example.ReactJS_CRUD.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.ReactJS_CRUD.Entity.LeaveTypeMaster;
import com.example.ReactJS_CRUD.repository.LeaveTypeRepository;

@Service
public class LeaveTypeService {

    @Autowired
    private LeaveTypeRepository repository;

    // GET ALL
    public Page<LeaveTypeMaster> getAll(int page, int size) {
        return repository.findAll(PageRequest.of(page, size));
    }

    // GET BY ID
    public Optional<LeaveTypeMaster> getByIdLeave(Long id) {
        return repository.findById(id);
    }

    // SAVE / UPDATE
    public LeaveTypeMaster save(LeaveTypeMaster leaveType) {
        return repository.save(leaveType);
    }

    // DELETE
    public void delete(Long id) {
        repository.deleteById(id);
    }
    
    public Page<LeaveTypeMaster> searchLeaveType(String name, int page, int size) {

        PageRequest pageable = PageRequest.of(page, size);

        return repository.findByLeaveTypeNameContainingIgnoreCase(name, pageable);
    }
}