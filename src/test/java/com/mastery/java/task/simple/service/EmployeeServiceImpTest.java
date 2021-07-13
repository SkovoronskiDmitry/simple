package com.mastery.java.task.simple.service;

import com.mastery.java.task.simple.SimpleApplication;
import com.mastery.java.task.simple.dto.Employee;
import com.mastery.java.task.simple.service.employee.EmployeeService;
import com.mastery.java.task.simple.service.exception.EmployeeServiceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
            "assistance", LocalDate.of(1987, 04, 13));

    @Autowired
    private EmployeeService employeeService;

    @Test
    void shouldFindAllEmployee() throws EmployeeServiceException {
        LOGGER.info("TEST method: Find all Employees");
        List<Employee> employeeList = employeeService.findAll();

        Assertions.assertNotNull(employeeList);
        Assertions.assertTrue(employeeList.size() > 0, "");
    }

    @Test
    void ShouldCreateEmployee() throws EmployeeServiceException {
        Employee employeeResultTest = employeeService.createEmployee(EMPLOYEE);
        LOGGER.info("TEST create method: Employee created: {}", employeeResultTest);
        Assertions.assertNotNull(employeeResultTest);
    }

    @Test
    void shouldFindEmployeeById() throws EmployeeServiceException {
        Employee employeeResultTest = employeeService.createEmployee(EMPLOYEE);

        Optional<Employee> optionalEmployee = employeeService.findById(employeeResultTest.getEmployeeId());

        LOGGER.info("TEST method: Find Employee by ID: {} {}", employeeResultTest.getEmployeeId(), optionalEmployee.get());

        Assertions.assertTrue(optionalEmployee.isPresent());
        Assertions.assertEquals(optionalEmployee.get().getEmployeeId(), EMPLOYEE.getEmployeeId());
        Assertions.assertEquals(optionalEmployee.get().getFirstName(), EMPLOYEE.getFirstName());
        Assertions.assertEquals(optionalEmployee.get().getLastName(), EMPLOYEE.getLastName());
        Assertions.assertEquals(optionalEmployee.get().getJobTitle(), EMPLOYEE.getJobTitle());
        Assertions.assertEquals(optionalEmployee.get().getGender(), EMPLOYEE.getGender());
        Assertions.assertEquals(optionalEmployee.get().getDepartmentId(), EMPLOYEE.getDepartmentId());
        Assertions.assertEquals(optionalEmployee.get().getDateOfBirth(), EMPLOYEE.getDateOfBirth());
    }

    @Test
    void updateEmployee() throws EmployeeServiceException {
        Employee employeeResultTest = employeeService.createEmployee(EMPLOYEE);
        Assertions.assertNotNull(employeeResultTest);

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
    void deleteEmployee() throws EmployeeServiceException {
        List<Employee> employeeList = employeeService.findAll();
        Assertions.assertNotNull(employeeList);

        employeeService.deleteEmployee(1L);

        List<Employee> employeesFlightAfterDelete = employeeService.findAll();
        Assertions.assertNotNull(employeesFlightAfterDelete);
        LOGGER.info("TEST method: Delete Employee: {}", (employeeList.size() - 1 == employeesFlightAfterDelete.size()));
        Assertions.assertEquals(employeesFlightAfterDelete.size(), employeeList.size() - 1);
    }
}
