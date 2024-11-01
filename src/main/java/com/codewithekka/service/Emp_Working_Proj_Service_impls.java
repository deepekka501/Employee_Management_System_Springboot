package com.codewithekka.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codewithekka.model.Department;
import com.codewithekka.model.Employee_Working_Project;
import com.codewithekka.repository.Employee_Working_Project_Repository;

@Service
public class Emp_Working_Proj_Service_impls implements Emp_Working_Proj_Service{

	@Autowired
	private Employee_Working_Project_Repository ewpRepo;
	
	@Override
	public Employee_Working_Project assignProj(Employee_Working_Project ewp) {
		// TODO Auto-generated method stub
		return ewpRepo.save(ewp);
	}
	public List<Employee_Working_Project> viewAllAssingedProj(){
		return ewpRepo.findAll();
	}
	@Override
	public void deleteProj(int id) {
		ewpRepo.deleteById(id);
		
	}
	
	public List<Employee_Working_Project> viewAllWorkingProj(int id) {
		// TODO Auto-generated method stub
		return ewpRepo.getWorkingProjByDept(id);
	}
	

}
