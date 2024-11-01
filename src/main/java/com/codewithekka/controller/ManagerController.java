package com.codewithekka.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.codewithekka.model.ApplyLeave;
import com.codewithekka.model.Department;
import com.codewithekka.model.EmployeeDtls;
import com.codewithekka.model.Employee_Working_Project;
import com.codewithekka.model.Projects;
import com.codewithekka.repository.DepartmentRepo;
import com.codewithekka.repository.EmployeeRepository;
import com.codewithekka.repository.Employee_Working_Project_Repository;
import com.codewithekka.repository.ProjectsRepo;
import com.codewithekka.repository.leaveRepository;
import com.codewithekka.service.DeptServiceImpl;
import com.codewithekka.service.Emp_Working_Proj_Service_impls;
import com.codewithekka.service.EmployeeServiceImpl;
import com.codewithekka.service.LeaveServiceImpl;
import com.codewithekka.service.ProjectsServiceImpl;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/manager")
public class ManagerController {

	@Autowired
	private EmployeeRepository empRepo;
	@Autowired
	private EmployeeServiceImpl empService;
	@Autowired
	private DepartmentRepo deptRepo;
	@Autowired
	private DeptServiceImpl deptService;
	
	@Autowired
	private leaveRepository leaveRepo;
	
	@Autowired
	private Employee_Working_Project_Repository ewpRepo;
	
	@Autowired
	private Emp_Working_Proj_Service_impls ewpService;
	
	@Autowired
	private ProjectsRepo projRepo;
	
	@Autowired
	private ProjectsServiceImpl projService;
	
	@Autowired
	private LeaveServiceImpl leaveService;
	
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
		
		
//		Department dept_id=emp.getDept_id();
		
		List<EmployeeDtls> empall=empRepo.getEmpByDept("ROLE_EMPLOYEE", emp.getDept_id());
		m.addAttribute("employee",empall);
		
		List<Projects> proj=projRepo.getProjByDept(emp.getDept_id().getId());
		m.addAttribute("project",proj);
		
		List<Employee_Working_Project> ewp=ewpService.viewAllWorkingProj(emp.getDept_id().getId());
		m.addAttribute("empworks",ewp);
		
		List<ApplyLeave> leave=leaveRepo.getLeaveByDept(emp.getDept_id().getId());
		m.addAttribute("leaveapp",leave);
		
	}
	
	
	
	
	
	
	@GetMapping("/")
	public String home() {
		return "manager/home";
	}
	@GetMapping("/profile")
	public String viewprofile() {
		return "manager/viewprofile";
	}
	
	//Get all employee
	@GetMapping("/employees")
	public String employees(Principal p, Model m) {
		
		String email=p.getName();
		EmployeeDtls emp=empRepo.findByEmail(email);
		Department dept_id=emp.getDept_id();
		
		List<EmployeeDtls> empall=empRepo.getEmpByDept("ROLE_EMPLOYEE", dept_id);
		
		m.addAttribute("empall",empall);
		return "/manager/employee";
	}
	
	//View Employee Data
	@GetMapping("/viewemp")
	public String editemp(@RequestParam(value="id",required=false)int id,Model m) {
		EmployeeDtls e= empService.getEmpById(id);
		List<Employee_Working_Project> proj=ewpRepo.getProjByEmpId(id);
		
		m.addAttribute("empl", e);
		m.addAttribute("proj",proj);
		return "/manager/viewemployee";
	}
	
	
	
	// Here all leave by id.....
	@GetMapping("/application")
	public String viewLeave(Principal p, Model m) {
		
		System.out.println("this is principal"+ p.getName());
		String email=p.getName();
		EmployeeDtls emp=empRepo.findByEmail(email);
		System.out.println("this is principal"+ emp.getDept_id().getId());
		//leave where employee dept==dept.getdept;
		List<ApplyLeave> leave=leaveRepo.getLeaveByDept(emp.getDept_id().getId());
		m.addAttribute("leave",leave);
		
		return "manager/viewLeaves";
		
	}
	@GetMapping("/approveleave")
	public String viewLeaveStatus(@RequestParam(value="id",required=false)Integer leaveid,Model m) {
//		projService.addProjects(proj);
		ApplyLeave le=leaveService.getleavesById(leaveid);
		
//		leaveRepo.deleteById(leaveid);
		m.addAttribute("le",le);
		
		
		return "/manager/approvereject";
	}
	@PostMapping("/approveReject")
	public String approveReject(@ModelAttribute ApplyLeave e, HttpSession session) {
		
		leaveRepo.save(e);
	
		
		return "redirect:/manager/application";
		
	}
	
	
																				// here add projects 
	@GetMapping("/addproject") 
	public String projects(Model m) {
		List<Department> deptall=deptService.getAllDept();
		m.addAttribute("deptall",deptall);
		return "/manager/addprojects";
	}
	@PostMapping("/addprojects")
	public String addProject(@ModelAttribute Projects projects, HttpSession session) {
		//Department department= deptService.addDept(dept);
		Projects proj=projService.addProjects(projects);
		
		return "redirect:/manager/addproject";
	}
	
	
	
	
	
	
	
	
	
	
	
	//Here alll project by dept
	
	
	@GetMapping("/allprojects")
	public String viewProj(Principal p, Model m) {
		
		System.out.println("this is principal"+ p.getName());
		String email=p.getName();
		EmployeeDtls emp=empRepo.findByEmail(email);
		System.out.println("this is principal"+ emp.getDept_id().getId());
		//leave where employee dept==dept.getdept;
		List<Projects> proj=projRepo.getProjByDept(emp.getDept_id().getId());
		m.addAttribute("proj",proj);
		
		return "/manager/viewproj";
		
	}
	
	@GetMapping("/allprojects/delete/{id}")
	public String deleteProj(@PathVariable int id, HttpSession session ) {
		projService.deleteProj(id);
		session.setAttribute("msg","Project Data Deleted Sucessfully...");
		return "redirect:/manager/allprojects";
	}
	
	@GetMapping("/assignproj")
	public String assignProj(@RequestParam(value="id",required=false)int id,Model m) {
		
		//parameter
//		System.out.println(urlParameter+"urlParameter");
		Projects p= projService.getProjectsById(id);
		m.addAttribute("proj", p);
		long pdeptid=p.getDept().getId();
		Department dept_id=deptRepo.getById((int) pdeptid);
		List<EmployeeDtls> empall=empRepo.getEmpByDept("ROLE_EMPLOYEE",dept_id);
		m.addAttribute("empall",empall);
		return "/manager/assignprojects";
	}
	@PostMapping("/assigned")
	public String assigned(@ModelAttribute Employee_Working_Project empWp, 
			HttpSession session){
		
		LocalDate date = LocalDate.now();
		empWp.setAssigndate(date);
		empWp.setStatus("Assigned, Working Stage Now");
//		System.out.println(empWp);
		Employee_Working_Project empWorkingProj=ewpService.assignProj(empWp);
		
		return "redirect:/manager/workingProj";
	}
	
	
	@GetMapping("/workingProj")
	public String viewAllAssingedProj(Model m, Principal p) {
		String email=p.getName();
		EmployeeDtls emp=empRepo.findByEmail(email);
		List<Employee_Working_Project> ewp=ewpService.viewAllWorkingProj(emp.getDept_id().getId());
		m.addAttribute("ewp",ewp);
		return "/manager/assingproj";
		
	}
	@GetMapping("/workingProj/delete/{id}")
	public String deleteWorkingProj(@PathVariable int id, HttpSession session ) {
		ewpService.deleteProj(id);
		session.setAttribute("msg","Project Data Deleted Sucessfully...");
		return "redirect:/manager/workingProj";
	}
	
}
