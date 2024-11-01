package com.codewithekka.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.codewithekka.config.EmailSenderService;
import com.codewithekka.model.ApplyLeave;
import com.codewithekka.model.Department;
import com.codewithekka.model.Emp_Querys;
import com.codewithekka.model.EmployeeDtls;
import com.codewithekka.model.Employee_Working_Project;
import com.codewithekka.model.Projects;
import com.codewithekka.repository.DepartmentRepo;
import com.codewithekka.repository.EmpQueryRepo;
import com.codewithekka.repository.EmployeeRepository;
import com.codewithekka.repository.Employee_Working_Project_Repository;
import com.codewithekka.repository.ProjectsRepo;
import com.codewithekka.repository.leaveRepository;
import com.codewithekka.service.DeptServiceImpl;
import com.codewithekka.service.EmpQueryService;
import com.codewithekka.service.Emp_Working_Proj_Service_impls;
import com.codewithekka.service.EmployeeServiceImpl;
import com.codewithekka.service.LeaveServiceImpl;
import com.codewithekka.service.ProjectsServiceImpl;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private EmployeeRepository empRepo;
	@Autowired
	private EmployeeServiceImpl service;
	
	@Autowired
	private DepartmentRepo deptRepo;
	
	@Autowired
	private DeptServiceImpl deptService;
	
	@Autowired
	private ProjectsServiceImpl projService;
	
	
	@Autowired
	private Emp_Working_Proj_Service_impls ewpService;
	

	@Autowired
	private leaveRepository leaveRepo;
	
	@Autowired
	private EmpQueryRepo queryRepo;
	
	@Autowired
	private EmpQueryService queryService;
	
	@Autowired
	private LeaveServiceImpl leaveService;
	
	@Autowired
	private EmailSenderService eSender;
	
	
	@ModelAttribute
	private void employeeDetails(Model m, Principal p) {
		String email=p.getName();
		EmployeeDtls emp=empRepo.findByEmail(email);
		m.addAttribute("emp",emp);
	}
	@ModelAttribute
	private void AllTables(Model m,HttpSession session) {
		List<EmployeeDtls> allemployee=empRepo.findAll();
		m.addAttribute("allemployee",allemployee);
		
		List<EmployeeDtls> allmanager=empRepo.getEmpByRole("ROLE_MANAGER");
		m.addAttribute("allmanager",allmanager);
		
		List<EmployeeDtls> empall=empRepo.getEmpByRole("ROLE_EMPLOYEE");
		m.addAttribute("empall",empall);
		
		List<Projects> proj=projService.getAllProject();
		m.addAttribute("proj",proj);
		
		List<Department> deptall=deptService.getAllDept();
		m.addAttribute("deptall",deptall);
		
		List<ApplyLeave> leave=leaveService.getAllLeave();
		m.addAttribute("leave",leave);
		
		List<Emp_Querys> query=queryService.getAllQuerys();
		m.addAttribute("query",query);
	}
	
	
	@GetMapping("/")
	public String home() {
		return "/admin/indexAdmin";
	}
	@GetMapping("/profile")
	public String viewprofile() {
		return "/admin/viewprofile";
	}
	@GetMapping("/editprofile")
	public String editprofile() {
		
		
		return "/admin/editprofile";
	}

	
	//********************************************************************************************
	//*************************************Employee start here*************************************
	//********************************************************************************************
	
	
	@GetMapping("/addemp")
	public String addemp(Model m) {
		List<Department> deptall=deptService.getAllDept();
		m.addAttribute("deptall",deptall);
		return "/admin/addemployee";
	}
	
	
	@PostMapping("/addemp")
	public String addemployee(@ModelAttribute EmployeeDtls employee, HttpSession session) {
		boolean f=service.checkEmail(employee.getEmail());
		if(f) {
			session.setAttribute("msg", "Email Id already exists");
		}
		else {
			//System.out.println(employee);
			EmployeeDtls empDtls= service.addEmployee(employee);
			if(empDtls!=null) {
				try {
					//Here is emal function
					eSender.sendEmail(empDtls.getEmail(),
							"Account Generated Successfully", 
							"Welcome to Employee Management System."
							+ " Hi "+empDtls.getFirstname()+" Your Account has been generated Successfully. Your"
							+" Department is "+empDtls.getDept_id().getDept_name()+". Thank you.");

					
				}catch(Exception e) {
					System.out.print(e);
				}
				
			
				session.setAttribute("msg", "Registered Successfully");
			}
			else {
				session.setAttribute("msg", "Something went worng");
			}
		}
		
		return "redirect:/admin/addemp";
	}
	
	
	// using this method we can view only Employee
	@GetMapping("/viewemp")
	public String viewemp(Model m) {
		List<EmployeeDtls> empall=empRepo.getEmpByRole("ROLE_EMPLOYEE");
//		List<EmployeeDtls> empall=service.getAllEmp();
		
//		List<EmployeeResponse> empall=empRepo.getJoinInfo("ROLE_EMPLOYEE");
		
		m.addAttribute("empall",empall);
		return "/admin/viewemployee";
	}
	
								// This method is for only testing purpose
//	@GetMapping("/testviewemp")
//	public String testviewemp(Model m) {
//		List<EmployeeResponse> emall=empRepo.getJoinInfo("ROLE_EMPLOYEE");
//		m.addAttribute("emall",emall);
//		return "/admin/testallemp";
//	}
	
																		//Working fine 
	@GetMapping("/editemp")
	public String editemp(@RequestParam(value="id",required=false)int id,Model m) {
		EmployeeDtls e= service.getEmpById(id);
		m.addAttribute("emp", e);
		List<Department> deptall=deptService.getAllDept();
		m.addAttribute("deptall",deptall);
		return "/admin/editemployeedtls";
	}
																		// Not working properly
	@PostMapping("/update")
	public String updateEmp(@ModelAttribute EmployeeDtls e, HttpSession session) {
		service.addEmployee(e);
		session.setAttribute("msg","Employee Data Update Sucessfully...");
		return "redirect:/admin/viewemp";
	}
															// works fine
	@GetMapping("/viewemp/delete/{id}")
	public String deleteEmp(@PathVariable int id, HttpSession session ) {
		try {
			service.deleteEmp(id);
			session.setAttribute("msg","Employee Data Deleted Sucessfully...");
		}catch(Exception e) {
			System.out.println("Can't delete Employee" +e);
		}
		
		return "redirect:/admin/viewemp";
	}
	
	
	
	
	//********************************************************************************************
	//*************************************Employee Ends here*************************************
	//********************************************************************************************
	
	
	
	
	
	
	//********************************************************************************************
	//*************************************Manager here*************************************
	//********************************************************************************************
																//works fine 
	// using this method we can view only managers
	@GetMapping("/managers")
	public String viewmanager(Model m) {
		List<EmployeeDtls> allmanager=empRepo.getEmpByRole("ROLE_MANAGER");
//		List<EmployeeResponse> allmanager=empRepo.getJoinInfo("ROLE_MANAGER");
		m.addAttribute("allmanager",allmanager);
		return "admin/viewmanager";
	}
						
	@GetMapping("managers/delete/{id}")
	public String deleteManager(@PathVariable int id, HttpSession session ) {
		
		try {
			service.deleteEmp(id);
			session.setAttribute("msg","Employee Data Deleted Sucessfully...");
		}catch(Exception e) {
			System.out.println("Can't delete manager data" + e);
		}
		
		return "redirect:/admin/managers";
	}
	
	

	@GetMapping("/asignmanager")
	public String asignmanager() {
		return "admin/asignmanager";
	}
	
	
	//********************************************************************************************
	//*************************************Manager Ends here*************************************
	//********************************************************************************************
	
	

	//********************************************************************************************
	//*************************************Deptartment Start here*************************************
	//********************************************************************************************
	
	@GetMapping("/add_dept")
	public String adddept() {
		return "admin/adddept";
	}
	
	@PostMapping("/addDept")
	public String addDept(@ModelAttribute Department dept, HttpSession session) {
		deptService.addDept(dept);
		return "redirect:/admin/add_dept";
	}
	
	
	@GetMapping("/departments")
	public String viewdept(Model m) {
		List<Department> deptall=deptService.getAllDept();
//		System.out.println(deptall.size());
//		int numofdept=deptall.size();
		m.addAttribute("deptall",deptall);
		
		return "admin/viewdepts";
	}
	
	@GetMapping("/editdept")
	public String editdept(@RequestParam(value="id",required=false)int id,Model m) {
		
		Department dept=deptService.findDeptByid(id);
		m.addAttribute("dept",dept);
		
		return "admin/editdept";
	}
	
	@PostMapping("/editdeptdata")
	public String updateEmp(@ModelAttribute Department d, HttpSession session) {
		deptService.addDept(d);
		session.setAttribute("msg","Department Data Update Sucessfully...");
		return "redirect:/admin/departments";
	}
	
	@GetMapping("departments/delete/{id}")
	public String deleteDept(@PathVariable int id, HttpSession session ){
		try {
			deptService.deleteDept(id);
			session.setAttribute("msg","Department Data Deleted Sucessfully...");
		}
		catch(DataIntegrityViolationException e) {
			System.out.println("Can't Delete this department Employee/Project exist here...");
			session.setAttribute("msg","Can't Delete this department Employee/Project exist here...");
			
		}
		catch(Exception e) {
			System.err.println(e);
			
		}
		
		return "redirect:/admin/departments";
	}
	
	//********************************************************************************************
	//*************************************Deptartment ends here*************************************
	//********************************************************************************************
	
	
	
	//********************************************************************************************
	//*************************************Project Start here*************************************
	//********************************************************************************************
	
	
	//addprojects
	
	@GetMapping("/addproject") 
	public String projects(Model m) {
		List<Department> deptall=deptService.getAllDept();
		m.addAttribute("deptall",deptall);
		return "/admin/addprojects";
	}
	
	@PostMapping("/addprojects")
	public String addProject(@ModelAttribute Projects projects, HttpSession session) {
		//Department department= deptService.addDept(dept);
		projService.addProjects(projects);
		
		return "redirect:/admin/addproject";
	}
	
	
	@GetMapping("/allprojects") 
	public String viewprojects(Model m) {
		List<Projects> proj=projService.getAllProject();
//		List<ProjectResponse> proj=projRepo.getProjJoinInfo();
//		List<Projects> proj=projRepo.findByDeptName();
		m.addAttribute("proj",proj);
		return "/admin/viewprojects";
	}
	
	@GetMapping("/allprojects/delete/{id}")
	public String deleteProj(@PathVariable int id, HttpSession session ) {
		projService.deleteProj(id);
		session.setAttribute("msg","Project Data Deleted Sucessfully...");
		return "redirect:/admin/allprojects";
	}
	
	@GetMapping("/editproj")
	public String editProj(@RequestParam(value="id",required=false)int id,Model m) {
		
		List<Department> deptall=deptService.getAllDept();
		m.addAttribute("deptall",deptall);
		Projects p= projService.getProjectsById(id);
		m.addAttribute("proj", p);
		return "/admin/editproject";
	}
	
	
	@PostMapping("/updateproj")
	public String updateProj(@ModelAttribute Projects proj) {
		projService.addProjects(proj);
		return "redirect:/admin/allprojects";
	}
	
	
	//********************************************************************************************
	//*************************************Assing Project Start here*************************************
	//********************************************************************************************
	
	
	
//	@GetMapping("/allprojects/assignproj/{id}")     // here id = projectid
//	public String assignProj(@PathVariable int id,Model m) {
//		
//		Projects p= projService.getProjectsById(id);
//		m.addAttribute("proj", p);
//		long pdeptid=p.getDept().getId();
//		
//		Department dept_id=deptRepo.getById((int) pdeptid);
//		
//		List<EmployeeDtls> empall=empRepo.getEmpByDept("ROLE_EMPLOYEE",dept_id);
//		m.addAttribute("empall",empall);
//		
//		return "/admin/assignproject";
//	}
	
	@GetMapping("/assignproj")
	public String assignProj(@RequestParam(value="id",required=false)int urlParameter,Model m) {
		
		//parameter
//		System.out.println(urlParameter+"urlParameter");
		
		Projects p= projService.getProjectsById(urlParameter);
		m.addAttribute("proj", p);
		long pdeptid=p.getDept().getId();
		Department dept_id=deptRepo.getById((int) pdeptid);
		List<EmployeeDtls> empall=empRepo.getEmpByDept("ROLE_EMPLOYEE",dept_id);
		m.addAttribute("empall",empall);
		return "/admin/assignproject";
	}
		
	//This method is working fine
	@PostMapping("/assigned")
	public String assigned(@ModelAttribute Employee_Working_Project empWp, 
			HttpSession session){
		
		LocalDate date = LocalDate.now();
		empWp.setAssigndate(date);
		empWp.setStatus("Assigned, Working Stage Now");
//		System.out.println(empWp);
		ewpService.assignProj(empWp);
		
		return "redirect:/admin/allprojects";
	}
	
	@GetMapping("/workingProj")
	public String viewAllAssingedProj(Model m) {
		List<Employee_Working_Project> ewp=ewpService.viewAllAssingedProj();
		
		m.addAttribute("ewp",ewp);
		return "/admin/viewAssignedProj";
		
	}
	@GetMapping("/workingProj/delete/{id}")
	public String deleteWorkingProj(@PathVariable int id, HttpSession session ) {
		ewpService.deleteProj(id);
		session.setAttribute("msg","Project Data Deleted Sucessfully...");
		return "redirect:/admin/workingProj";
	}
	
	
	
	@GetMapping("/application")
	public String viewLeave( Model m) {
		List<ApplyLeave> leave=leaveRepo.findAll();
		
		m.addAttribute("leave",leave);
		
		return "/admin/leaveapplication";
		
	}
	@GetMapping("/query")
	public String viewquerys( Model m) {
		List<Emp_Querys> query=queryRepo.findAll();
		
		m.addAttribute("query",query);
		
		return "/admin/viewquery";
		
	}
	
	@GetMapping("/reply")
	public String replyQuery(@RequestParam(value="id",required=false)int urlParameter,Model m) {
		
		//parameter
//		System.out.println(urlParameter+"urlParameter");
		Emp_Querys que=queryService.getQueryByid(urlParameter);
//		Projects p= projService.getProjectsById(urlParameter);
		m.addAttribute("query", que);
		
		return "/admin/replyquery";
	}
	@PostMapping("/updatequery")
	public String updateQuery(@ModelAttribute Emp_Querys que, HttpSession session) {
		queryService.answer(que);
		session.setAttribute("msg","Department Data Update Sucessfully...");
		return "redirect:/admin/query";
	}
	
	
	@GetMapping("/query/delete/{id}")
	public String deleteQuery(@PathVariable int id, HttpSession session ) {
		queryService.deleteQuery(id);
		session.setAttribute("msg","Project Data Deleted Sucessfully...");
		return "redirect:/admin/query";
	}
	
	
	
	

}
