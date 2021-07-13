package com.mastery.java.task.simple.dao;

import com.mastery.java.task.simple.dto.Employee;
import com.mastery.java.task.simple.service.exception.EmployeeServiceException;

import java.util.List;

public interface EmployeeRepositoryCustom {
    List<Employee> findByFirstNameAndLastName(final String FirstName, final String LastName) throws EmployeeServiceException;
}
