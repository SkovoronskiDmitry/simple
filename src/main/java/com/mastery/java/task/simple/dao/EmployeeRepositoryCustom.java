package com.mastery.java.task.simple.dao;

import com.mastery.java.task.simple.dto.Employee;

import java.util.List;

public interface EmployeeRepositoryCustom {
    List<Employee> findByFirstNameAndLastName(final String FirstName, final String LastName);
}
