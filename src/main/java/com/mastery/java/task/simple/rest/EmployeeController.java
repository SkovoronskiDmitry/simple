package com.mastery.java.task.simple.rest;

import com.mastery.java.task.simple.dao.exception.EmployeeDaoException;
import com.mastery.java.task.simple.dto.Employee;
import com.mastery.java.task.simple.rest.exception.EmployeeNotFoundException;
import com.mastery.java.task.simple.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(value = "/employee")
    public Collection<Employee> findAll() throws EmployeeDaoException {
        return employeeService.findAll();
    }

    @GetMapping(value = "/employee/{employeeId}")
    public ResponseEntity<Employee> findById(@PathVariable final Integer employeeId) {
        Optional<Employee> optionalEmployee = employeeService.findById(employeeId);
        return optionalEmployee.map(employee -> new ResponseEntity<>(employee, HttpStatus.OK)).
                orElseGet(() -> new ResponseEntity(new EmployeeNotFoundException(employeeId), HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "/createEmployee")
    public ResponseEntity<Long> createEmployee(@Valid @RequestBody final Employee employee) throws EmployeeDaoException {
        final Long employeeId = employeeService.createEmployee(employee);
        return new ResponseEntity<>(employeeId, HttpStatus.OK);
    }

    @PutMapping(value = "/updateEmployee")
    public ResponseEntity<Boolean> updateEmployee(@RequestBody final Employee employee) {
        employeeService.updateEmployee(employee);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteEmployee/{employeeId}")
    public ResponseEntity<Integer> deleteEmployee(@PathVariable final Integer employeeId) {
        return new ResponseEntity(employeeService.deleteEmployee(employeeId), HttpStatus.OK);
    }
}
