package com.mastery.java.task.simple.service;

import com.mastery.java.task.simple.SimpleApplication;
import com.mastery.java.task.simple.dao.exception.EmployeeDaoException;
import com.mastery.java.task.simple.dto.Employee;
import com.mastery.java.task.simple.service.employee.EmployeeService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@SpringBootTest(classes = SimpleApplication.class)
class EmployeeServiceImpTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpTest.class);

    private final static Employee EMPLOYEE = new Employee("Alex", "Poll",
            "male", 8L,
            "assistance", LocalDate.now());

    @Autowired
    private EmployeeService employeeService;

    @Test
    void shouldFindAllEmployee() throws EmployeeDaoException {
        LOGGER.info("TEST method: Find all Employees");
        List<Employee> employeeList = employeeService.findAll();

        Assertions.assertNotNull(employeeList);
        Assertions.assertTrue(employeeList.size() > 0, "");
    }

    @Test
    void ShouldCreateEmployee() throws EmployeeDaoException {
        Long newEmployeeId = employeeService.createEmployee(EMPLOYEE);
        LOGGER.info("TEST method: Employee created with ID: {}", newEmployeeId);
        Assertions.assertNotNull(newEmployeeId);
    }

    @Test
    void shouldFindEmployeeById() throws EmployeeDaoException {
        Long newEmployeeId = employeeService.createEmployee(EMPLOYEE);

        Optional<Employee> optionalEmployee = employeeService.findById(Math.toIntExact(newEmployeeId));

        LOGGER.info("TEST method: Find Employee by ID: {} {}", newEmployeeId, optionalEmployee.get());

        Assertions.assertTrue(optionalEmployee.isPresent());
        Assertions.assertEquals(optionalEmployee.get().getEmployeeId(), newEmployeeId);
        Assertions.assertEquals(optionalEmployee.get().getFirstName(), EMPLOYEE.getFirstName());
        Assertions.assertEquals(optionalEmployee.get().getLastName(), EMPLOYEE.getLastName());
        Assertions.assertEquals(optionalEmployee.get().getJobTitle(), EMPLOYEE.getJobTitle());
        Assertions.assertEquals(optionalEmployee.get().getGender(), EMPLOYEE.getGender());
        Assertions.assertEquals(optionalEmployee.get().getDepartmentId(), EMPLOYEE.getDepartmentId());
        Assertions.assertEquals(optionalEmployee.get().getDateOfBirth(), EMPLOYEE.getDateOfBirth());
    }

    @Test
    void updateEmployee() throws EmployeeDaoException {
        Long employeeId = employeeService.createEmployee(EMPLOYEE);
        Assertions.assertNotNull(employeeId);

        Employee employeeForUpdate = EMPLOYEE;

        employeeForUpdate.setDepartmentId(777l);
        employeeForUpdate.setJobTitle("worker");
        LOGGER.info("TEST method: Update Employee: {}", EMPLOYEE.equals(employeeForUpdate));
        employeeService.updateEmployee(employeeForUpdate);
        Assertions.assertEquals(EMPLOYEE.getEmployeeId(), employeeForUpdate.getEmployeeId());
        Assertions.assertEquals(EMPLOYEE.getFirstName(), employeeForUpdate.getFirstName());
        Assertions.assertEquals(EMPLOYEE.getLastName(), employeeForUpdate.getLastName());
        Assertions.assertEquals(EMPLOYEE.getDepartmentId(), employeeForUpdate.getDepartmentId());
        Assertions.assertEquals(EMPLOYEE.getJobTitle(), employeeForUpdate.getJobTitle());
        Assertions.assertEquals(EMPLOYEE.getGender(), employeeForUpdate.getGender());
        Assertions.assertEquals(EMPLOYEE.getDateOfBirth(), employeeForUpdate.getDateOfBirth());
    }

    @Test
    void deleteEmployee() throws EmployeeDaoException {
        List<Employee> employeeList = employeeService.findAll();
        Assertions.assertNotNull(employeeList);

        employeeService.deleteEmployee(1);

        List<Employee> employeesFlightAfterDelete = employeeService.findAll();
        Assertions.assertNotNull(employeesFlightAfterDelete);
        LOGGER.info("TEST method: Delete Employee: {}" , (employeeList.size() - 1 == employeesFlightAfterDelete.size()));
        Assertions.assertEquals(employeesFlightAfterDelete.size(), employeeList.size() - 1);
    }
}
