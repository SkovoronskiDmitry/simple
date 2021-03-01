package com.mastery.java.task.simple.rest.exceptions;

public class EmployeeNotFoundException extends RuntimeException{

    public EmployeeNotFoundException(Integer employeeId) {
        super("Employee not found for id: " + employeeId);
    }
}
