package com.neu.edu.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

import com.neu.edu.model.Role;

@Service
public class RoleDao extends DAO {

	public int createRole(Role role) {
		try {
			begin();
			getSession().save(role);
			commit();
			close();
			return 1;
		} catch (HibernateException e) {
			rollback();
			return 0;
			
		}
	}
	
	public Role getRoleFromId(long id) {
		begin();
		try {
			Role role = getSession().get(Role.class, id);
			return role;
		}
		catch(HibernateException ex) {
			ex.printStackTrace();
			return null;
		}
		finally {
			close();
		}
	}
	
	public int deleteRoleFromId(long id) {
		try {
			begin();
			Role role = getSession().get(Role.class, id);
			getSession().delete(role);
			commit();
			close();
			return 1;
		} catch (HibernateException e) {
			rollback();
			return 0;
			
		}
	}
	
	public List<Role> getRoles(){
    
		List<Role> roles;
		begin();
        Query query = getSession().createQuery("from Role");    
        try {
        roles = query.list();
        }
        catch(HibernateException ex) {
        	ex.printStackTrace();
        	return null;
        }
        //commit();
        close();
        return roles;
    }
}
