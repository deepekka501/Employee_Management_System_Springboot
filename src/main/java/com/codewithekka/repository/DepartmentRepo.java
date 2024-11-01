package com.codewithekka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codewithekka.model.Department;

@Repository
public interface DepartmentRepo extends JpaRepository<Department,Integer>{

	
}
