package com.neu.edu.dao;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;

import com.neu.edu.model.Engineer;

@Service
public class EngineerDao extends DAO{

	public Engineer getEngineer(long id) {
	
		Engineer engineer = null;
		begin();
		try {
			engineer = getSession().get(Engineer.class, id);
		}
		catch(HibernateException ex) {
			ex.printStackTrace();			
		}
		finally {
			close();
		}
		return engineer;
	}
	

	public int registerEngineer(Engineer engineer) {
		
		begin();
		try {
			getSession().save(engineer);
			commit();
			return 1;
		}
		catch (Exception e) {
			rollback();
			return 0 ;
		}
		finally {
			close();
		}
		
	}

}
