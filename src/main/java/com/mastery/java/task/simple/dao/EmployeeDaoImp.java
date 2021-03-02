package com.mastery.java.task.simple.dao;

import com.mastery.java.task.simple.dto.Employee;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class EmployeeDaoImp implements EmployeeDao {

    private static final String findAllSql = "SELECT * FROM employee";
    private static final String findByIdSql = "SELECT * FROM employee WHERE employee_id = :employee_id";
    private static final String createEmployeeSql = "INSERT INTO employee (first_name, last_name ,department_id, job_title, gender, date_of_birth) VALUES (:first_name, :last_name, :department_id, :job_title, :gender, :date_of_birth)";
    private static final String updateEmployeeSql = "UPDATE employee SET department_id = :department_id, job_title = :job_title WHERE employee_id = :employee_id";
    private static final String deleteEmployeeSql = "DELETE FROM employee WHERE employee_id = :employee_id";

    private final static EmployeeMapper employeeMapper = new EmployeeMapper();

    private final NamedParameterJdbcTemplate template;

    public EmployeeDaoImp(final NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    @Override
    public List<Employee> findAll() {
        return template.query(findAllSql, employeeMapper);
    }

    @Override
    public Optional<Employee> findById(final Integer employeeId) {
        final SqlParameterSource namedParameters = new MapSqlParameterSource("employee_id", employeeId);
        final List<Employee> resultList = template.query(
                findByIdSql, namedParameters, employeeMapper);
        Employee employee = DataAccessUtils.uniqueResult(resultList);
        return Optional.ofNullable(employee);
    }

    @Override
    public Long createEmployee(final Employee employee) {
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(createEmployeeSql,
                mapSqlParameterSource(employee), keyHolder, new String[]{"employee_id"});
        return keyHolder.getKey().longValue();
    }

    @Override
    public void updateEmployee(final Employee employee) {
        template.update(updateEmployeeSql, mapSqlParameterSource(employee));
    }

    @Override
    public int deleteEmployee(final Integer employeeId) {
        return template.update(deleteEmployeeSql,
                new MapSqlParameterSource().addValue("employee_id", employeeId));
    }

    private MapSqlParameterSource mapSqlParameterSource(final Employee employee) {
        return new MapSqlParameterSource()
                .addValue("employee_id", employee.getEmployeeId())
                .addValue("first_name", employee.getFirstName())
                .addValue("last_name", employee.getLastName())
                .addValue("job_title", employee.getJobTitle())
                .addValue("department_id", employee.getDepartmentId())
                .addValue("gender", employee.getGender())
                .addValue("date_of_birth", employee.getDateOfBirth());
    }
}
