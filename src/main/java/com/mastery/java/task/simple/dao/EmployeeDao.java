package com.mastery.java.task.simple.dao;

import com.mastery.java.task.simple.dto.Employee;
import com.mastery.java.task.simple.dto.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class EmployeeDao extends JdbcDaoSupport {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }


    private String findAllSql = "SELECT * FROM employee";

    public List<Employee> findAll() {
        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(findAllSql);

        List<Employee> result = new ArrayList<Employee>();
        for (Map<String, Object> row : rows) {
            Employee employee = new Employee();
            employee.setEmployeeId((Long) row.get("employee_id"));
            employee.setFirstName((String) row.get("first_name"));
            employee.setLastName((String) row.get("last_name"));
            employee.setDepartmentId((Long) row.get("department_id"));
            employee.setGender((Gender) row.get("gender"));
            employee.setJobTitle((String) row.get("job_title"));
            employee.setDateOfBirth((LocalDate) row.get("date_of_birth"));
            result.add(employee);
        }
        return result;
    }
}
