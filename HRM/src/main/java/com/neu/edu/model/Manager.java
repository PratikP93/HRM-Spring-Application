package com.neu.edu.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Manager extends Employee {
	
	@OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name = "manager_id")	
	Set<Project> projects;
	
	@OneToMany(mappedBy = "manager" , fetch = FetchType.EAGER)
	Set<Engineer> engineers;

	public Set<Project> getProjects() {
		return projects;
	}

	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}

	public Set<Engineer> getEngineers() {
		return engineers;
	}

	public void setEngineers(Set<Engineer> engineers) {
		this.engineers = engineers;
	}
	
	
	
}
