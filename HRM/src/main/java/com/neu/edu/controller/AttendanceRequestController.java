package com.neu.edu.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.neu.edu.dao.AttendanceRequestDao;
import com.neu.edu.dao.EmployeeDao;
import com.neu.edu.dao.EngineerDao;
import com.neu.edu.dao.ManagerDao;
import com.neu.edu.model.AttendanceRequest;
import com.neu.edu.model.Employee;
import com.neu.edu.model.Engineer;
import com.neu.edu.model.Manager;

@Controller
public class AttendanceRequestController {

	@Autowired
	AttendanceRequestDao attReqDao;

	@Autowired
	EmployeeDao empDao;
	
	@Autowired
	ManagerDao mangDao;
	
	@Autowired
	EngineerDao engDao;
	@RequestMapping(value = "/allrequests", method = RequestMethod.GET)
	public ModelAndView getAllrequests(HttpServletRequest request) {
		if(request.getAttribute("employee") == null) {
            insertLoggedUserInSession(request);
        }

		Employee emp = (Employee) request.getSession().getAttribute("employee");
	long  id = emp.getEmpId();
		
		ModelAndView mv = new ModelAndView();
		
		mv.addObject("leaves", attReqDao.getLeaveRequests(id));
		mv.setViewName("leaves-dashboard");
		return mv;

	}

	@RequestMapping(value = "/requests", method = RequestMethod.GET)
	public ModelAndView createLeaveRequest( HttpServletRequest request) {
		if(request.getAttribute("employee") == null) {
            insertLoggedUserInSession(request);
        }
		ModelAndView mv = new ModelAndView();
		mv.addObject("leaveReq", new AttendanceRequest());
		mv.setViewName("manage-leaves");
		return mv;
	}

	@RequestMapping(value = "/requests", method = RequestMethod.POST)
	public ModelAndView saveRequest(HttpServletRequest request) throws ParseException {
		if(request.getAttribute("employee") == null) {
            insertLoggedUserInSession(request);
        }
		ModelAndView mv = new ModelAndView();
		AttendanceRequest leaveReq = new AttendanceRequest();
		String type = request.getParameter("type");
		leaveReq.setType(type);
		HttpSession session = request.getSession();
		Employee e = (Employee) session.getAttribute("employee");
		String stDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		System.out.println("st Date:::" + stDate);
		System.out.println("end Date:::" + endDate);

		Date sdate = new SimpleDateFormat("yyyy-MM-dd").parse(stDate);
		Date edate = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);

		int status = attReqDao.getRequestbyDate(sdate, edate,e.getEmpId());

		int result = 0;
		if (status == 0) {
			leaveReq.setEmployee(e);
			leaveReq.setStartDate(sdate);
			leaveReq.setEndDate(edate);
			leaveReq.setStatus("submitted");	
			result = attReqDao.applyLeave(leaveReq);
		}
		if (result > 0) {
			String message = "Successfully created new Leave request : ";
			// e.ge
		
			return new ModelAndView("success-page", "message", message);
		} else {
			String message = "Failed to create Leave request: You might have previous Application with similar date range ";
			return new ModelAndView("success-page", "message", message);
		}
	}

	@RequestMapping(value = "/managereq", method = RequestMethod.GET)
	public ModelAndView manageRequests(HttpServletRequest request) {
		
		if(request.getAttribute("employee") == null) {
            insertLoggedUserInSession(request);
        }
		List<AttendanceRequest> allmanagedReq = new ArrayList<AttendanceRequest>();
		ModelAndView mv = new ModelAndView();

		HttpSession session = request.getSession();
		Manager emp = (Manager) session.getAttribute("employee");
		Set<Engineer> engList = emp.getEngineers();
		for (Engineer e : engList) {

			allmanagedReq.addAll(attReqDao.manageLeaveReq(e.getEmpId()));

		}
		System.out.println("size:::::::::" + allmanagedReq.size());
		mv.addObject("attRequests", allmanagedReq);
		mv.setViewName("manage-leaverequests");
		return mv;

	}

	@RequestMapping(value = "/managereq/{id}", method = RequestMethod.POST)
	public ModelAndView manageLeaveRequests(HttpServletRequest request, @PathVariable("id") long id,
			HttpServletResponse response) {
		if(request.getAttribute("employee") == null) {
            insertLoggedUserInSession(request);
        }
		
		String status = request.getParameter("btnAction");

		int result = attReqDao.updateRequestStatus(id, status);

		
		return new  ModelAndView("redirect:/managereq"); 
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
