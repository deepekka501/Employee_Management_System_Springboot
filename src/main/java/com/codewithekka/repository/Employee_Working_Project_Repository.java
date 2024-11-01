package com.codewithekka.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codewithekka.model.EmployeeDtls;
import com.codewithekka.model.Employee_Working_Project;
import com.codewithekka.model.Projects;



@Repository
public interface Employee_Working_Project_Repository extends JpaRepository<Employee_Working_Project,Integer>{
	@Query("select u From Employee_Working_Project u where u.empid.id= ?1")
	public List< Employee_Working_Project> getProjByEmpId(@Param("n") Integer empid);
	
	@Query("select u From Employee_Working_Project u where u.projid.dept.id= ?1")
	public List<Employee_Working_Project> getWorkingProjByDept(@Param("n") Integer deptid);
}
