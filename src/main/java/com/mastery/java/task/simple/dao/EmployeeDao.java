package com.mastery.java.task.simple.dao;

import com.mastery.java.task.simple.dto.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class EmployeeDao {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private String findAllSql = "SELECT * FROM employee";

    public List<Employee> findAll() {
        return jdbcTemplate.query(findAllSql, BeanPropertyRowMapper.newInstance(Employee.class));
    }
}
