package com.codewithekka.model;

import java.time.LocalDate;
// import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;


@Data
@Entity
public class Emp_Querys {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String question;
	private String answer;
	private LocalDate date;

	@ManyToOne
	private EmployeeDtls emp;
}
