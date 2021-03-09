package com.mastery.java.task.simple.service;

import com.mastery.java.task.simple.dao.EmployeeDao;
import com.mastery.java.task.simple.dao.exception.EmployeeDaoException;
import com.mastery.java.task.simple.dto.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImp implements EmployeeService {

    private final EmployeeDao employeeDao;

    @Autowired
    public EmployeeServiceImp(final EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Override
    public List<Employee> findAll() throws EmployeeDaoException {
        return employeeDao.findAll();
    }

    @Override
    public Optional<Employee> findById(final Integer employeeId) {
        return employeeDao.findById(employeeId);
    }

    @Override
    public Long createEmployee(final Employee employee) throws EmployeeDaoException {
        if (employee.getEmployeeId() != null) {
            throw new IllegalArgumentException();
        }
        return employeeDao.createEmployee(employee);
    }

    @Override
    public void updateEmployee(final Employee employee) {
        employeeDao.updateEmployee(employee);
    }

    @Override
    public int deleteEmployee(final Integer employeeId) {
        return employeeDao.deleteEmployee(employeeId);
    }
}
