package com.neu.edu.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.neu.edu.model.Attendance;
import com.neu.edu.model.Employee;
import com.neu.edu.model.Role;

@Service
public class EmployeeDao extends DAO {

	public EmployeeDao() {
	}

	
	public Employee validateLogin(String username, String password) {
		Employee employee = null;
		begin();
		
		Query query = getSession().createQuery("from Employee where email= :email");
		query.setParameter("email", username);
		query.setMaxResults(1);
		employee = (Employee)query.uniqueResult();
		close();
		return employee;
	}

	public Employee getEmployeeByEmail(String username) {
		Employee employee = null;
		begin();
		try {
			Query query = getSession().createQuery("from Employee where email= :email");
			query.setParameter("email", username);
			query.setMaxResults(1);
			employee = (Employee)query.uniqueResult();
		}
		catch(HibernateException ex) {
			ex.printStackTrace();
		}
		finally {
			close();
		}
		return employee;
	}
	
	public int registerEmployee(Employee employee) {
		int result;
		begin();
		try {
			getSession().save(employee);
			commit();
			result = 1 ;
		}
		catch(HibernateException ex) {
			ex.printStackTrace();
			result = 0;
		}
		finally {
			close();
		}
		return result;
	}
	
	public List<Employee> getEmployees(){
		List<Employee> employees = null;
		begin();
		Query query = getSession().createQuery("from Employee");
		try {
			employees = query.list();
			commit();
		}
		catch(HibernateException ex){
			ex.printStackTrace();		
		}
		finally {
			close();
		}
		return employees;
	}

	public Employee getEmployeeById(long id) {
		Employee employee = null;
		begin();
		try {
			employee = getSession().get(Employee.class, id);
			commit();
		}
		catch(HibernateException ex) {
			ex.printStackTrace();			
		}
		finally {
			close();
		}
		return employee;
	}
	
	public void delete(Employee employee) {
		try {
			begin();
			getSession().delete(employee);
			commit();
			
		} catch (HibernateException e) {
			rollback();			
		}
		finally {
			close();
		}
	}
	
	public List<Attendance> getEmployeeAttendance(long empId){
		List<Attendance> attList=null;
		begin();
		try {
			Employee emp = (Employee)getSession().get(Employee.class, empId);
			attList = emp.getAttendancerecords();
		}
		catch(HibernateException ex) {
			ex.printStackTrace();
		}
		finally {
			close();
		}
		return attList;
	}
	
	public int getEmployeeCountByRole(Role role) {
		begin();
		int count;
		try {
			
			Criteria crit = getSession().createCriteria(Employee.class);
			Criteria roleCrit = crit.createCriteria("role");
			roleCrit.add(Restrictions.eq("rolename",role.getRolename()));
			crit.setProjection(Projections.rowCount());
			count = ((Long)crit.uniqueResult()).intValue();
			
		}
		catch(HibernateException ex) {
			ex.printStackTrace();
			return 0;
		}
		return count;
	}
	
	
	@Transactional
	public int updateEmployee(long id, Employee newEmployee) {
		int result;
		begin();
		try {
			System.out.println("Update object : " + newEmployee.getFirstName());
			Employee emp = getSession().get(Employee.class, id);
			
			emp.setEmail(newEmployee.getEmail());
			emp.setFirstName(newEmployee.getFirstName());
			emp.setLastName(newEmployee.getLastName());
			emp.setLeaveCount(newEmployee.getLeaveCount());
			emp.setPhone(newEmployee.getPhone());
			emp.setRole(newEmployee.getRole());
			emp.setWage(newEmployee.getWage());
			getSession().update(emp);
			commit();
			result = 1;
		}
		catch(HibernateException ex) {
			ex.printStackTrace();
			result = 0;
		}
		finally {
			close();
		}
		return result;
	}
	
	
}
