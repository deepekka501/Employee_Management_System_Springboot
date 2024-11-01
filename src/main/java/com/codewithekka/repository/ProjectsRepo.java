package com.codewithekka.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codewithekka.model.ApplyLeave;
import com.codewithekka.model.Projects;


@Repository
public interface ProjectsRepo extends JpaRepository<Projects,Integer>{

//	@Query("SELECT p FROM Projects p INNER JOIN Department d ON p.dept= d.id")
//	public List<Projects> findByDeptName();
//	private String projname;
//	private String aboutproj;
//	private String projdeadline;
//	private String dept_name;
	
//	@Query("SELECT new com.codewithekka.dto.ProjectResponse(e.id,e.projname, e.aboutproj,"
//			+ " e.projdeadline,  d.dept_name) "
//			+ "From Projects e JOIN e.dept d")
//	public List<ProjectResponse> getProjJoinInfo();
	@Query("select u From Projects u where u.dept.id= ?1")
	public List<Projects> getProjByDept(@Param("n") Integer deptid);
	
}
