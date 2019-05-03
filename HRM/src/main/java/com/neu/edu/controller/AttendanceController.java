package com.neu.edu.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.neu.edu.dao.AttendanceDao;
import com.neu.edu.dao.EmployeeDao;
import com.neu.edu.dao.EngineerDao;
import com.neu.edu.dao.ManagerDao;
import com.neu.edu.model.Attendance;
import com.neu.edu.model.Employee;
import com.neu.edu.model.Engineer;
import com.neu.edu.model.Manager;

@Controller
public class AttendanceController {

	@Autowired
	AttendanceDao attDao;
	
	@Autowired
	EmployeeDao empDao;
	
	@Autowired
	ManagerDao mangDao;
	
	@Autowired
	EngineerDao engDao;
	
	@RequestMapping(value ="/attendance", method= RequestMethod.GET)
	public ModelAndView viewAttendance( HttpServletRequest request) {
		if(request.getAttribute("employee") == null) {
            insertLoggedUserInSession(request);
        }
		
		ModelAndView mv = new ModelAndView();
		
		String userName = "";
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			userName = ((UserDetails)principal).getUsername();
		} else {
			userName = principal.toString();
		}
		
		
		HttpSession session = request.getSession();
		session.setAttribute("employee", empDao.getEmployeeByEmail(userName));
		
		Employee emp = empDao.getEmployeeByEmail(userName);
		long employeeId  = emp.getEmpId();
		List<Attendance> attendanceList = attDao.getEmployeeAttendance(emp);
		Attendance attendance = attDao.getTodayAttendance(employeeId); 
		mv.addObject("attList", attendanceList);
		mv.addObject("att", attendance);
		mv.setViewName("manage-attendance");
		return mv;
	}
	
	@RequestMapping(value ="/attendance/checkIn/{empId}", method= RequestMethod.GET)
	public ModelAndView checkInAttendance(@PathVariable("empId") long empId, HttpServletRequest request) {
		if(request.getAttribute("employee") == null) {
            insertLoggedUserInSession(request);
        }
		ModelAndView mv = new ModelAndView();
		
		Employee emp = empDao.getEmployeeById(empId);
		Attendance att = new Attendance();
		Calendar now = Calendar.getInstance();

	    Date curDate = now.getTime();
		att.setCheckIn(curDate);
		att.setCheckOut(null);
		att.setEmployee(emp);
		
		int result = attDao.insertAttendance(att);
//		if(result > 0)
		
		return new  ModelAndView("redirect:/attendance");
	}
	
	@RequestMapping(value ="/attendance/checkOut/{empId}", method= RequestMethod.GET)
	public ModelAndView checkOutAttendance(@PathVariable("empId") long empId , HttpServletRequest request) {
		if(request.getAttribute("employee") == null) {
            insertLoggedUserInSession(request);
        }
		ModelAndView mv = new ModelAndView();
		
		
		int result = attDao.updateCheckOut(empId);
//		if(result > 0)
		
		return new  ModelAndView("redirect:/attendance");
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
