package com.mastery.java.task.simple.dao;

import com.mastery.java.task.simple.dao.exception.EmployeeDaoException;
import com.mastery.java.task.simple.dao.field.EmployeeColumnNames;
import com.mastery.java.task.simple.dto.Employee;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@Transactional
public class EmployeeDaoImp implements EmployeeDao {

    private final static Logger LOGGER = Logger.getLogger(EmployeeDaoImp.class);

    private static final String EMPLOYEE_ID = EmployeeColumnNames.EMPLOYEE_ID;
    private static final String FIRST_NAME = EmployeeColumnNames.FIRST_NAME;
    private static final String LAST_NAME = EmployeeColumnNames.LAST_NAME;
    private static final String DEPARTMENT_ID = EmployeeColumnNames.DEPARTMENT_ID;
    private static final String JOB_TITLE = EmployeeColumnNames.JOB_TITLE;
    private static final String GENDER = EmployeeColumnNames.GENDER;
    private static final String DATE_OF_BIRTH = EmployeeColumnNames.DATE_OF_BIRTH;

    private static final String FIND_ALL_SQL = "SELECT * FROM employee";
    private static final String FIND_BY_ID_SQL = "SELECT * FROM employee WHERE " + EMPLOYEE_ID + " = :" + EMPLOYEE_ID;
    private static final String CREATE_EMPLOYEE_SQL = "INSERT INTO employee (" + FIRST_NAME + ", " + LAST_NAME + " ," + DEPARTMENT_ID + ", " + JOB_TITLE + ", " + GENDER + ", " + DATE_OF_BIRTH + ") VALUES (:" + FIRST_NAME + ", :" + LAST_NAME + ", :" + DEPARTMENT_ID + ", :" + JOB_TITLE + ", :" + GENDER + ", :" + DATE_OF_BIRTH + ")";
    private static final String UPDATE_EMPLOYEE_SQL = "UPDATE employee SET " + DEPARTMENT_ID + " = :" + DEPARTMENT_ID + ", " + JOB_TITLE + " = :" + JOB_TITLE + " WHERE " + EMPLOYEE_ID + " = :" + EMPLOYEE_ID;
    private static final String DELETE_EMPLOYEE_SQL = "DELETE FROM employee WHERE " + EMPLOYEE_ID + " = :" + EMPLOYEE_ID;

    private final NamedParameterJdbcTemplate template;
    private final EmployeeMapper employeeMapper;

    @Autowired
    public EmployeeDaoImp(final NamedParameterJdbcTemplate template, final EmployeeMapper employeeMapper) {
        this.template = template;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public List<Employee> findAll() throws EmployeeDaoException {
        LOGGER.info("Find all employee");
        try {
            return template.query(FIND_ALL_SQL, employeeMapper);
        } catch (final DataAccessException ex) {
            final String errorMessage = "Failed to get all employees";
            throw new EmployeeDaoException(errorMessage, ex);
        }
    }

    @Override
    public Optional<Employee> findById(final Integer employeeId) throws EmployeeDaoException {
        LOGGER.info("Find employee by ID: " + employeeId);
        try {
            final SqlParameterSource namedParameters = new MapSqlParameterSource(EMPLOYEE_ID, employeeId);
            final Employee employee = template.queryForObject(FIND_BY_ID_SQL, namedParameters, employeeMapper);
            return Optional.ofNullable(employee);
        } catch (final RuntimeException ex) {
            final String errorMessage = "Failed to get all employees";
            throw new EmployeeDaoException(errorMessage, ex);
        }
    }

    @Override
    public Long createEmployee(final Employee employee) throws EmployeeDaoException {
        LOGGER.info("Create a new employee: " + employee.toString());
        try {
            final KeyHolder keyHolder = new GeneratedKeyHolder();
            template.update(CREATE_EMPLOYEE_SQL, mapSqlParameterSource(employee), keyHolder, new String[]{EMPLOYEE_ID});
            return Objects.requireNonNull(keyHolder.getKey()).longValue();
        } catch (final NullPointerException ex) {
            final String errorMassage = "Employee not created";
            throw new EmployeeDaoException(errorMassage, ex);
        }
    }

    @Override
    public void updateEmployee(final Employee employee) throws EmployeeDaoException {
        LOGGER.info("Update the employee");
        try {
            template.update(UPDATE_EMPLOYEE_SQL, mapSqlParameterSource(employee));
        } catch (final RuntimeException ex) {
            final String errorMassage = "Check the employee passed to the method ";
            throw new EmployeeDaoException(errorMassage, ex);
        }
    }

    @Override
    public int deleteEmployee(final Integer employeeId) throws EmployeeDaoException {
        LOGGER.info("Delete the employee with ID: " + employeeId);
        try {
            final MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource().addValue(EMPLOYEE_ID, employeeId);
            return template.update(DELETE_EMPLOYEE_SQL, mapSqlParameterSource);
        } catch (final RuntimeException ex) {
            final String errorMassage = "Employee not found with ID:" + employeeId;
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
