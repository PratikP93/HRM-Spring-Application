package com.neu.edu.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.neu.edu.model.AttendanceRequest;
import com.neu.edu.model.Employee;

@Service
public class AttendanceRequestDao extends DAO {

	public int applyLeave(AttendanceRequest request) {
		begin();
		try {
			getSession().save(request);
			commit();
			return 1;
		} catch (HibernateException ex) {
			ex.printStackTrace();
			return 0;
		} finally {
			close();
		}
	}


public List<AttendanceRequest> getLeaveRequests(long id){
	
	try {
		begin();
		Query query = getSession().createQuery("from AttendanceRequest where employeeId =: id");
		query.setParameter("id",id);
		List<AttendanceRequest> leavereq = query.list();
		return leavereq;
	}
	catch(HibernateException ex){
		ex.printStackTrace();
		return null;
	}
	finally {
		close();
	}	
}


	public int getRequestbyDate(Date sdate, Date edate , long id) {

		begin();
		try {

			Criteria criteria = getSession().createCriteria(AttendanceRequest.class);
			// Restrictions.ge("orderDate", minDate);
			Criterion restrictstDate = Restrictions.between("startDate", sdate, edate);
			Criterion restrictenDate = Restrictions.between("endDate", sdate, edate);
		Criteria empCriteria = criteria.createCriteria("employee");
		empCriteria.add(Restrictions.eq("empId",id ));
		 
			Disjunction or = Restrictions.disjunction();
			or.add(restrictstDate);
			or.add(restrictenDate);
			criteria.add(or);
		

			criteria.setMaxResults(1);
			AttendanceRequest ar = (AttendanceRequest) criteria.uniqueResult();

			if (ar != null)
				return 1;
		} catch (HibernateException ex) {
			ex.printStackTrace();
		} finally {
			close();
		}
		return 0;

	}

	

	public List<AttendanceRequest> manageLeaveReq(long id) {

		List<AttendanceRequest> allAttRequests = new ArrayList<AttendanceRequest>();

		try {
			begin();

			Query query = getSession().createQuery("from AttendanceRequest where employeeId=: empId");
			query.setParameter("empId", id);

			allAttRequests = query.list();
			System.out.println("allAttRequests::" + allAttRequests.size());
			return allAttRequests;

		} catch (HibernateException ex) {
			ex.printStackTrace();
		} finally {
			close();
		}

		return allAttRequests;
	}

	public int updateRequestStatus(long reqId, String status) {
		try {
			begin();

			AttendanceRequest attReq = getSession().get(AttendanceRequest.class, reqId);
			if (attReq != null) {
				attReq.setStatus(status);
				getSession().update(attReq);
				commit();
				return 1;
			}

		} catch (HibernateException ex) {
			ex.printStackTrace();
			return 0;
		} finally {
			close();
		}
		return 0;

	}

}
