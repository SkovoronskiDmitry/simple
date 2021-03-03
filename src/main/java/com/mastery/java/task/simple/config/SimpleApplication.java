package com.mastery.java.task.simple.config;

import com.mastery.java.task.simple.dao.EmployeeDaoImp;
import com.mastery.java.task.simple.service.EmployeeServiceImp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.mastery.java.task.simple"})
public class SimpleApplication {

    private final EmployeeServiceImp employeeServiceImp;

    public SimpleApplication(final EmployeeServiceImp employeeServiceImp) {
        this.employeeServiceImp = employeeServiceImp;
    }

    public static void main(final String[] args) {
        ApplicationContext context = SpringApplication.run(SimpleApplication.class, args);
    }
}
