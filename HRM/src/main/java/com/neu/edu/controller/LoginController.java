package com.neu.edu.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.neu.edu.dao.EmployeeDao;
import com.neu.edu.dao.EngineerDao;
import com.neu.edu.dao.ManagerDao;
import com.neu.edu.model.Employee;
import com.neu.edu.model.Engineer;
import com.neu.edu.model.Manager;


@Controller
public class LoginController {

	static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired
	private ManagerDao managerDao;
	
	@Autowired
	private EngineerDao engineerDao;
	
	@RequestMapping(value = "/login", method= RequestMethod.POST)
	protected ModelAndView login(HttpServletRequest request)  {
		
		logger.info("In login Post");
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		String userName = "";
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			userName = ((UserDetails)principal).getUsername();
		} else {
			userName = principal.toString();
		}
		
		logger.info("Emp : " + userName);
		
		HttpSession session = request.getSession();
		session.setAttribute("employee", employeeDao.getEmployeeByEmail(userName));
		
		return new ModelAndView("view-user");
		
	}
	
	@RequestMapping(value = "/Access_Denied", method= RequestMethod.GET)
	protected ModelAndView redirect(HttpServletRequest request)  {
		
		String message="Access Denied for the Resource. Contact admin for rights.";
		return new ModelAndView("access-denied", "message", message);
		
	}
	
	@RequestMapping(value = "/login", method= RequestMethod.GET)
	protected ModelAndView redirectLogin(HttpServletRequest request)  {
		
		logger.info("In login Get");
		
		return new ModelAndView("login");
		
	}
	
	@RequestMapping(value ="/user", method= RequestMethod.GET)
	public ModelAndView viewUser(HttpServletRequest request) {
		
		logger.info("In login Post");
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		String userName = "";
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			userName = ((UserDetails)principal).getUsername();
		} else {
			userName = principal.toString();
		}
		
		logger.info("Emp : " + userName);
		
		HttpSession session = request.getSession();
		
		Employee emp = employeeDao.getEmployeeByEmail(userName);
		if(emp.getRole().getRolename().equalsIgnoreCase("manager"))
		{
			Manager manager = managerDao.getManager(emp.getEmpId());
			session.setAttribute("employee", manager);
		}
		else if(emp.getRole().getRolename().equalsIgnoreCase("engineer")) {
			Engineer engineer = engineerDao.getEngineer(emp.getEmpId());
			session.setAttribute("employee", engineer);
		}
		else {
			session.setAttribute("employee", employeeDao.getEmployeeByEmail(userName));
		}
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("view-user");
		return mv;
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutAction(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    return "redirect:/login";
		
	}
}
