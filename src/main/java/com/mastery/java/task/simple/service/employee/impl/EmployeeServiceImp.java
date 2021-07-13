package com.mastery.java.task.simple.service.employee.impl;

import com.mastery.java.task.simple.dao.EmployeeRepository;
import com.mastery.java.task.simple.dao.exception.EmployeeDaoException;
import com.mastery.java.task.simple.dto.Employee;
import com.mastery.java.task.simple.service.employee.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImp implements EmployeeService {


    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImp(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> findAll() throws EmployeeDaoException {
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> findById(final Long employeeId) throws EmployeeDaoException {
        return employeeRepository.findById(employeeId);
    }

    @Override
    public List<Employee> findByFirstNameAndLastName(final String firstName, final String lastName) throws EmployeeDaoException {
        return employeeRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    @Override
    public Employee createEmployee(final Employee employee) throws EmployeeDaoException {
        return employeeRepository.save(employee);
    }

    @Override
    public void updateEmployee(final Employee employee) throws EmployeeDaoException {
        employeeRepository.findById(employee.getEmployeeId())
                .map(newEmployee -> {
                    newEmployee.setJobTitle(employee.getJobTitle());
                    newEmployee.setDepartmentId(employee.getDepartmentId());
                    return employeeRepository.save(newEmployee);
                });
    }

    @Override
    public void deleteEmployee(final Long employeeId) throws EmployeeDaoException {
        employeeRepository.deleteById(employeeId);
    }
}
