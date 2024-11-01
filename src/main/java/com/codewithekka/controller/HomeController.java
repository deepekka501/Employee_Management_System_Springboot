package com.codewithekka.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.codewithekka.model.Department;
import com.codewithekka.model.EmployeeDtls;
import com.codewithekka.service.DeptServiceImpl;
import com.codewithekka.service.EmployeeService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private DeptServiceImpl deptService;
	
	@GetMapping("/")
	public String index(Model m) {
		List<Department> deptall=deptService.getAllDept();
		m.addAttribute("deptall",deptall);
		return "index";
	}
	
//	@GetMapping("/signin")
//	public String login() {
//		return "login";
//	}
	
	@GetMapping("/register")
	public String register(Model m) {
		List<Department> deptall=deptService.getAllDept();
		m.addAttribute("deptall",deptall);
		return "register";
	}
	
	
	@PostMapping("/createEmployee")
	public String createemployee(@ModelAttribute EmployeeDtls employee, HttpSession session) {
		
		boolean f=employeeService.checkEmail(employee.getEmail());
		if(f) {
			session.setAttribute("msg", "Email Id already exists");
		}
		else {
			//System.out.println(employee);
			EmployeeDtls empDtls= employeeService.createEmployee(employee);
			if(empDtls!=null) {
				session.setAttribute("msg", "Registered Successfully");
				
			}
			else {
				session.setAttribute("msg", "Something went worng");
			}
		}
		
		return "redirect:/";
	}
}
