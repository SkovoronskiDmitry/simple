package com.mastery.java.task.simple.config;

import com.mastery.java.task.simple.dto.Employee;
import com.mastery.java.task.simple.dto.Gender;
import com.mastery.java.task.simple.service.EmployeeServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDate;

@SpringBootApplication
@ComponentScan(basePackages = {"com.mastery.java.task.simple"})
public class SimpleApplication {

    @Autowired
    EmployeeServiceImp employeeServiceImp;

    public static void main(final String[] args) {

        ApplicationContext context = SpringApplication.run(SimpleApplication.class, args);








    }


}
