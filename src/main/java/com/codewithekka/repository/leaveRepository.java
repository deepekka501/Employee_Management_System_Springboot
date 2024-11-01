package com.codewithekka.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codewithekka.model.ApplyLeave;
import com.codewithekka.model.Employee_Working_Project;

@Repository
public interface leaveRepository extends JpaRepository<ApplyLeave,Integer>{

	@Query("select u From ApplyLeave u where u.emp.id= ?1")
	public List<ApplyLeave> getLeaveByEmpId(@Param("n") Integer empid);
	
	@Query("select u From ApplyLeave u where u.emp.id= ?1  AND u.status= ?2")
	public List<ApplyLeave> getLeaveByEmpIdApprove(@Param("n") Integer empid,String status);

	@Query("select u From ApplyLeave u where u.emp.dept_id.id= ?1")
	public List<ApplyLeave> getLeaveByDept(@Param("n") Integer deptid);
}
