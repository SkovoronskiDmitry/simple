package com.mastery.java.task.simple.dao.impl;

import com.mastery.java.task.simple.dao.EmployeeRepositoryCustom;
import com.mastery.java.task.simple.dto.Employee;
import com.mastery.java.task.simple.service.exception.EmployeeServiceException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class EmployeeRepositoryCustomImpl implements EmployeeRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Employee> findByFirstNameAndLastName(String firstName, String lastName) throws EmployeeServiceException {
        Query query = entityManager.createNativeQuery(
                "SELECT * FROM Employee WHERE first_name = '"+firstName+"' AND last_name = '"+lastName+"'");
        System.out.println(query.getResultList());
        return query.getResultList();
    }
}
