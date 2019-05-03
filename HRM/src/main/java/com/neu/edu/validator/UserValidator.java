package com.neu.edu.validator;

import javax.validation.*;

import org.springframework.beans.factory.annotation.Autowired;

import com.neu.edu.dao.EmployeeDao;
import com.neu.edu.model.Employee;

public class UserValidator implements ConstraintValidator<UniqueUsername, String> {

	@Autowired
	EmployeeDao employeeDao;

	@Override
	public void initialize(UniqueUsername constraintAnnotation) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		System.out.println("in is Valid:::::");
		Employee emp1 = employeeDao.getEmployeeByEmail(value);

		if (emp1 == null) {
			System.out.println("in null");
			return true;
		} else {
			System.out.println("in not null:::::");
			return false;

		}

	}

}
