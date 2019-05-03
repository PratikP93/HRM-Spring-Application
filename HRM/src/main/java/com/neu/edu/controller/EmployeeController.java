package com.neu.edu.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.neu.edu.dao.EmployeeDao;
import com.neu.edu.dao.EngineerDao;
import com.neu.edu.dao.ManagerDao;
import com.neu.edu.dao.ProjectDao;
import com.neu.edu.dao.RoleDao;
import com.neu.edu.model.Employee;
import com.neu.edu.model.Engineer;
import com.neu.edu.model.Manager;
import com.neu.edu.model.Project;
import com.neu.edu.model.Role;
import com.neu.edu.service.ManagerService;

@Controller
public class EmployeeController {
	static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	RoleDao roleDao;

	@Autowired
	EmployeeDao employeeDao;

	@Autowired
	ManagerDao managerDao;

	@Autowired
	EngineerDao engineerDao;

	@Autowired
	ProjectDao projDao;

	@Autowired
	ManagerService managerService;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@RequestMapping(value = "/employee", method = RequestMethod.GET)
	public ModelAndView viewEmployees(HttpServletRequest request) {
		if(request.getAttribute("employee") == null) {
            insertLoggedUserInSession(request);
        }
		
		
		String userName = "";

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
		} else {
			userName = principal.toString();
		}

		HttpSession session = request.getSession();
		session.setAttribute("employee", employeeDao.getEmployeeByEmail(userName));

		ModelAndView mv = new ModelAndView();
		List<Employee> employees = employeeDao.getEmployees();
		mv.addObject("employees", employees);
		mv.setViewName("manage-employee");
		return mv;
	}

	@RequestMapping(value = "/employee/{id}", method = RequestMethod.GET)
	public ModelAndView viewEmployee(@PathVariable("id") long id , HttpServletRequest request) {
		if(request.getAttribute("employee") == null) {
            insertLoggedUserInSession(request);
        }
		ModelAndView mv = new ModelAndView();
		Employee employee = employeeDao.getEmployeeById(id);
		
		if(employee.getRole().getRolename().equalsIgnoreCase("manager"))
		{
			Manager manager = managerDao.getManager(employee.getEmpId());
			mv.addObject("emp", manager);
		}
		else if(employee.getRole().getRolename().equalsIgnoreCase("engineer")) {
			Engineer engineer = engineerDao.getEngineer(employee.getEmpId());
			mv.addObject("emp", engineer);
		}
		else {
			mv.addObject("emp", employee);
		}
		
		
		mv.setViewName("edit-employee");
		return mv;
	}

	@RequestMapping(value = "/managers", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Manager> getManagers(HttpServletRequest request) {

		if(request.getAttribute("employee") == null) {
            insertLoggedUserInSession(request);
        }
		logger.info("In managers controller");

		return managerService.getManagers();
		// return managers;
	}

	@RequestMapping(value = "/manager/projects", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Set<Project> getManagerProjects(@RequestParam("managerId") long id, HttpServletRequest request) {

		if(request.getAttribute("employee") == null) {
            insertLoggedUserInSession(request);
        }
		logger.info("In managers projects controller");

		return managerDao.getProjectsByManager(id);
		// return managers;
	}

	@RequestMapping(value = "/employee/create", method = RequestMethod.GET)
	public ModelAndView viewCreateEmployee(HttpServletRequest request) {
		if(request.getAttribute("employee") == null) {
            insertLoggedUserInSession(request);
        }
		String userName = "";

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
		} else {
			userName = principal.toString();
		}

		HttpSession session = request.getSession();
		session.setAttribute("employee", employeeDao.getEmployeeByEmail(userName));

		ModelAndView mv = new ModelAndView();
		List<Role> roles = roleDao.getRoles();
		mv.addObject("roles", roles);
		mv.addObject("emp", new Employee());
		mv.setViewName("create-employee");
		return mv;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	@RequestMapping(value = "/employee/create", method = RequestMethod.POST)
	public ModelAndView createEmployee(@Valid @ModelAttribute("emp") Employee employee, BindingResult results,
			HttpServletRequest request) {
		if(request.getAttribute("employee") == null) {
            insertLoggedUserInSession(request);
        }
		System.out.println("ubfbhjbvdbvvh");
try {
		if (results.hasErrors()) {
			System.out.println("Has Errors:");
			String message = "Failed to create employee:::::: : " + employee.getFirstName();
			return new ModelAndView("create-employee", "message", message);

		} else {
			int result;
			String message = "";
			Role role = roleDao.getRoleFromId(Long.parseLong(request.getParameter("roles")));
			employee.setRole(role);
			String password = employee.getPassword();
			String  psw = bCryptPasswordEncoder.encode(employee.getPassword());
			if (role.getRolename().equalsIgnoreCase("manager")) {
				Manager manager = new Manager();
				manager.setEmail(employee.getEmail());
				manager.setFirstName(employee.getFirstName());
				manager.setLastName(employee.getLastName());
				manager.setLeaveCount(employee.getLeaveCount());
				manager.setPhone(employee.getPhone());
				manager.setRole(role);
				manager.setWage(employee.getWage());
				manager.setPassword(psw);
				result = managerDao.registerManager(manager);
				SendEmail(employee.getEmail(), password);
			} else if (role.getRolename().equalsIgnoreCase("engineer")) {
				Engineer eng = new Engineer();
				eng.setEmail(employee.getEmail());
				eng.setFirstName(employee.getFirstName());
				eng.setLastName(employee.getLastName());
				eng.setLeaveCount(employee.getLeaveCount());
				eng.setPhone(employee.getPhone());
				eng.setRole(role);
				eng.setWage(employee.getWage());
				eng.setPassword(psw);
				eng.setManager(managerDao.getManager(Long.parseLong(request.getParameter("manager"))));
				eng.setProject(projDao.getProjectById(Long.parseLong(request.getParameter("project"))));
				result = engineerDao.registerEngineer(eng);
				SendEmail(employee.getEmail(), password);
			} else {
				
				employee.setPassword(psw);
				result = employeeDao.registerEmployee(employee);
				SendEmail(employee.getEmail(), password);
				
			}
			if (result > 0) {
				message = "Successfully created new employee : " + employee.getFirstName();
				return new ModelAndView("success-page", "message", message);
			} else {
				message = "Failed to create employee : " + employee.getFirstName();
				return new ModelAndView("success-page", "message", message);
			}
		}
}
catch(Exception e) {
	e.printStackTrace();
	return null;
}

	}

	@RequestMapping(value = "/employee/update/{id}", method = RequestMethod.POST)
	public ModelAndView updateEmployee(@PathVariable("id") long id, @ModelAttribute("emp") Employee employee,
			HttpServletRequest request) {
		if(request.getAttribute("employee") == null) {
            insertLoggedUserInSession(request);
        }

		String message = "";
		
		Role role = roleDao.getRoleFromId(Long.parseLong(request.getParameter("roles")));
		Employee newEmployee = employee;
		newEmployee.setRole(role);
		int result = employeeDao.updateEmployee(id, newEmployee);
		if (result > 0) {
			message = "Successfully updated employee : " + newEmployee.getFirstName();
			return new ModelAndView("success-page", "message", message);
		} else {
			message = "Failed to update employee : " + newEmployee.getFirstName();
			return new ModelAndView("success-page", "message", message);
		}

	}
	
	public void SendEmail(String emailId, String password) throws EmailException {
		Email email = new SimpleEmail();
		email.setHostName("smtp.googlemail.com");
		email.setSmtpPort(465);
		//User your gmail username and password
		email.setAuthenticator(new DefaultAuthenticator("me.pratikpatil193@gmail.com", "Newuser@123"));
		email.setSSLOnConnect(true);
		email.setFrom("no-reply@msis.neu.edu");
		email.setSubject("TestMail");
		email.setMsg("Your Account is created and Password is :  "+ password);
		email.addTo(emailId);
		email.send();
	}
	
	public void insertLoggedUserInSession(HttpServletRequest request) {
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
            HttpSession ses = request.getSession();
            Employee employee = employeeDao.getEmployeeByEmail(username);
            if(employee.getRole().getRolename().equalsIgnoreCase("manager"))
            {
                Manager mang = managerDao.getManager(employee.getEmpId());
                ses.setAttribute("employee", mang);
            }
            else if(employee.getRole().getRolename().equalsIgnoreCase("engineer")) {
                Engineer eng = engineerDao.getEngineer(employee.getEmpId());
                ses.setAttribute("employee", eng);
            }
            else {
                ses.setAttribute("employee", employee);
            }
        } 
        
        
    }
	
	
	

}
