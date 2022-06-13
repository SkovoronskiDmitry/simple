package com.mastery.java.task.simple.dao.impl;

import com.mastery.java.task.simple.dao.EmployeeDao;
import com.mastery.java.task.simple.dao.exception.EmployeeDaoException;
import com.mastery.java.task.simple.dao.field.EmployeeColumnNames;
import com.mastery.java.task.simple.dao.field.SqlRequests;
import com.mastery.java.task.simple.dao.mapper.EmployeeMapper;
import com.mastery.java.task.simple.dto.Employee;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
public class EmployeeDaoImp implements EmployeeDao {

    private static final String EMPLOYEE_ID = EmployeeColumnNames.EMPLOYEE_ID;
    private static final String FIRST_NAME = EmployeeColumnNames.FIRST_NAME;
    private static final String LAST_NAME = EmployeeColumnNames.LAST_NAME;
    private static final String DEPARTMENT_ID = EmployeeColumnNames.DEPARTMENT_ID;
    private static final String JOB_TITLE = EmployeeColumnNames.JOB_TITLE;
    private static final String GENDER = EmployeeColumnNames.GENDER;
    private static final String DATE_OF_BIRTH = EmployeeColumnNames.DATE_OF_BIRTH;

    private static final String FIND_ALL_SQL = SqlRequests.FIND_ALL_SQL;
    private static final String FIND_BY_ID_SQL = SqlRequests.FIND_BY_ID_SQL;
    private static final String CREATE_EMPLOYEE_SQL = SqlRequests.CREATE_EMPLOYEE_SQL;
    private static final String UPDATE_EMPLOYEE_SQL = SqlRequests.UPDATE_EMPLOYEE_SQL;
    private static final String DELETE_EMPLOYEE_SQL = SqlRequests.DELETE_EMPLOYEE_SQL;

    private final NamedParameterJdbcTemplate template;
    private final EmployeeMapper employeeMapper;

    @Override
    public List<Employee> findAll() throws EmployeeDaoException {
        log.info("Find all employee");
        try {
            return template.query(FIND_ALL_SQL, employeeMapper);
        } catch (final DataAccessException ex) {
            throw new EmployeeDaoException("Failed to get all employees", ex);
        }
    }

    @Override
    public Optional<Employee> findById(final Integer employeeId) throws EmployeeDaoException {
        log.info("Find employee by ID: {}", employeeId);
        try {
            final SqlParameterSource namedParameters = new MapSqlParameterSource(EMPLOYEE_ID, employeeId);
            final Employee employee = template.queryForObject(FIND_BY_ID_SQL, namedParameters, employeeMapper);
            return Optional.ofNullable(employee);
        } catch (final DataAccessException ex) {
            final String errorMessage = String.format("Failed to get employee by ID: %s", employeeId);
            throw new EmployeeDaoException(errorMessage, ex);
        }
    }

    @Override
    public Long createEmployee(final Employee employee) throws EmployeeDaoException {
        log.info("Create a new employee: {}", employee);
        try {
            final KeyHolder keyHolder = new GeneratedKeyHolder();
            template.update(CREATE_EMPLOYEE_SQL, mapSqlParameterSource(employee), keyHolder, new String[]{EMPLOYEE_ID});
            return Objects.requireNonNull(keyHolder.getKey()).longValue();
        } catch (final DataAccessException ex) {
            throw new EmployeeDaoException("Some issues with SQL", ex);
        } catch (final NullPointerException ex) {
            throw new EmployeeDaoException("Employee not created", ex);
        }
    }

    @Override
    public void updateEmployee(final Employee employee) throws EmployeeDaoException {
        log.info("Update the employee: {}", employee);
        try {
            template.update(UPDATE_EMPLOYEE_SQL, mapSqlParameterSource(employee));

        } catch (final RuntimeException ex) {
            throw new EmployeeDaoException("Check the employee passed to the method", ex);
        }
    }

    @Override
    public int deleteEmployee(final Integer employeeId) throws EmployeeDaoException {
        log.info("Delete the employee with ID: {}", employeeId);
        try {
            final MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource().addValue(EMPLOYEE_ID, employeeId);
            return template.update(DELETE_EMPLOYEE_SQL, mapSqlParameterSource);
        } catch (final RuntimeException ex) {
            final String errorMassage = String.format("Employee not found with ID: %s", employeeId);
            throw new EmployeeDaoException(errorMassage, ex);
        }
    }

    private MapSqlParameterSource mapSqlParameterSource(final Employee employee) {
        return new MapSqlParameterSource()
                .addValue(EMPLOYEE_ID, employee.getEmployeeId())
                .addValue(FIRST_NAME, employee.getFirstName())
                .addValue(LAST_NAME, employee.getLastName())
                .addValue(JOB_TITLE, employee.getJobTitle())
                .addValue(DEPARTMENT_ID, employee.getDepartmentId())
                .addValue(GENDER, employee.getGender())
                .addValue(DATE_OF_BIRTH, employee.getDateOfBirth());
    }
}
