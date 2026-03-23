package com.example.ReactJS_CRUD.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.ReactJS_CRUD.Entity.RoleMaster;
import com.example.ReactJS_CRUD.repository.RoleRepository;
import com.example.ReactJS_CRUD.service.RoleService;

@Service
public class RoleService
{

    @Autowired
    private RoleRepository repository;

    public Page<RoleMaster> getAll(int page, int size) {
        return repository.findAll(PageRequest.of(page, size));
    }

    public Optional<RoleMaster> getByIdRole(Long id) {
        return repository.findById(id);
    }

    public RoleMaster save(RoleMaster role) {
    	role.setRoleName(role.getRoleName().toUpperCase());
        return repository.save(role);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
    
    
    public Page<RoleMaster> searchRole(String name, int page, int size){
        PageRequest pageable = PageRequest.of(page, size);
        return repository.findByRoleNameContainingIgnoreCase(name, pageable);
    }
}