package com.mastery.java.task.simple.rest;

import com.mastery.java.task.simple.dao.exception.EmployeeDaoException;
import com.mastery.java.task.simple.dto.Employee;
import com.mastery.java.task.simple.rest.exception.EmployeeNotFoundException;
import com.mastery.java.task.simple.service.EmployeeService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@Api(value = "Employees")
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
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully find all employees"),
            @ApiResponse(code = 400, message = "Missing or invalid request body"),
            @ApiResponse(code = 404, message = "Employees or resource not found"),
            @ApiResponse(code = 500, message = "Internal error")
    })
    @GetMapping(value = "/employee")
    public Collection<Employee> findAll() throws EmployeeDaoException {
        return employeeService.findAll();
    }

    @ApiOperation(
            value = "Find employee by ID",
            response = Employee.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully find employee by ID"),
            @ApiResponse(code = 400, message = "Missing or invalid employee's ID"),
            @ApiResponse(code = 404, message = "Employee or resource not found"),
            @ApiResponse(code = 500, message = "Internal error")
    })
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
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employee created successfully"),
            @ApiResponse(code = 400, message = "Missing or invalid employee"),
            @ApiResponse(code = 500, message = "Internal error")
    })
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
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Employee successfully update"),
            @ApiResponse(code = 400, message = "Missing or invalid employee"),
            @ApiResponse(code = 404, message = "Employee or resource not found"),
            @ApiResponse(code = 500, message = "Internal error")
    })
    @PutMapping(value = "/updateEmployee")
    public void updateEmployee(
            @ApiParam(value = "Employee for update", required = true)
            @RequestBody final Employee employee) throws EmployeeDaoException {
        employeeService.updateEmployee(employee);
    }

    @ApiOperation(value = "Delete Employee by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employee successfully delete"),
            @ApiResponse(code = 400, message = "Missing or invalid employee's ID"),
            @ApiResponse(code = 404, message = "Employee or resource not found"),
            @ApiResponse(code = 500, message = "Internal error")
    })
    @DeleteMapping(value = "/deleteEmployee/{employeeId}")
    public int deleteEmployee(
            @ApiParam(value = "ID for delete employee", required = true)
            @PathVariable final Integer employeeId) throws EmployeeDaoException {
        return employeeService.deleteEmployee(employeeId);
    }
}
