package com.neu.edu.config;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neu.edu.dao.EmployeeDao;
import com.neu.edu.model.Employee;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
	static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
	
	@Autowired
	private EmployeeDao employeeDao;
	
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username) {
		logger.info("Searching user : " + username );
		Employee emp = employeeDao.getEmployeeByEmail(username);
		logger.info("User : {}", emp);
		if(emp==null){
			logger.info("User not found");
		}
			return new org.springframework.security.core.userdetails.User(emp.getEmail(), emp.getPassword(),
				 true, true, true, true, getGrantedAuthorities(emp));
	}

	
	private List<GrantedAuthority> getGrantedAuthorities(Employee emp){
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		
			logger.info("UserProfile : {}", emp.getRole().getRolename().toUpperCase());
			authorities.add(new SimpleGrantedAuthority("ROLE_"+emp.getRole().getRolename().toUpperCase()));
		
		logger.info("authorities : {}", authorities);
		return authorities;
	}


	
}
