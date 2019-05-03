package com.neu.edu.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.neu.edu.dao.EmployeeDao;
import com.neu.edu.dao.EngineerDao;
import com.neu.edu.dao.ManagerDao;
import com.neu.edu.dao.SalaryDao;
import com.neu.edu.model.Employee;
import com.neu.edu.model.Engineer;
import com.neu.edu.model.Manager;
import com.neu.edu.model.Salary;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Controller
public class SalaryController {

	static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private EmployeeDao employeeDao;

	@Autowired
	private SalaryDao salaryDao;

	@Autowired
	private ResourceLoader resourceLoader;

	
	
	
	@Autowired
	ManagerDao mangDao;
	
	@Autowired
	EngineerDao engDao;
	
	@RequestMapping(value = "/salary", method = RequestMethod.GET)
	protected ModelAndView getSalary(HttpServletRequest request) {

		logger.info("In Salary Get");
		if(request.getAttribute("employee") == null) {
            insertLoggedUserInSession(request);
        }
		List<Salary> salaries;
		Employee emp = (Employee) request.getSession().getAttribute("employee");
		if (emp != null && (emp.getRole().getRolename().equalsIgnoreCase("hr")
				|| emp.getRole().getRolename().equalsIgnoreCase("admin")))
			salaries = salaryDao.getAllSalary();
		else
			salaries = salaryDao.getSalaryByEmployee(emp.getEmpId());
		ModelAndView mv = new ModelAndView();

		mv.addObject("salaries", salaries);
		mv.setViewName("manage-salary");
		return mv;

	}

	@RequestMapping(value = "/salary/generate", method = RequestMethod.POST)
	protected ModelAndView generateSalary(@RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate, HttpServletRequest request) throws java.text.ParseException {

		if(request.getAttribute("employee") == null) {
            insertLoggedUserInSession(request);
        }
		String message = "";
		logger.info("In Salary Generate Post");

		Date sdate = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
		Date edate = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
		logger.info("Start date : " + sdate);
		int total = salaryDao.generateSalaries(sdate, edate);

		if (total > 0) {
			message = "Successfully created salries for " + total + " employees";
			return new ModelAndView("success-page", "message", message);
		} else {
			message = "Failed to generate salaries. Contact Administrator";
			return new ModelAndView("success-page", "message", message);
		}

	}

	@RequestMapping(value = "/salary/export/{id}", method = RequestMethod.GET)
	protected void generateSalary(@PathVariable("id") long id, HttpServletResponse response ,HttpServletRequest request) throws IOException, JRException {

		if(request.getAttribute("employee") == null) {
            insertLoggedUserInSession(request);
        }
		JasperPrint jasperPrint = salaryDao.getPrint(id);

		if (jasperPrint != null) {
			response.setContentType("application/x-download");
			response.setHeader("Content-Disposition",
					String.format("attachment; filename=\"salary_" + id + ".pdf\""));

			OutputStream out = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, out);
		}
		else {
			logger.info("Unable to create report.");
		}

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
