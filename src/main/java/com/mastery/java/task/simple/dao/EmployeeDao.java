package com.mastery.java.task.simple.dao;

import com.mastery.java.task.simple.dto.Employee;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDao {

    public EmployeeDao(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    NamedParameterJdbcTemplate template;

    private final String findAllSql = "SELECT * FROM employee";

    public List<Employee> findAll() {

        return template.query(findAllSql, new EmployeeMapper());
    }
}
