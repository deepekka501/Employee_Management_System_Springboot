package com.codewithekka.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codewithekka.model.ApplyLeave;
import com.codewithekka.model.Department;
import com.codewithekka.model.Projects;
import com.codewithekka.repository.leaveRepository;

@Service
public class LeaveServiceImpl implements LeaveService{

	@Autowired
	private leaveRepository repo;

	@Override
	public ApplyLeave applyLeave(ApplyLeave leave) {
		leave.setStatus("Pending..");
		return repo.save(leave);
	}
	public List<ApplyLeave> getAllLeave(){
		return repo.findAll();
	}
	
	public ApplyLeave getleavesById(int id) {
		Optional<ApplyLeave> proj = repo.findById(id);
		if(proj.isPresent()) {
			return proj.get();
		}
		return null;
		
	}
	

}
