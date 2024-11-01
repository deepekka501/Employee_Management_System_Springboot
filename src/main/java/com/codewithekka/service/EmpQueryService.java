package com.codewithekka.service;

import java.util.List;

import com.codewithekka.model.Emp_Querys;

public interface EmpQueryService {

	public Emp_Querys question(Emp_Querys question,int id);
	public void deleteQuery(int id);
	public Emp_Querys getQueryByid(int id);
	public Emp_Querys answer(Emp_Querys answer);
	public List<Emp_Querys> getAllQuerys();
}
