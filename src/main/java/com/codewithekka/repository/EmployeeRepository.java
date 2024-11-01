package com.codewithekka.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codewithekka.model.Department;
import com.codewithekka.model.EmployeeDtls;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeDtls,Integer>{

	public boolean existsByEmail(String email);
	
	
	public EmployeeDtls findByEmail(String email);
	
	
	@Query("select u From EmployeeDtls u where u.role= :n")
	public List<EmployeeDtls> getEmpByRole(@Param("n") String role);
	
	//Query for select employee by role and dept
	@Query("select u From EmployeeDtls u where u.role= :x AND u.dept_id= :n")
	public List<EmployeeDtls> getEmpByDept(@Param("x") String role,@Param("n") Department dept_id);
	
	
	
//	@Query("SELECT new com.codewithekka.dto.EmployeeResponse(e.id,e.firstname, e.lastname,"
//			+ " e.mobile, e.email, e.address, e.city, e.state,e.pincode,e.role, d.dept_name) "
//			+ "From EmployeeDtls e JOIN e.dept_id d where e.role= :n")
//	public List<EmployeeResponse> getJoinInfo(@Param("n") String role);
	
}
