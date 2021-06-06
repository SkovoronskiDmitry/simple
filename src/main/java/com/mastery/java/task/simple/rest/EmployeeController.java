package com.mastery.java.task.simple.rest;

import com.mastery.java.task.simple.dao.exception.EmployeeDaoException;
import com.mastery.java.task.simple.dto.Employee;
import com.mastery.java.task.simple.rest.exception.EmployeeNotFoundException;
import com.mastery.java.task.simple.service.EmployeeService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@Api(value = "Employees")
@RestController
@RequestMapping(value = "/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(final EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @ApiOperation(
            value = "Get all employees",
            response = Employee.class,
            responseContainer = "List",
            authorizations = {
                    @Authorization(
                            value = "Employees",
                            scopes = {@AuthorizationScope(scope = "find:employees", description = "find all employees")}
                    )}
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Everything is working"),
            @ApiResponse(code = 400, message = "Bad Request — The request was invalid or cannot be served. The exact error should be explained in the error payload. E.g. „The JSON is not valid"),
            @ApiResponse(code = 404, message = "Not found — There is no resource behind the URI"),
            @ApiResponse(code = 500, message = "Internal Server Error — API developers should avoid this error. If an error occurs in the global catch blog, the stracktrace should be logged and not returned as response")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Employee> findAll() throws EmployeeDaoException {
        return employeeService.findAll();
    }

    @ApiOperation(
            value = "Find employee by ID",
            response = Employee.class,
            authorizations = {
                    @Authorization(
                            value = "Employee",
                            scopes = {@AuthorizationScope(scope = "find:employee", description = "find employee by ID")}
                    )})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Everything is working"),
            @ApiResponse(code = 400, message = "Bad Request — The request was invalid or cannot be served. The exact error should be explained in the error payload. E.g. „The JSON is not valid"),
            @ApiResponse(code = 404, message = "Not found — There is no resource behind the URI"),
            @ApiResponse(code = 500, message = "Internal Server Error — API developers should avoid this error. If an error occurs in the global catch blog, the stracktrace should be logged and not returned as response")
    })
    @GetMapping(value = "/{employeeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Employee findById(
            @ApiParam(value = "ID for search employee", required = true)
            @PathVariable final Integer employeeId) throws EmployeeNotFoundException, EmployeeDaoException {
        return employeeService.findById(employeeId).
                orElseThrow(() -> new EmployeeNotFoundException(employeeId));
    }

    @ApiOperation(
            value = "Create new Employee",
            response = Long.class,
            authorizations = {
                    @Authorization(
                            value = "Employee",
                            scopes = {@AuthorizationScope(scope = "create:employee", description = "create new employee")}
                    )}
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Everything is working"),
            @ApiResponse(code = 400, message = "Bad Request — The request was invalid or cannot be served. The exact error should be explained in the error payload. E.g. „The JSON is not valid"),
            @ApiResponse(code = 500, message = "Internal Server Error — API developers should avoid this error. If an error occurs in the global catch blog, the stracktrace should be logged and not returned as response")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Long createEmployee(
            @ApiParam(value = "New employee", required = true)
            @Valid @RequestBody final Employee employee) throws EmployeeDaoException {
        return employeeService.createEmployee(employee);
    }

    @ApiOperation(
            value = "Update Employee",
            notes = "change the values departmentId and job title",
            authorizations = {
                    @Authorization(
                            value = "Employee",
                            scopes = {@AuthorizationScope(scope = "update:employee", description = "update employee")}
                    )}
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New Employee has been created"),
            @ApiResponse(code = 400, message = "Bad Request — The request was invalid or cannot be served. The exact error should be explained in the error payload. E.g. „The JSON is not valid"),
            @ApiResponse(code = 404, message = "Not found — There is no resource behind the URI"),
            @ApiResponse(code = 500, message = "Internal Server Error — API developers should avoid this error. If an error occurs in the global catch blog, the stracktrace should be logged and not returned as response")
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateEmployee(
            @ApiParam(value = "Employee for update", required = true)
            @RequestBody final Employee employee) throws EmployeeDaoException {
        employeeService.updateEmployee(employee);
    }

    @ApiOperation(value = "Delete Employee by ID",
            authorizations = {
                    @Authorization(
                            value = "Employee",
                            scopes = {@AuthorizationScope(scope = "delete:employee", description = "delete employee by ID")}
                    )})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Everything is working"),
            @ApiResponse(code = 400, message = "Bad Request — The request was invalid or cannot be served. The exact error should be explained in the error payload. E.g. „The JSON is not valid"),
            @ApiResponse(code = 404, message = "Not found — There is no resource behind the URI"),
            @ApiResponse(code = 500, message = "Internal Server Error — API developers should avoid this error. If an error occurs in the global catch blog, the stracktrace should be logged and not returned as response")
    })
    @DeleteMapping(value = "/{employeeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public int deleteEmployee(
            @ApiParam(value = "ID for delete employee", required = true)
            @PathVariable final Integer employeeId) throws EmployeeDaoException {
        return employeeService.deleteEmployee(employeeId);
    }
}
