package com.codewithekka.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class ApplyLeave {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String startdate;
	private String lastdate;
	private String reason;
	private String status;
	
	@ManyToOne
	private EmployeeDtls emp;
}
