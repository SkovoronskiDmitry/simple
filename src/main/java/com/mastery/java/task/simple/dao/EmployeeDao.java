package com.mastery.java.task.simple.dao;

import com.mastery.java.task.simple.dto.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeDao {

    List<Employee> findAll();

    Optional<Employee> findById(Integer id);

    Long createEmployee(Employee employee);

    void updateEmployee(Employee employee);

    int deleteEmployee(Integer employeeId);
}
