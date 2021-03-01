package com.mastery.java.task.simple.dao;

import com.mastery.java.task.simple.dto.Employee;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;


public class EmployeeMapper implements RowMapper<Employee> {
    
    @Override
    public Employee mapRow(ResultSet resultSet, int arg1) throws SQLException {
        Employee employee = new Employee();
        employee.setEmployeeId(Long.valueOf(resultSet.getString("employee_id")));
        employee.setFirstName(resultSet.getString("first_name"));
        employee.setLastName(resultSet.getString("last_name"));
        employee.setDepartmentId(Long.valueOf(resultSet.getString("department_id")));
        employee.setJobTitle(resultSet.getString("job_title"));
        employee.setGender(resultSet.getString("gender"));
        employee.setDateOfBirth(Instant.ofEpochMilli
                ((resultSet.getDate("date_of_birth")).
                        getTime()).atZone(ZoneId.systemDefault()).toLocalDate());

        return employee;
    }
}
