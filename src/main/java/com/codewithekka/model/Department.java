package com.codewithekka.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Department {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String dept_name;
	private String dept_discription;
	
	@OneToMany(mappedBy="dept_id")
	private List<EmployeeDtls> employee;
	
	@OneToMany(mappedBy="dept")
	private List<Projects> projects;
	
}
