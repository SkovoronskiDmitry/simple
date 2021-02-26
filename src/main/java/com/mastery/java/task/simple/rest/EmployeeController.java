package com.mastery.java.task.simple.rest;

import com.mastery.java.task.simple.dto.Employee;
import com.mastery.java.task.simple.service.EmployeeServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Optional;

@RestController()
public class EmployeeController {

    @Autowired
    private EmployeeServiceImp employeeServiceImp;

    @GetMapping(value = "/test")
    public String Request() {
        return "Example";
    }

    @GetMapping(value = "/employee")
    public Collection<Employee> findAll() {
        return employeeServiceImp.findAll();
    }

    @GetMapping(value = "/employee/{employeeId}")
    public ResponseEntity<Employee> findById(@PathVariable Integer employeeId) {
        Optional<Employee> optionalEmployee = employeeServiceImp.findById(employeeId);
        return optionalEmployee.isPresent()
                ? new ResponseEntity<>(optionalEmployee.get(), HttpStatus.OK)
                : new ResponseEntity(
                new FileNotFoundException(),
                HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/createEmployee")
    public ResponseEntity<Long> createEmployee(@RequestBody Employee employee) {
        Long employeeId = employeeServiceImp.createEmployee(employee);
        return new ResponseEntity<>(employeeId, HttpStatus.OK);
    }

    @PutMapping(value = "/updateEmployee")
    public ResponseEntity<Boolean> updateEmployee(@RequestBody Employee employee) {
        employeeServiceImp.updateEmployee(employee);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping(value = "employee/{employeeId}")
    public ResponseEntity<Integer> deleteEmployee(@PathVariable Integer employeeId) {
        return new ResponseEntity(employeeServiceImp.deleteEmployee(employeeId), HttpStatus.OK);
    }


}
