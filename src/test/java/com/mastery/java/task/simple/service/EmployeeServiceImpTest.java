package com.mastery.java.task.simple.service;

import com.mastery.java.task.simple.config.SimpleApplication;
import com.mastery.java.task.simple.dto.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;


@SpringBootTest(classes = SimpleApplication.class)
class EmployeeServiceImpTest {

    @Autowired
    EmployeeServiceImp employeeServiceImp;

    Employee employee = new Employee("Alex", "Poll",
            "male", 8L,
            "assistance", LocalDate.now());

    @Test
    void shouldFindAllEmployee() {
        List<Employee> employeeList = employeeServiceImp.findAll();
        assertNotNull(employeeList);
        assertTrue(employeeList.size() > 0);
    }

    @Test
    void ShouldCreateEmployee() {
        Long newEmployeeId = employeeServiceImp.createEmployee(employee);
        assertNotNull(newEmployeeId);
    }

    @Test
    void shouldFindEmployeeById() {
        Long newEmployeeId = employeeServiceImp.createEmployee(employee);

        Optional<Employee> optionalEmployee = employeeServiceImp.findById(Math.toIntExact(newEmployeeId));

        Assertions.assertTrue(optionalEmployee.isPresent());
        assertEquals(optionalEmployee.get().getEmployeeId(), newEmployeeId);
        assertEquals(optionalEmployee.get().getFirstName(), employee.getFirstName());
        assertEquals(optionalEmployee.get().getLastName(), employee.getLastName());
        assertEquals(optionalEmployee.get().getJobTitle(), employee.getJobTitle());
        assertEquals(optionalEmployee.get().getGender(), employee.getGender());
        assertEquals(optionalEmployee.get().getDepartmentId(), employee.getDepartmentId());
        assertEquals(optionalEmployee.get().getDateOfBirth(), employee.getDateOfBirth());
    }

    @Test
    void updateEmployee() {
        Long employeeId = employeeServiceImp.createEmployee(employee);
        assertNotNull(employeeId);

        Employee employeeForUpdate = employee;

        employeeForUpdate.setDepartmentId(777l);
        employeeForUpdate.setJobTitle("worker");

        employeeServiceImp.updateEmployee(employeeForUpdate);
        assertEquals(employee.getEmployeeId(), employeeForUpdate.getEmployeeId());
        assertEquals(employee.getFirstName(), employeeForUpdate.getFirstName());
        assertEquals(employee.getLastName(), employeeForUpdate.getLastName());
        assertEquals(employee.getDepartmentId(), employeeForUpdate.getDepartmentId());
        assertEquals(employee.getJobTitle(), employeeForUpdate.getJobTitle());
        assertEquals(employee.getGender(), employeeForUpdate.getGender());
        assertEquals(employee.getDateOfBirth(), employeeForUpdate.getDateOfBirth());
    }

    @Test
    void deleteEmployee() {
        List<Employee> employeeList = employeeServiceImp.findAll();
        assertNotNull(employeeList);

        employeeServiceImp.deleteEmployee(1);

        List<Employee> employeesFlightAfterDelete = employeeServiceImp.findAll();
        assertNotNull(employeesFlightAfterDelete);

        assertTrue(employeeList.size() - 1 == employeesFlightAfterDelete.size());
    }
}
