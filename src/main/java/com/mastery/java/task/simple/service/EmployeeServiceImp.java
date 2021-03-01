package com.mastery.java.task.simple.service;

import com.mastery.java.task.simple.dao.EmployeeDaoImp;
import com.mastery.java.task.simple.dto.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImp implements EmployeeService {

    @Autowired
    private EmployeeDaoImp employeeDaoImp;

    @Override
    public List<Employee> findAll() {
        return employeeDaoImp.findAll();
    }

    @Override
    public Optional<Employee> findById(final Integer employeeId) {
        return employeeDaoImp.findById(employeeId);
    }

    @Override
    public Long createEmployee(final Employee employee) {
        return employeeDaoImp.createEmployee(employee);
    }

    @Override
    public void updateEmployee(final Employee employee) {
        employeeDaoImp.updateEmployee(employee);
    }

    @Override
    public int deleteEmployee(final Integer employeeId) {
        return employeeDaoImp.deleteEmployee(employeeId);

    }
}
