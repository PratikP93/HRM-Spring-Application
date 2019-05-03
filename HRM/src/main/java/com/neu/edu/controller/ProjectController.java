package com.neu.edu.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.neu.edu.dao.EmployeeDao;
import com.neu.edu.dao.EngineerDao;
import com.neu.edu.dao.ManagerDao;
import com.neu.edu.dao.ProjectDao;
import com.neu.edu.model.Employee;
import com.neu.edu.model.Engineer;
import com.neu.edu.model.Manager;
import com.neu.edu.model.Project;

@Controller
public class ProjectController {

	@Autowired
	ProjectDao projectDao;

	@Autowired
	EmployeeDao empDao;
	
	@Autowired
	ManagerDao mangDao;
	
	@Autowired
	EngineerDao engDao;
	

	@RequestMapping(value = "/project", method = RequestMethod.GET)
	public ModelAndView createProject(HttpServletRequest request ) {
		if(request.getAttribute("employee") == null) {
            insertLoggedUserInSession(request);
        }
		ModelAndView mv = new ModelAndView();
//		mv.addObject("project", new Project());

		mv.addObject("projects",projectDao.getAllProjects());
		mv.setViewName("manage-project");
		
		return mv;
	}
	
	

	@RequestMapping(value = "/project", method = RequestMethod.POST)
	public ModelAndView storePoject(HttpServletRequest request) {
		if(request.getAttribute("employee") == null) {
            insertLoggedUserInSession(request);
        }
		HttpSession session = request.getSession();
		Manager manager = (Manager)session.getAttribute("employee") ;
		long managerId = manager.getEmpId();
 		String projectName = request.getParameter("projectName");
		
		//project.setManager(manager);
		
		String message = "";
		int result = projectDao.registerProject(managerId, projectName);
		if (result > 0) {
			message = "Successfully created new Project : " + projectName;
			return new ModelAndView("success-page", "message", message);
		} else {
			message = "Failed to create Project : " + projectName;
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
