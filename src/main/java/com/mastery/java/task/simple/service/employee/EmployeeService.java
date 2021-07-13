package com.mastery.java.task.simple.service.employee;

import com.mastery.java.task.simple.dao.exception.EmployeeDaoException;
import com.mastery.java.task.simple.dto.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    List<Employee> findAll() throws EmployeeDaoException;

    Optional<Employee> findById(Long id) throws EmployeeDaoException;

    List<Employee> findByFirstNameAndLastName (String firstName, String lastName) throws EmployeeDaoException;

    Employee createEmployee(Employee employee) throws EmployeeDaoException;

    void updateEmployee(Employee employee) throws EmployeeDaoException;

    void deleteEmployee(Long employeeId) throws EmployeeDaoException;
}
