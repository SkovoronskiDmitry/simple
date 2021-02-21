package com.mastery.java.task.simple.config;

import com.mastery.java.task.simple.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SimpleApplication {
    @Autowired
    EmployeeService employeeService;

    public static void main(final String[] args) {

        ApplicationContext context = SpringApplication.run(SimpleApplication.class, args);
        EmployeeService employeeService = context.getBean(EmployeeService.class);

        System.out.println(employeeService.findAll());


    }


}
