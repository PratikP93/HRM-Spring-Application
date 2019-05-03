package com.neu.edu.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Engineer extends Employee {

	@ManyToOne
	@JoinColumn(name="ProjectId", nullable = false)
	private Project project;
	
	@ManyToOne
	@JoinColumn(name="ManagerId", nullable = false)
	private Manager manager;

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}
	
	
	
	
}
