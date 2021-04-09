package com.mastery.java.task.simple.service;

import com.mastery.java.task.simple.dao.exception.EmployeeDaoException;
import com.mastery.java.task.simple.dto.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    List<Employee> findAll() throws EmployeeDaoException;

    Optional<Employee> findById(Integer id) throws EmployeeDaoException;

    Long createEmployee(Employee employee) throws EmployeeDaoException;

    void updateEmployee(Employee employee) throws EmployeeDaoException;

    int deleteEmployee(Integer employeeId) throws EmployeeDaoException;
}
