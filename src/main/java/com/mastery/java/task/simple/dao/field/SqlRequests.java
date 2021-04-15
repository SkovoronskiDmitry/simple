package com.mastery.java.task.simple.dao.field;

import static com.mastery.java.task.simple.dao.field.EmployeeColumnNames.*;

public final class SqlRequests {

    public static final String FIND_ALL_SQL = "SELECT * FROM employee";
    public static final String FIND_BY_ID_SQL = "SELECT * FROM employee WHERE " + EMPLOYEE_ID + " = :" + EMPLOYEE_ID;
    public static final String CREATE_EMPLOYEE_SQL = "INSERT INTO employee (" + FIRST_NAME + ", " + LAST_NAME + " ," + DEPARTMENT_ID + ", " + JOB_TITLE + ", " + GENDER + ", " + DATE_OF_BIRTH + ") VALUES (:" + FIRST_NAME + ", :" + LAST_NAME + ", :" + DEPARTMENT_ID + ", :" + JOB_TITLE + ", :" + GENDER + ", :" + DATE_OF_BIRTH + ")";
    public static final String UPDATE_EMPLOYEE_SQL = "UPDATE employee SET " + DEPARTMENT_ID + " = :" + DEPARTMENT_ID + ", " + JOB_TITLE + " = :" + JOB_TITLE + " WHERE " + EMPLOYEE_ID + " = :" + EMPLOYEE_ID;
    public static final String DELETE_EMPLOYEE_SQL = "DELETE FROM employee WHERE " + EMPLOYEE_ID + " = :" + EMPLOYEE_ID;

    private SqlRequests(){}
}
