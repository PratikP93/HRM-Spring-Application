package com.neu.edu.dao;

import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.neu.edu.model.Manager;
import com.neu.edu.model.Project;

@Service
public class ManagerDao extends DAO{
	
	static final Logger logger = LoggerFactory.getLogger(ManagerDao.class);
	
	public List<Manager> getManagers() {
	
		List<Manager> managers = null;
		begin();
		try {
			Query query = getSession().createQuery("select empId, firstName from Manager");
			
			managers = query.list();
			logger.info("Return manager size : " + managers.size());
		}
		catch(HibernateException ex) {
			ex.printStackTrace();			
		}
		finally {
			close();
		}
		return managers;
	}
	
	public Manager getManager(long id) {
		
		Manager manager = null;
		begin();
		try {
			manager = getSession().get(Manager.class, id);
		}
		catch(HibernateException ex) {
			ex.printStackTrace();			
		}
		finally {
			close();
		}
		return manager;
	}
	
	public Set<Project> getProjectsByManager(long id){
		Manager mang = getManager(id);
		Set<Project> projects = mang.getProjects();
		return projects;
	}
	

	public int registerManager(Manager manager) {
		
		begin();
		try {
			getSession().save(manager);
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
