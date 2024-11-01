package com.codewithekka.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class EmployeeDtls {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String firstname;
	private String lastname;
	private String dob;
	private String mobile;
	private String gender;
	private String email;
	private String password;
	private String address;
	private String city;
	private String state;
	private String pincode;
	private String role;
	
	@ManyToOne
	private Department dept_id;
	
	@OneToMany(mappedBy="emp")
	private List<ApplyLeave> leave;
	
	@OneToMany(mappedBy="emp")
	private List<Emp_Querys> querys;
	
	@OneToMany(mappedBy="empid")
	private List<Employee_Working_Project> workingproj;
	
//	@ManyToMany(mappedBy="employee")
//	private List<Projects> projects;
	
	
	

}
