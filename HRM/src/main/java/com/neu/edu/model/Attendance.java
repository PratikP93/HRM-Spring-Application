package com.neu.edu.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Attendance {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "checkin")
	private Date checkIn;
	
	@Column(name = "checkout")
	private Date checkOut;
	
	@Column(name= "totalHours")
	private int totalHours;
	
	@Column(name = "isRegularised")
	private boolean isRegularised;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="employeeId", nullable=false)
    Employee employee;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(Date checkIn) {
		this.checkIn = checkIn;
	}

	public Date getCheckOut() {
		return checkOut;
	}

	public void setCheckOut(Date checkOut) {
		this.checkOut = checkOut;
	}

	public boolean isRegularised() {
		return isRegularised;
	}

	public void setRegularised(boolean isRegularised) {
		this.isRegularised = isRegularised;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public int getTotalHours() {
		return totalHours;
	}

	public void setTotalHours(int totalHours) {
		this.totalHours = totalHours;
	}
	
	
	
	
}
