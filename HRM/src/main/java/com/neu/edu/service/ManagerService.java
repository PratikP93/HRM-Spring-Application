package com.neu.edu.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neu.edu.dao.ManagerDao;
import com.neu.edu.model.Manager;

@Service
public class ManagerService {

	static final Logger logger = LoggerFactory.getLogger(ManagerService.class);
	
	@Autowired
	ManagerDao managerDao;
	
	@Transactional
	public List<Manager> getManagers(){
		
		
		logger.info("Inside manager service");
		List<Manager> managers = managerDao.getManagers();
		
		 
		logger.info("Manager size : " + managers.size());
		return managers;
	}
}
