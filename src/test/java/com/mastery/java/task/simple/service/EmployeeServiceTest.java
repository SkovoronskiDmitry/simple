package com.mastery.java.task.simple.service;

import com.mastery.java.task.simple.dao.EmployeeDaoImp;
import com.mastery.java.task.simple.dao.exception.EmployeeDaoException;
import com.mastery.java.task.simple.dto.Employee;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class EmployeeServiceTest {

    private final static Logger LOGGER = Logger.getLogger(EmployeeServiceTest.class);

    @InjectMocks
    private static EmployeeServiceImp employeeService;

    @Mock
    private static EmployeeDaoImp employeeDao;

    private static Employee employee;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        employee = new Employee(
                "Alex",
                "Poll",
                "male",
                8L,
                "assistance",
                LocalDate.now());
    }

    @Test
    public void shouldUpdateEmployee() {
        LOGGER.info("Test Update-method with mock");
        // given
        when(employeeDao.findById(any(Integer.class))).thenReturn(Optional.ofNullable(employee));
        Employee employeeForUpdate = employee;
        employeeForUpdate.setDepartmentId(87l);
        employeeForUpdate.setJobTitle("boss");

        // when
        employeeService.updateEmployee(employeeForUpdate);

        // then
        Assertions.assertEquals(employee.getFirstName(), employeeForUpdate.getFirstName());
        Assertions.assertEquals(employee.getLastName(), employeeForUpdate.getLastName());
        Assertions.assertEquals(employee.getDepartmentId(), employeeForUpdate.getDepartmentId());
        Assertions.assertEquals(employee.getJobTitle(), employeeForUpdate.getJobTitle());
        Assertions.assertEquals(employee.getGender(), employeeForUpdate.getGender());
        Assertions.assertEquals(employee.getDateOfBirth(), employeeForUpdate.getDateOfBirth());
    }

    @Test
    public void shouldFindAllEmployee() throws EmployeeDaoException {
        LOGGER.info("Test Find all-method with mock");
        // given
        List<Employee> employeeList = new LinkedList<>();
        employeeList.add(employee);
        when(employeeDao.findAll()).thenReturn(employeeList);

        // when
        List<Employee> resultListEmployee = employeeService.findAll();

        // then
        Assertions.assertNotNull(resultListEmployee);
    }

    @Test
    public void shouldCreateEmployee() throws EmployeeDaoException {
        LOGGER.info("Test Create-method with mock");
        // given
        when(employeeDao.createEmployee(employee)).thenReturn(any(Long.class));

        // when
        Long employeeId = employeeService.createEmployee(employee);

        // then
        Assertions.assertNotNull(employeeId);
    }

    @Test
    public void shouldFindEmployeeById() throws EmployeeDaoException {
        LOGGER.info("Test Find by ID-method with mock");
        // given
        when(employeeDao.findById(any(Integer.class))).thenReturn(Optional.ofNullable(employee));

        // when
        Optional<Employee> optionalEmployee = employeeService.findById(any(Integer.class));

        // then
        Assertions.assertTrue(optionalEmployee.isPresent());
        Assertions.assertEquals(optionalEmployee.get().getFirstName(), employee.getFirstName());
        Assertions.assertEquals(optionalEmployee.get().getLastName(), employee.getLastName());
        Assertions.assertEquals(optionalEmployee.get().getJobTitle(), employee.getJobTitle());
        Assertions.assertEquals(optionalEmployee.get().getGender(), employee.getGender());
        Assertions.assertEquals(optionalEmployee.get().getDepartmentId(), employee.getDepartmentId());
        Assertions.assertEquals(optionalEmployee.get().getDateOfBirth(), employee.getDateOfBirth());
    }
}
