package com.codewithekka.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codewithekka.model.Emp_Querys;
import com.codewithekka.model.Projects;
import com.codewithekka.repository.EmpQueryRepo;

@Service
public class EmpQueryServiceImpl implements EmpQueryService{


	@Autowired
	private EmpQueryRepo repo;
	
	@Override
	public Emp_Querys question(Emp_Querys question,int id) {
		LocalDate date =LocalDate.now();
		question.setAnswer("No Response...");
		question.setDate(date);
		return repo.save(question);
	}

	@Override
	public void deleteQuery(int id) {
		// TODO Auto-generated method stub
		repo.deleteById(id);
	}

	@Override
	public Emp_Querys getQueryByid(int id) {
		// TODO Auto-generated method stub
		Optional<Emp_Querys> query=repo.findById(id);
		if(query.isPresent()) {
			return query.get();
		}
		return null;
	}

	@Override
	public Emp_Querys answer(Emp_Querys answer) {

		return repo.save(answer);
	}

	@Override
	public List<Emp_Querys> getAllQuerys() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}
	
	
	

}
