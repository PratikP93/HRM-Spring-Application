package com.neu.edu.dao;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.neu.edu.model.Attendance;
import com.neu.edu.model.Employee;

@Service
public class AttendanceDao extends DAO {

	public List<Attendance> getAttendance(){
		List<Attendance> attendanceList = null;
		begin();
		try {
			Query query = getSession().createQuery("from Attendance");
			attendanceList = query.list();
		}
		catch(HibernateException ex) {
			ex.printStackTrace();
		}
		finally {
			close();
		}
		return attendanceList;
	}
	
	public List<Attendance> getEmployeeAttendance(Employee emp){
		List<Attendance> attendanceList = null;
		begin();
		try {
			Query query = getSession().createQuery("from Attendance where employee=:emp");
			query.setParameter("emp", emp);
			attendanceList = query.list();
		}
		catch(HibernateException ex) {
			ex.printStackTrace();
		}
		finally {
			close();
		}
		return attendanceList;
	}
	
	public int getEmployeeTotalHoursByDate(Employee emp, Date stDate, Date endDate){
		int totalHours = 0;
		
		begin();
		try {
			Criteria crit = getSession().createCriteria(Attendance.class);
			crit.add(Restrictions.eq("employee", emp));
			crit.add(Restrictions.ge("checkIn", stDate));
			crit.add(Restrictions.le("checkOut",endDate));
			crit.setProjection(Projections.sum("totalHours"));
			Long total = crit.list().get(0)==null ? Long.valueOf(0) : (Long)crit.list().get(0);
			totalHours = total.intValue();
		
		}
		catch(HibernateException ex) {
			ex.printStackTrace();
		}
		finally {
			close();
		}
		return totalHours;
	}
	
	public Attendance getTodayAttendance(long empId) {
		Attendance att = null;
		SimpleDateFormat mdyFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar now = Calendar.getInstance();
        now.set(Calendar.HOUR, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.HOUR_OF_DAY, 0);
	    Date stDate = now.getTime();
	    now.set(Calendar.HOUR_OF_DAY, 23);
	    Date edDate = now.getTime();

		begin();
		try {
			Query query = getSession().createQuery("from Attendance where employeeId =: empId AND checkIn BETWEEN :start AND :end  ");
			query.setParameter("start", stDate);
			query.setParameter("end", edDate);
			query.setParameter("empId", empId);
			query.setMaxResults(1);
			att = (Attendance) query.uniqueResult();
		}
		catch(HibernateException ex) {
			ex.printStackTrace();
		}
		finally {
			close();
		}
		return att;
	}
	
	
	
	public int insertAttendance(Attendance att) {
		int result;
		begin();
		try {
			getSession().save(att);
			commit();
			result = 1;
		}
		catch(HibernateException ex) {
			ex.printStackTrace();
			result =0;
		}
		finally {
			close();
		}
		return result;
	}
	
	public int updateCheckOut(long empId) {
		int result;
		Attendance att = getTodayAttendance(empId);
		Date nw = new Date();
		
		long diffInMillies = Math.abs(nw.getTime() - att.getCheckIn().getTime());
	    int diff = (int)TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		
		begin();
		try {
			
			att.setCheckOut(nw);
			att.getCheckIn();
			att.setTotalHours(diff);
			
			getSession().update(att);
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
