package com.mastery.java.task.simple.rest;

import com.mastery.java.task.simple.dao.exception.EmployeeDaoException;
import com.mastery.java.task.simple.dto.Employee;
import com.mastery.java.task.simple.service.employee.EmployeeService;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class EmployeeControllerTest {

    private static Employee employeeForTest = new Employee(
            "Alex",
            "Poll",
            "male",
            8L,
            "assistance",
            LocalDate.now());

    @InjectMocks
    private EmployeeController employeeController;
    @Mock
    private EmployeeService employeeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() {
        verifyNoInteractions(employeeService);
    }

    @Test
    void findAll() throws EmployeeDaoException {
        // given
        final List<Employee> employeeList = new LinkedList<>();
        employeeList.add(employeeForTest);
        employeeList.add(employeeForTest);
        when(employeeService.findAll()).thenReturn(employeeList);

        // when
        final List<Employee> resultListEmployee = employeeService.findAll();

        // then
        Mockito.verify(employeeService, Mockito.times(1)).findAll();
        Assertions.assertNotNull(resultListEmployee);
    }

    @Test
    void findById() throws EmployeeDaoException {
        // given
        Mockito.when(employeeService.findById(any(Integer.class))).thenReturn(Optional.ofNullable(employeeForTest));

        // when
        final Optional<Employee> optionalEmployee = employeeService.findById(any(Integer.class));

        // then
        Mockito.verify(employeeService, Mockito.times(1)).findById(0);
        Assertions.assertTrue(optionalEmployee.isPresent());
        assertThat(optionalEmployee, CoreMatchers.notNullValue());
        assertThat(employeeForTest.getFirstName(), CoreMatchers.is(optionalEmployee.get().getFirstName()));
        assertThat(employeeForTest.getLastName(), CoreMatchers.is(optionalEmployee.get().getLastName()));
        assertThat(employeeForTest.getJobTitle(), CoreMatchers.is(optionalEmployee.get().getJobTitle()));
        assertThat(employeeForTest.getGender(), CoreMatchers.is(optionalEmployee.get().getGender()));
        assertThat(employeeForTest.getDepartmentId(), CoreMatchers.is(optionalEmployee.get().getDepartmentId()));
        assertThat(employeeForTest.getDateOfBirth(), CoreMatchers.is(optionalEmployee.get().getDateOfBirth()));
    }

    @Test
    void createEmployee() throws EmployeeDaoException {
        // given
        when(employeeService.createEmployee(employeeForTest)).thenReturn(any(Long.class));

        // when
        final Long employeeId = employeeService.createEmployee(employeeForTest);

        // then
        Mockito.verify(employeeService, Mockito.times(1)).createEmployee(employeeForTest);
        assertThat(employeeId, CoreMatchers.notNullValue());
    }

    @Test
    void updateEmployee() throws EmployeeDaoException {
        // given
        final Employee employeeForUpdate = new Employee("Alex",
                "Poll",
                "male",
                69L,
                "boss",
                LocalDate.now());
        doAnswer((invocation -> {
            employeeForTest = invocation.getArgument(0);
            return null;
        })).when(employeeService).updateEmployee(employeeForUpdate);

        // when
        employeeService.updateEmployee(employeeForUpdate);

        // then
        Mockito.verify(employeeService, Mockito.times(1)).updateEmployee(employeeForUpdate);
        assertThat(employeeForTest.getFirstName(), CoreMatchers.is(employeeForUpdate.getFirstName()));
        Assertions.assertEquals(employeeForTest.getLastName(), employeeForUpdate.getLastName());
        Assertions.assertEquals(employeeForTest.getDepartmentId(), employeeForUpdate.getDepartmentId());
        Assertions.assertEquals(employeeForTest.getJobTitle(), employeeForUpdate.getJobTitle());
        Assertions.assertEquals(employeeForTest.getGender(), employeeForUpdate.getGender());
        Assertions.assertEquals(employeeForTest.getDateOfBirth(), employeeForUpdate.getDateOfBirth());
    }

    @Test
    void deleteEmployee() throws EmployeeDaoException {
        // given
        when(employeeService.deleteEmployee(any(Integer.class))).thenReturn(1);

        // when
        final int resultOfDelete = employeeService.deleteEmployee(1);

        // then
        Mockito.verify(employeeService, Mockito.times(1)).deleteEmployee(any(Integer.class));
        assertThat(resultOfDelete, CoreMatchers.equalTo(1));
    }

    @Test
    public void testExceptionFindAll() throws EmployeeDaoException {
        // given
        Mockito.doThrow(EmployeeDaoException.class).when(employeeService).findAll();

        // when
        final Throwable throwable = catchThrowable(() -> employeeService.findAll());

        // then
        verify(employeeService).findAll();
        assertThat(throwable, Matchers.instanceOf(EmployeeDaoException.class));
    }

    @Test
    public void testExceptionFindById() throws EmployeeDaoException {
        // given
        Mockito.doThrow(EmployeeDaoException.class).when(employeeService).findById(any(Integer.class));

        // when
        final Throwable throwable = catchThrowable(() -> employeeService.findById(any(Integer.class)));

        // then
        verify(employeeService).findById(any(Integer.class));
        assertThat(throwable, Matchers.instanceOf(EmployeeDaoException.class));
    }

    @Test
    public void testExceptionCreateEmployee() throws EmployeeDaoException {
        // given
        Mockito.doThrow(EmployeeDaoException.class).when(employeeService).createEmployee(employeeForTest);

        // when
        final Throwable throwable = catchThrowable(() -> employeeService.createEmployee(employeeForTest));

        // then
        verify(employeeService).createEmployee(employeeForTest);
        assertThat(throwable, Matchers.instanceOf(EmployeeDaoException.class));
    }

    @Test
    public void testExceptionUpdateEmployee() throws EmployeeDaoException {
        // given
        Mockito.doThrow(EmployeeDaoException.class).when(employeeService).updateEmployee(employeeForTest);

        // when
        final Throwable throwable = catchThrowable(() -> employeeService.updateEmployee(employeeForTest));

        // then
        verify(employeeService).updateEmployee(employeeForTest);
        assertThat(throwable, Matchers.instanceOf(EmployeeDaoException.class));
    }

    @Test
    public void testExceptionDeleteEmployee() throws EmployeeDaoException {
        // given
        Mockito.doThrow(EmployeeDaoException.class).when(employeeService).deleteEmployee(1);

        // when
        final Throwable throwable = catchThrowable(() -> employeeService.deleteEmployee(1));

        // then
        verify(employeeService).deleteEmployee(1);
        assertThat(throwable, Matchers.instanceOf(EmployeeDaoException.class));
    }
}