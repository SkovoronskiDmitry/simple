package com.mastery.java.task.simple.service;

import com.mastery.java.task.simple.dao.EmployeeDaoImp;
import com.mastery.java.task.simple.dao.exception.EmployeeDaoException;
import com.mastery.java.task.simple.dto.Employee;
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
class EmployeeServiceTest {

    private static Employee employeeForTest = new Employee(
            "Alex",
            "Poll",
            "male",
            8L,
            "assistance",
            LocalDate.now());

    @InjectMocks
    private EmployeeServiceImp employeeService;

    @Mock
    private EmployeeDaoImp employeeDao;

    @After
    public void tirDown() {
        verifyNoInteractions(employeeDao);
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldFindAllEmployee() throws EmployeeDaoException {
        // given
        final List<Employee> employeeList = new LinkedList<>();
        employeeList.add(employeeForTest);
        when(employeeDao.findAll()).thenReturn(employeeList);

        // when
        final List<Employee> resultListEmployee = employeeService.findAll();

        // then
        Mockito.verify(employeeDao, Mockito.times(1)).findAll();
        Assertions.assertNotNull(resultListEmployee);
    }

    @Test
    public void shouldFindEmployeeById() {
        // given
        Mockito.when(employeeDao.findById(any(Integer.class))).thenReturn(Optional.ofNullable(employeeForTest));

        // when
        final Optional<Employee> optionalEmployee = employeeService.findById(any(Integer.class));

        // then
        Mockito.verify(employeeDao, Mockito.times(1)).findById(0);
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
    public void shouldCreateEmployee() throws EmployeeDaoException {
        // given
        when(employeeDao.createEmployee(employeeForTest)).thenReturn(any(Long.class));

        // when
        final Long employeeId = employeeService.createEmployee(employeeForTest);

        // then
        Mockito.verify(employeeDao, Mockito.times(1)).createEmployee(employeeForTest);
        assertThat(employeeId, CoreMatchers.notNullValue());
    }

    @Test
    public void shouldUpdateEmployee() {
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
        })).when(employeeDao).updateEmployee(employeeForUpdate);

        // when
        employeeService.updateEmployee(employeeForUpdate);

        // then
        Mockito.verify(employeeDao, Mockito.times(1)).updateEmployee(employeeForUpdate);
        assertThat(employeeForTest.getFirstName(), CoreMatchers.is(employeeForUpdate.getFirstName()));
        Assertions.assertEquals(employeeForTest.getLastName(), employeeForUpdate.getLastName());
        Assertions.assertEquals(employeeForTest.getDepartmentId(), employeeForUpdate.getDepartmentId());
        Assertions.assertEquals(employeeForTest.getJobTitle(), employeeForUpdate.getJobTitle());
        Assertions.assertEquals(employeeForTest.getGender(), employeeForUpdate.getGender());
        Assertions.assertEquals(employeeForTest.getDateOfBirth(), employeeForUpdate.getDateOfBirth());
    }

    @Test
    public void shouldDeleteEmployee() throws EmployeeDaoException {
        // given
        when(employeeDao.deleteEmployee(any(Integer.class))).thenReturn(1);

        // when
        final int resultOfDelete = employeeService.deleteEmployee(1);

        // then
        Mockito.verify(employeeDao, Mockito.times(1)).deleteEmployee(any(Integer.class));
        assertThat(resultOfDelete, CoreMatchers.equalTo(1));
    }

    @Test
    public void testExceptionFindAll() throws EmployeeDaoException {
        // given
        Mockito.doThrow(EmployeeDaoException.class).when(employeeDao).findAll();

        // when
        final Throwable throwable = catchThrowable(() -> employeeService.findAll());

        // then
        verify(employeeDao).findAll();
        assertThat(throwable, Matchers.instanceOf(EmployeeDaoException.class));
    }

    @Test
    public void testExceptionCreateEmployee() throws EmployeeDaoException {
        // given
        Mockito.doThrow(EmployeeDaoException.class).when(employeeDao).createEmployee(employeeForTest);

        // when
        final Throwable throwable = catchThrowable(() -> employeeService.createEmployee(employeeForTest));

        // then
        verify(employeeDao).createEmployee(employeeForTest);
        assertThat(throwable, Matchers.instanceOf(EmployeeDaoException.class));
    }
}
