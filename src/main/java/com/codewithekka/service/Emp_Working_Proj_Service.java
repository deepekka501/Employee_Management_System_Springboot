package com.codewithekka.service;

import com.codewithekka.model.Employee_Working_Project;

public interface Emp_Working_Proj_Service {
	public Employee_Working_Project assignProj(Employee_Working_Project ewp);
	public void deleteProj(int id);
}
