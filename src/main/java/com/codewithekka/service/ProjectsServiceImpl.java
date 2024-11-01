package com.codewithekka.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codewithekka.model.Projects;
import com.codewithekka.repository.ProjectsRepo;

@Service
public class ProjectsServiceImpl implements ProjectsService{
	
	@Autowired
	private ProjectsRepo projRepo;

	@Override
	public Projects addProjects(Projects project) {
	
		return projRepo.save(project);
	}
	
	public List<Projects> getAllProject(){
		return projRepo.findAll();
	}

	@Override
	public void deleteProj(int id) {
		projRepo.deleteById(id);
		
	}
	
	
	public Projects getProjectsById(int id) {
		Optional<Projects> proj = projRepo.findById(id);
		if(proj.isPresent()) {
			return proj.get();
		}
		return null;
		
	}

	


}
