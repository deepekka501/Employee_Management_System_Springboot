package com.codewithekka.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.codewithekka.model.ApplyLeave;
import com.codewithekka.model.Department;
import com.codewithekka.model.Emp_Querys;
import com.codewithekka.model.EmployeeDtls;
import com.codewithekka.model.Employee_Working_Project;
import com.codewithekka.model.Projects;
import com.codewithekka.repository.EmpQueryRepo;
import com.codewithekka.repository.EmployeeRepository;
import com.codewithekka.repository.Employee_Working_Project_Repository;
import com.codewithekka.repository.leaveRepository;
import com.codewithekka.service.EmpQueryServiceImpl;
import com.codewithekka.service.Emp_Working_Proj_Service_impls;
import com.codewithekka.service.LeaveServiceImpl;
import com.codewithekka.service.ProjectsServiceImpl;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/employee")
public class EmployeeController {
	
	@Autowired
	private EmployeeRepository empRepo;
	@Autowired
	private LeaveServiceImpl leaveSrvice;
	
	@Autowired
	private ProjectsServiceImpl projService;
	
	@Autowired
	private leaveRepository leaveRepo;
	
	@Autowired
	private EmpQueryRepo queryRepo;
	
	
	@Autowired
	private Employee_Working_Project_Repository ewpRepo;
	
	@Autowired
	private Emp_Working_Proj_Service_impls ewpService;
	
	@Autowired
	private EmpQueryServiceImpl empQuery;
	
	
	
	
	@ModelAttribute
	private void employeeDetails(Model m, Principal p) {
		
		String email=p.getName();
		EmployeeDtls emp=empRepo.findByEmail(email);
		m.addAttribute("emp",emp);
	}
	

	@ModelAttribute
	private void AllTables(Model m,Principal p,HttpSession session) {
		String email=p.getName();
		EmployeeDtls emp=empRepo.findByEmail(email);
		
		List<Employee_Working_Project> proj=ewpRepo.getProjByEmpId(emp.getId());

		m.addAttribute("proj",proj);
		
		List<ApplyLeave> allleave=leaveRepo.getLeaveByEmpId(emp.getId());
		m.addAttribute("allleave",allleave);
		
		List<ApplyLeave> leave=leaveRepo.getLeaveByEmpIdApprove(emp.getId(),"Approved");
		m.addAttribute("leave",leave);
		
		List<Emp_Querys> querys=queryRepo.getMyQuerys(emp.getId());
//		.getLeaveByEmpId(emp.getId());
		m.addAttribute("querys",querys);
	}
	
	
	
	@GetMapping("/")
	public String emphome(){
		return "employee/emphome";
	}
	@GetMapping("/profile")
	public String viewprofile() {
		return "employee/viewprofile";
	}
	@GetMapping("/editprofile")
	public String editprofile() {
		return "employee/editprofile";
	}
	
	@GetMapping("/leave")
	public String leave(@ModelAttribute ApplyLeave leave) {
		
		return "employee/leave";
	}
	
	@PostMapping("/applyLeave")
	public String applyLeave(@ModelAttribute ApplyLeave leave) {
//		leave.setEmp(eid);
		
		leaveSrvice.applyLeave(leave);
		return "redirect:/employee/applications";
		
	}
	

	@GetMapping("/empquery")
	public String empquery(@ModelAttribute Emp_Querys empquery) {
		
		return "employee/AskQuery";
	}
	
	@PostMapping("/question")
	public String question(@ModelAttribute Emp_Querys empquery,Principal p) {
//		leave.setEmp(eid);
		

		String email=p.getName();
		EmployeeDtls emp=empRepo.findByEmail(email);
		
		empQuery.question(empquery, emp.getId());
		
		return "redirect:/employee/empquery";
		
	}
	@GetMapping("/getMyQuestion")
	public String viewmyQuestion(Principal p, Model m) {
		
		String email=p.getName();
		EmployeeDtls emp=empRepo.findByEmail(email);
		List<Emp_Querys> querys=queryRepo.getMyQuerys(emp.getId());
//				.getLeaveByEmpId(emp.getId());
		m.addAttribute("querys",querys);
		
		return "employee/viewMyQuestion";
		
	}
	@GetMapping("/getallQuestion")
	public String viewallquerys( Model m) {
		List<Emp_Querys> querys=queryRepo.findAll();
//				.getLeaveByEmpId(emp.getId());
		m.addAttribute("querys",querys);
		return "employee/viewallquerys";
		
	}
	
	@GetMapping("/applications")
	public String viewLeave(Principal p, Model m) {
		
		String email=p.getName();
		EmployeeDtls emp=empRepo.findByEmail(email);
		List<ApplyLeave> leave=leaveRepo.getLeaveByEmpId(emp.getId());
		m.addAttribute("leave",leave);
		
		return "employee/viewLeaves";
		
	}
	
	@GetMapping("/projects")
	public String projects(@RequestParam(value="id",required=false)Integer eid,Principal p, Model m) {
//		Projects p= projService.getProjectsById(urlParameter);

		String email=p.getName();
		EmployeeDtls emp=empRepo.findByEmail(email);
		
		List<Employee_Working_Project> proj=ewpRepo.getProjByEmpId(emp.getId());
//		System.out.print(proj);
		m.addAttribute("proj",proj);
		return "employee/viewProjects";
	}
}
