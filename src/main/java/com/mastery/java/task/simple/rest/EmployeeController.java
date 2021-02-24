package com.mastery.java.task.simple.rest;

import com.mastery.java.task.simple.dto.Employee;
import com.mastery.java.task.simple.service.EmployeeServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public Optional<Employee> findById(@PathVariable Integer employeeId) {
        return employeeServiceImp.findById(employeeId);
    }

    @PostMapping(value = "/createEmployee")
    public Long createEmployee(@RequestBody Employee employee) {
        return employeeServiceImp.createEmployee(employee);
    }
    @PostMapping(value = "/updateEmployee")
    public void updateEmployee(@RequestBody Employee employee){
        employeeServiceImp.updateEmployee(employee);
    }

    @DeleteMapping(value = "employee/{employeeId}")
    public ResponseEntity<Integer> deleteEmployee(@PathVariable Integer employeeId){
        return new ResponseEntity(employeeServiceImp.deleteEmployee(employeeId), HttpStatus.OK);
    }


}
