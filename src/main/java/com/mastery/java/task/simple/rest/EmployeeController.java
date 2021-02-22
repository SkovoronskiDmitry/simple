package com.mastery.java.task.simple.rest;

import com.mastery.java.task.simple.dto.Employee;
import com.mastery.java.task.simple.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController()
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping(value = "/test")
    public String Request() {
        return "Example";
    }

    @GetMapping(value = "/employee")
    public Collection<Employee> findAll (){
        return employeeService.findAll();
    }
}
