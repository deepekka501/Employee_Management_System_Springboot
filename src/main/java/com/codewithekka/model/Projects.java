package com.codewithekka.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Projects {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String projname;
	private String aboutproj;
	private String projdeadline;
	
	@ManyToOne
	private Department dept;
	
	@OneToMany(mappedBy="projid")
	private List<Employee_Working_Project> workingon;
	
//	@ManyToMany
//	@JoinTable(name="working_on",
//				joinColumns=@JoinColumn(name="pid"),
//				inverseJoinColumns=@JoinColumn(name="eid"))
//	private List<EmployeeDtls> employee;
	
}
