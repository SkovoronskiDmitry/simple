package com.mastery.java.task.simple.rest;

import com.mastery.java.task.simple.dao.exception.EmployeeDaoException;
import com.mastery.java.task.simple.dto.Employee;
import com.mastery.java.task.simple.rest.exception.EmployeeNotFoundException;
import com.mastery.java.task.simple.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(final EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(value = "/employee")
    public Collection<Employee> findAll() throws EmployeeDaoException {
        return employeeService.findAll();
    }

    @GetMapping(value = "/employee/{employeeId}")
    public Employee findById(@PathVariable final Integer employeeId) throws EmployeeNotFoundException {
        return employeeService.findById(employeeId).
                orElseThrow(() -> new EmployeeNotFoundException(employeeId));
    }

    @PostMapping(value = "/createEmployee")
    @ResponseStatus
    public Long createEmployee(@Valid @RequestBody final Employee employee) throws EmployeeDaoException {
        return employeeService.createEmployee(employee);
    }

    @PutMapping(value = "/updateEmployee")
    public void updateEmployee(@RequestBody final Employee employee) {
        employeeService.updateEmployee(employee);
    }

    @DeleteMapping(value = "/deleteEmployee/{employeeId}")
    public int deleteEmployee(@PathVariable final Integer employeeId) {
        return employeeService.deleteEmployee(employeeId);
    }
}
