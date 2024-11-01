package com.codewithekka.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codewithekka.model.ApplyLeave;
import com.codewithekka.model.Emp_Querys;


@Repository
public interface EmpQueryRepo extends JpaRepository<Emp_Querys,Integer>{

	@Query("select u From Emp_Querys u where u.emp.id= ?1")
	public List<Emp_Querys> getMyQuerys(@Param("n") Integer empid);
}
