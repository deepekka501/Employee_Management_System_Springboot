package com.codewithekka.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codewithekka.model.Department;
import com.codewithekka.model.EmployeeDtls;
import com.codewithekka.repository.DepartmentRepo;

@Service
public class DeptServiceImpl implements Dept_service{
	@Autowired
	private DepartmentRepo deptRepo;
	@Override
	public Department addDept(Department dept) {
		return deptRepo.save(dept);
	}
	public List<Department> getAllDept(){
		return deptRepo.findAll();
	}
	@Override
	public void deleteDept(int id) {
		deptRepo.deleteById(id);
	}
	public Department findDeptByid(int id) {
		Optional<Department> dept = deptRepo.findById(id);
		if(dept.isPresent()) {
			return dept.get();
		}
		return null;
	}

}
