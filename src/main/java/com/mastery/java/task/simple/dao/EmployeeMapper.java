package com.mastery.java.task.simple.dao;

import com.mastery.java.task.simple.dao.field.EmployeeColumnNames;
import com.mastery.java.task.simple.dto.Employee;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@Component
public class EmployeeMapper implements RowMapper<Employee> {

    private static final String EMPLOYEE_ID = EmployeeColumnNames.EMPLOYEE_ID;
    private static final String FIRST_NAME = EmployeeColumnNames.FIRST_NAME;
    private static final String LAST_NAME = EmployeeColumnNames.LAST_NAME;
    private static final String DEPARTMENT_ID = EmployeeColumnNames.DEPARTMENT_ID;
    private static final String JOB_TITLE = EmployeeColumnNames.JOB_TITLE;
    private static final String GENDER = EmployeeColumnNames.GENDER;
    private static final String DATE_OF_BIRTH = EmployeeColumnNames.DATE_OF_BIRTH;

    @Override
    public Employee mapRow(final ResultSet resultSet, final int arg) throws SQLException {
        final Employee employee = new Employee();
        final long dateInMilliSec = (resultSet.getDate(DATE_OF_BIRTH)).getTime();
        final LocalDate localDateWithResultSet = Instant.ofEpochMilli(dateInMilliSec).atZone(ZoneId.systemDefault()).toLocalDate();

        employee.setEmployeeId(Long.valueOf(resultSet.getString(EMPLOYEE_ID)));
        employee.setFirstName(resultSet.getString(FIRST_NAME));
        employee.setLastName(resultSet.getString(LAST_NAME));
        employee.setDepartmentId(Long.valueOf(resultSet.getString(DEPARTMENT_ID)));
        employee.setJobTitle(resultSet.getString(JOB_TITLE));
        employee.setGender(resultSet.getString(GENDER));
        employee.setDateOfBirth(localDateWithResultSet);
        return employee;
    }
}
