package com.mastery.java.task.simple.rest;

import com.mastery.java.task.simple.dao.exception.EmployeeDaoException;
import com.mastery.java.task.simple.dto.Employee;
import com.mastery.java.task.simple.rest.exception.EmployeeNotFoundException;
import com.mastery.java.task.simple.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@Api(value = "Employees", description = "APIs for working with employees")
@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(final EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @ApiOperation(
            value = "Get all employees",
            response = Employee.class,
            responseContainer = "List"
    )
    @GetMapping(value = "/employee")
    public Collection<Employee> findAll() throws EmployeeDaoException {
        return employeeService.findAll();
    }

    @ApiOperation(
            value = "Find employee by ID",
            response = Employee.class
    )
    @GetMapping(value = "/employee/{employeeId}")
    public Employee findById(
            @ApiParam(value = "ID for search employee", required = true)
            @PathVariable final Integer employeeId) throws EmployeeNotFoundException, EmployeeDaoException {
        return employeeService.findById(employeeId).
                orElseThrow(() -> new EmployeeNotFoundException(employeeId));
    }

    @ApiOperation(
            value = "Create new Employee",
            response = Long.class
    )
    @PostMapping(value = "/createEmployee")
    public Long createEmployee(
            @ApiParam(value = "New employee", required = true)
            @Valid @RequestBody final Employee employee) throws EmployeeDaoException {
        return employeeService.createEmployee(employee);
    }

    @ApiOperation(
            value = "Update Employee",
            notes = "change the values departmentId and job title"
    )
    @PutMapping(value = "/updateEmployee")
    public void updateEmployee(
            @ApiParam(value = "Employee for update", required = true)
            @RequestBody final Employee employee) throws EmployeeDaoException {
        employeeService.updateEmployee(employee);
    }

    @ApiOperation(value = "Delete Employee by ID")
    @DeleteMapping(value = "/deleteEmployee/{employeeId}")
    public int deleteEmployee(
            @ApiParam(value = "ID for delete employee", required = true)
            @PathVariable final Integer employeeId) throws EmployeeDaoException {
        return employeeService.deleteEmployee(employeeId);
    }
}
