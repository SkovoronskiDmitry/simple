package com.mastery.java.task.simple.service;

import com.mastery.java.task.simple.dao.EmployeeDao;
import com.mastery.java.task.simple.dto.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    public List<Employee> findAll() {
        return employeeDao.findAll();
    }
}
