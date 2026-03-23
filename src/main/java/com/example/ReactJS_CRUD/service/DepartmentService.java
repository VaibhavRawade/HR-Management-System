package com.example.ReactJS_CRUD.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.ReactJS_CRUD.Entity.DepartmentMaster;
import com.example.ReactJS_CRUD.repository.DepartmentRepository;

@Service
public class DepartmentService {
	@Autowired
	private DepartmentRepository repository;

	public Page<DepartmentMaster> getAll(int page, int size) {
		return repository.findAll(PageRequest.of(page, size));
	}

	public Optional<DepartmentMaster> getByIdDepartment(Long id) {
	    return repository.findById(id);
	}

	public DepartmentMaster save(DepartmentMaster department) {
	
		return repository.save(department);
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	 // Return total department count
    public long getTotalDepartments() {
        return repository.getTotalDepartments();
    }
	
	public Page<DepartmentMaster> searchDepartment(String name, int page, int size) {

	    PageRequest pageable = PageRequest.of(page, size);

	    return repository.findByDepartmentNameContainingIgnoreCase(name, pageable);
	}
}