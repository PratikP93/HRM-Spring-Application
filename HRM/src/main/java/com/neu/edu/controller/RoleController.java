package com.neu.edu.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.neu.edu.dao.EmployeeDao;
import com.neu.edu.dao.EngineerDao;
import com.neu.edu.dao.ManagerDao;
import com.neu.edu.dao.RoleDao;
import com.neu.edu.model.Employee;
import com.neu.edu.model.Engineer;
import com.neu.edu.model.Manager;
import com.neu.edu.model.Role;

@Controller
public class RoleController {

	static final Logger logger = LoggerFactory.getLogger(RoleController.class);
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	EmployeeDao empDao;
	
	@Autowired
	ManagerDao mangDao;
	
	@Autowired
	EngineerDao engDao;
	
	
	@RequestMapping(value ="/roles", method= RequestMethod.GET)
	public ModelAndView viewRoles(HttpServletRequest request) {
		
		logger.info("In Role Get");
		if(request.getAttribute("employee") == null) {
            insertLoggedUserInSession(request);
        }
		String userName = "";
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			userName = ((UserDetails)principal).getUsername();
		} else {
			userName = principal.toString();
		}
		
		logger.info("Emp : " + userName);
		
		HttpSession session = request.getSession();
		session.setAttribute("employee", empDao.getEmployeeByEmail(userName));
		
		ModelAndView mv = new ModelAndView();
		List<Role> roles = roleDao.getRoles();
		mv.addObject("roles", roles);
		mv.addObject("role", new Role());
		mv.setViewName("manage-roles");
		return mv;
	}
	
	
	
	@RequestMapping(value = "/roles", method= RequestMethod.POST)
	protected ModelAndView createRole(@ModelAttribute("role") Role role, HttpServletRequest request)  {
		
		if(request.getAttribute("employee") == null) {
            insertLoggedUserInSession(request);
        }
		String message = "";
		
		int result = roleDao.createRole(role);
		if(result > 0) {
			message = "Successfully created new role : " + role.getRolename();
			return new ModelAndView("success-page", "message", message);
		}
		else {
			message = "Failed to create role : " + role.getRolename();
			return new ModelAndView("success-page", "message", message);
		}
	}
	
	@RequestMapping(value = "/roles/delete/{id}", method= RequestMethod.POST)
	protected ModelAndView createRole(@PathVariable("id") long id, HttpServletRequest request)  {
		
		if(request.getAttribute("employee") == null) {
            insertLoggedUserInSession(request);
        }
		String message = "";
		
		Role role = roleDao.getRoleFromId(id);
		int existingUserCount = empDao.getEmployeeCountByRole(role);
		logger.info("User Count : " + existingUserCount);
		if(existingUserCount>0) {
			message = "Users already exists in the role. Delete user before deleting role";
			return new ModelAndView("success-page", "message", message);
		}
		
		int result = roleDao.deleteRoleFromId(id);
		if(result > 0) {
			message = "Successfully deleted role";
			return new ModelAndView("success-page", "message", message);
		}
		else {
			message = "Failed to create role ";
			return new ModelAndView("success-page", "message", message);
		}
	}
	
	public void insertLoggedUserInSession(HttpServletRequest request) {
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
            HttpSession ses = request.getSession();
            Employee employee = empDao.getEmployeeByEmail(username);
            if(employee.getRole().getRolename().equalsIgnoreCase("manager"))
            {
                Manager mang = mangDao.getManager(employee.getEmpId());
                ses.setAttribute("employee", mang);
            }
            else if(employee.getRole().getRolename().equalsIgnoreCase("engineer")) {
                Engineer eng = engDao.getEngineer(employee.getEmpId());
                ses.setAttribute("employee", eng);
            }
            else {
                ses.setAttribute("employee", employee);
            }
        } 
        
        
    }
	
}
