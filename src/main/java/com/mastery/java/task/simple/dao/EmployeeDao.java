package com.mastery.java.task.simple.dao;

import com.mastery.java.task.simple.dao.exception.EmployeeDaoException;
import com.mastery.java.task.simple.dto.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeDao {

    List<Employee> findAll() throws EmployeeDaoException;

    Optional<Employee> findById(Integer id);

    Long createEmployee(Employee employee) throws EmployeeDaoException;

    void updateEmployee(Employee employee);

    int deleteEmployee(Integer employeeId);
}
