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

        EmployeeServiceImp employeeServiceImp = context.getBean(EmployeeServiceImp.class);

        System.out.println(employeeServiceImp.findAll());

        System.out.println();

        System.out.println(employeeServiceImp.findById(3));

        Employee employee = new Employee();
        employee.setFirstName("Gena");
        employee.setLastName("Na");
        employee.setJobTitle("engine");
        employee.setDepartmentId(7l);
        employee.setDateOfBirth(LocalDate.now());
        employee.setGender("MALE");
        System.out.println("CREATE:"+employeeServiceImp.createEmployee(employee));
        System.out.println("created employee: "+employeeServiceImp.findAll());

        Employee employee2 = new Employee();
        employee2.setEmployeeId(1L);
        employee2.setFirstName("Gena");
        employee2.setLastName("Na");
        employee2.setJobTitle("developer");
        employee2.setDepartmentId(12_453l);
        employee2.setDateOfBirth(LocalDate.now());
        employee2.setGender("MALE");

        employeeServiceImp.updateEmployee(employee2);
        System.out.println("update Employee: "+employeeServiceImp.findAll());






    }


}
