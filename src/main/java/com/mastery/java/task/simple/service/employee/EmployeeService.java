package com.mastery.java.task.simple.service.employee;

import com.mastery.java.task.simple.dao.exception.EmployeeDaoException;
import com.mastery.java.task.simple.dto.Employee;
import com.mastery.java.task.simple.service.exception.EmployeeServiceException;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    List<Employee> findAll() throws EmployeeServiceException;

    Optional<Employee> findById(Long id) throws EmployeeServiceException;

    List<Employee> findByFirstNameAndLastName (String firstName, String lastName) throws EmployeeServiceException;

    Employee createEmployee(Employee employee) throws EmployeeServiceException;

    void updateEmployee(Employee employee) throws EmployeeServiceException;

    void deleteEmployee(Long employeeId) throws EmployeeServiceException;
}
