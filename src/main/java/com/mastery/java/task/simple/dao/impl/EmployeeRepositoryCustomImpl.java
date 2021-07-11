package com.mastery.java.task.simple.dao.impl;

import com.mastery.java.task.simple.dao.EmployeeRepositoryCustom;
import com.mastery.java.task.simple.dto.Employee;
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
    public List<Employee> findByFirstNameAndLastName(String firstName, String lastName) {
        Query query = entityManager.createNativeQuery(
                "SELECT * FROM Employee WHERE FirstName = :firstName AND LastName = :lastName;");
        return query.getResultList();
    }
}
