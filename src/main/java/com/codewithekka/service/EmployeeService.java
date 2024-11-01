package com.codewithekka.service;

import com.codewithekka.model.Department;
import com.codewithekka.model.EmployeeDtls;

public interface EmployeeService {

	public EmployeeDtls createEmployee(EmployeeDtls employee);
	
	public EmployeeDtls addEmployee(EmployeeDtls employee);
	
	public boolean checkEmail(String email);
	
	public void deleteEmp(int id);
}
