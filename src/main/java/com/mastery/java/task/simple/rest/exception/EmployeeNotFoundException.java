package com.mastery.java.task.simple.rest.exception;

public class EmployeeNotFoundException extends Exception{

    public EmployeeNotFoundException(final String data) {
        super("Employee not found with data: " + data);
    }
}
