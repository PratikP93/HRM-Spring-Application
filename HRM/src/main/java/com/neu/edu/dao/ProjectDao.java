package com.neu.edu.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neu.edu.model.Manager;
import com.neu.edu.model.Project;

@Service	
public class ProjectDao extends DAO {
	
	@Autowired
	ManagerDao managerDao;
	
	@Transactional
	public int registerProject(long managerId, String projectName) {
		
		try {
			Project project = new Project();

			project.setProjectName(projectName);
			Manager manager = managerDao.getManager(managerId);
			manager.getProjects().add(project);
			
			begin();
			getSession().update(manager);
			commit();
			close();
		return 1;	
		}
		catch(Exception e) {
			e.printStackTrace();
			rollback();
		return 0;
		}
		finally {
			close();
		}
	}
	
	public Project getProjectById(long id) {
		Project project = null;
		begin();
		try {
			project = getSession().get(Project.class, id);
			commit();
		}
		catch(HibernateException ex) {
			ex.printStackTrace();			
		}
		finally {
			close();
		}
		return project;
	}
	
	
	public List<Project> getAllProjects() {
		List<Project> projects = null;
		begin();
		try {
			
			projects = getSession().createQuery("from Project").list();
			commit();
		
		}
		catch(HibernateException ex) {
			ex.printStackTrace();			
		}
		finally {
			close();
		}
		return projects;
	}
	
}

