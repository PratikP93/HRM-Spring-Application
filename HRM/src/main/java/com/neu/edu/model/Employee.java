package com.neu.edu.model;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.neu.edu.validator.UniqueUsername;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long empId;
	
	@Column(name = "firstName")
	private String firstName;
	
	@Column(name = "lastName")
	private String lastName;
	
	@Column(name = "email")
	@UniqueUsername
	private String email;
	
	@Column(name="password")
	private String password;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "roleID")
    private Role role;
	
	@OneToMany(mappedBy = "employee")    
	List<Attendance> attendancerecords;
	
	@Column
	private int leaveCount;
	
	@Column
	private int wage;
	
	@Column
	private String phone;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "employeeId")
	Set<Salary> salaryList;
	
	@OneToMany(mappedBy =  "employee")
	Set<AttendanceRequest> requests;

	public long getEmpId() {
		return empId;
	}

	public void setEmpId(long empId) {
		this.empId = empId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<Attendance> getAttendancerecords() {
		return attendancerecords;
	}

	public void setAttendancerecords(List<Attendance> attendancerecords) {
		this.attendancerecords = attendancerecords;
	}

	public int getLeaveCount() {
		return leaveCount;
	}

	public void setLeaveCount(int leaveCount) {
		this.leaveCount = leaveCount;
	}

	public Set<Salary> getSalaryList() {
		return salaryList;
	}

	public void setSalaryList(Set<Salary> salaryList) {
		this.salaryList = salaryList;
	}

	public Set<AttendanceRequest> getRequests() {
		return requests;
	}

	public void setRequests(Set<AttendanceRequest> requests) {
		this.requests = requests;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getWage() {
		return wage;
	}

	public void setWage(int wage) {
		this.wage = wage;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
