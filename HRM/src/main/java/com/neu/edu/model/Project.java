package com.neu.edu.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Project {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectID;
    
    @Column
    private String projectName;
    
//    @Column
//    Manager manager;
//    
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
//    private List<Engineer> engineers;

	public Long getProjectID() {
		return projectID;
	}

	public void setProjectID(Long projectID) {
		this.projectID = projectID;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

//	public Manager getManager() {
//		return manager;
//	}
//
//	public void setManager(Manager manager) {
//		this.manager = manager;
//	}

//	public List<Engineer> getEngineers() {
//		return engineers;
//	}
//
//	public void setEngineers(List<Engineer> engineers) {
//		this.engineers = engineers;
//	}
    
    
}
