package com.mastery.java.task.simple.service;

import com.mastery.java.task.simple.SimpleApplication;
import com.mastery.java.task.simple.dao.exception.EmployeeDaoException;
import com.mastery.java.task.simple.dto.Employee;
import org.apache.log4j.Logger;
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

    private static final Logger LOGGER = Logger.getLogger(EmployeeServiceImpTest.class);

    private final static Employee employee = new Employee("Alex", "Poll",
            "male", 8L,
            "assistance", LocalDate.now());

    @Autowired
    private EmployeeService employeeService;

    @Test
    void shouldFindAllEmployee() throws EmployeeDaoException {
        LOGGER.info("TEST method: Find all Employees");
        List<Employee> employeeList = employeeService.findAll();

        assertNotNull(employeeList);
        assertTrue("", employeeList.size() > 0);
    }

    @Test
    void ShouldCreateEmployee() throws EmployeeDaoException {
        Long newEmployeeId = employeeService.createEmployee(employee);
        LOGGER.info("TEST method: Employee created with ID:" + newEmployeeId);
        assertNotNull(newEmployeeId);
    }

    @Test
    void shouldFindEmployeeById() throws EmployeeDaoException {
        Long newEmployeeId = employeeService.createEmployee(employee);

        Optional<Employee> optionalEmployee = employeeService.findById(Math.toIntExact(newEmployeeId));

        LOGGER.info("TEST method: Find Employee by ID: " + newEmployeeId + optionalEmployee.get());

        Assertions.assertTrue(optionalEmployee.isPresent());
        assertEquals(optionalEmployee.get().getEmployeeId(), newEmployeeId);
        Assertions.assertEquals(optionalEmployee.get().getFirstName(), employee.getFirstName());
        assertEquals(optionalEmployee.get().getLastName(), employee.getLastName());
        assertEquals(optionalEmployee.get().getJobTitle(), employee.getJobTitle());
        assertEquals(optionalEmployee.get().getGender(), employee.getGender());
        assertEquals(optionalEmployee.get().getDepartmentId(), employee.getDepartmentId());
        assertEquals(optionalEmployee.get().getDateOfBirth(), employee.getDateOfBirth());
    }

    @Test
    void updateEmployee() throws EmployeeDaoException {
        Long employeeId = employeeService.createEmployee(employee);
        assertNotNull(employeeId);

        Employee employeeForUpdate = employee;

        employeeForUpdate.setDepartmentId(777l);
        employeeForUpdate.setJobTitle("worker");
        LOGGER.info("TEST method: Update Employee:" + employee.equals(employeeForUpdate));
        employeeService.updateEmployee(employeeForUpdate);
        assertEquals(employee.getEmployeeId(), employeeForUpdate.getEmployeeId());
        assertEquals(employee.getFirstName(), employeeForUpdate.getFirstName());
        assertEquals(employee.getLastName(), employeeForUpdate.getLastName());
        assertEquals(employee.getDepartmentId(), employeeForUpdate.getDepartmentId());
        assertEquals(employee.getJobTitle(), employeeForUpdate.getJobTitle());
        assertEquals(employee.getGender(), employeeForUpdate.getGender());
        assertEquals(employee.getDateOfBirth(), employeeForUpdate.getDateOfBirth());
    }

    @Test
    void deleteEmployee() throws EmployeeDaoException {
        List<Employee> employeeList = employeeService.findAll();
        assertNotNull(employeeList);

        employeeService.deleteEmployee(1);

        List<Employee> employeesFlightAfterDelete = employeeService.findAll();
        assertNotNull(employeesFlightAfterDelete);
        LOGGER.info("TEST method: Delete Employee:" + (employeeList.size() - 1 == employeesFlightAfterDelete.size()));
        assertTrue(employeeList.size() - 1 == employeesFlightAfterDelete.size());
    }
}
