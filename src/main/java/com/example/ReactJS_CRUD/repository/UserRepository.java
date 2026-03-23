package com.example.ReactJS_CRUD.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.ReactJS_CRUD.Entity.UserMaster;

public interface UserRepository extends JpaRepository<UserMaster,Long> {

    UserMaster findByMobilenoAndPassword(String mobileno, String password);

    @Query("SELECT COUNT(u) FROM UserMaster u JOIN u.role r WHERE LOWER(r.roleName) = LOWER(:roleName)")
    long countByRoleName(@Param("roleName") String roleName);
    
	UserMaster findByMobileno(String mobile);
	
	Page<UserMaster> findByNameContainingIgnoreCase(String name, Pageable pageable);
	
}