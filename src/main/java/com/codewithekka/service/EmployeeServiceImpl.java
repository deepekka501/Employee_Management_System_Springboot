package com.codewithekka.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.codewithekka.model.Department;
import com.codewithekka.model.EmployeeDtls;
import com.codewithekka.repository.DepartmentRepo;
import com.codewithekka.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService{

	@Autowired
	private EmployeeRepository employeeRepo;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	// using this method we get either one of this roles employee,manager, admin
	@Override
	public EmployeeDtls createEmployee(EmployeeDtls employee) {
		
		employee.setPassword(passwordEncoder.encode(employee.getPassword()));
		employee.setRole("ROLE_EMPLOYEE");
		// TODO Auto-generated method stub
		return employeeRepo.save(employee);
	}
	
	
	
	
	@Override
	public boolean checkEmail(String email) {
		// TODO Auto-generated method stub
		return employeeRepo.existsByEmail(email);
	}
	
	
	// using this method we ge t all the employee incuding manager and admin
	public List<EmployeeDtls> getAllEmp(){
		return employeeRepo.findAll();
	}

	// Get employee by id using for update the employee
	public EmployeeDtls getEmpById(int id) {
		Optional<EmployeeDtls> e = employeeRepo.findById(id);
		
		if(e.isPresent()) {
			return e.get();
		}
		return null;
		
	}



	//Using this method admin can add Employee with role and department and update Employee Details....
	@Override
	public EmployeeDtls addEmployee(EmployeeDtls employee) {
		
//			if(employee.getPassword()!=null){
//				
//			}
			employee.setPassword(passwordEncoder.encode(employee.getPassword()));
			return employeeRepo.save(employee);
	}




	@Override
	public void deleteEmp(int id) {
		// TODO Auto-generated method stub
		employeeRepo.deleteById(id);
	}




	



	
	





	
	
	

	

}
