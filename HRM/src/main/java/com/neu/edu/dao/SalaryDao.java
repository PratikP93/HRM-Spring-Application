package com.neu.edu.dao;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.neu.edu.model.Employee;
import com.neu.edu.model.Salary;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;



@Service
public class SalaryDao extends DAO{

	static final Logger logger = LoggerFactory.getLogger(SalaryDao.class);
	
	@Autowired
	EmployeeDao empDao;
	
	@Autowired
	AttendanceDao attDao;
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	public List<Salary> getAllSalary(){
		List<Salary> allSalary = null;
		
		begin();
		try {
			Query query = getSession().createQuery("from Salary");
			allSalary = query.list();
		}
		catch(HibernateException ex) {
			ex.printStackTrace();
		}
		finally {
			close();
		}
		return allSalary;
	}
	
	public Salary getSalary(long id){
		Salary salary =null;
		
		begin();
		try {
			salary = getSession().get(Salary.class,id);
			//allSalary = query.list();
		}
		catch(HibernateException ex) {
			ex.printStackTrace();
		}
		finally {
			close();
		}
		return salary;
	}
	
	public List<Salary> getSalaryByEmployee(long empId){
		List<Salary> allSalary = null;
		
		begin();
		try {
			
			Query query = getSession().createQuery("from Salary where empId=:empId");
			query.setParameter("empId", empId);
			allSalary = query.list();
		}
		catch(HibernateException ex) {
			ex.printStackTrace();
		}
		finally {
			close();
		}
		return allSalary;
	}
	
	public int generateSalaries(Date stDate, Date endDate) {
		List<Employee> empList = empDao.getEmployees();
		int perHourRate;
		int totalHours;
		int totalSalary;
		int rowCount = 0;
		
		for(Employee emp : empList) {
			totalHours=0;
			totalSalary = 0;
			perHourRate = emp.getWage();
			
			totalHours = attDao.getEmployeeTotalHoursByDate(emp, stDate, endDate);
			totalSalary = totalHours * perHourRate;
			logger.info("Total Hours : " + totalHours + " and perHourRate : " + perHourRate);

			Salary sal = new Salary();
			sal.setEmpId(emp.getEmpId());
			sal.setStartDate(stDate);
			sal.setEndDate(endDate);
			sal.setTotal(totalSalary);
			
			getSession().save(sal);
			
			rowCount++;
			
		}
		
		return rowCount;
	}
	
	public JasperPrint getPrint(long id) throws IOException {
		Salary sal = getSalary(id);
		
		JasperPrint jasperPrint =  null;
		String sourceFile = resourceLoader.getResource("classpath:rpt_salary.jrxml").getURI().getPath();
		logger.info("In export Get : " + sal.getSalaryId());
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("empId", sal.getEmpId().toString());
		parameters.put("startDate", sal.getStartDate().toString());
		parameters.put("endDate", sal.getEndDate().toString());
		parameters.put("total", sal.getTotal());
		
		try {
			//JasperReport report = (JasperReport) JRLoader.loadObjectFromFile(sourceFile);

			JRDataSource dataSource = new JREmptyDataSource(1);
			JasperReport report = JasperCompileManager.compileReport(sourceFile);

			jasperPrint = JasperFillManager.fillReport(report,
					parameters, dataSource);
			
		}
		catch (JRException e) {
			e.printStackTrace();
		}
		return jasperPrint;
	}
	
}
