package com.mastery.java.task.simple.rest.exception;

public class EmployeeNotFoundException extends Exception{

    public EmployeeNotFoundException(final Integer employeeId) {
        super("Employee not found for id: " + employeeId);
    }
}
