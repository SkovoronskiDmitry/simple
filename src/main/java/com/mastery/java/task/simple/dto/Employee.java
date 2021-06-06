package com.mastery.java.task.simple.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.Objects;

@ApiModel(value = "Employee Model", subTypes = {Employee.class}, description = "Sample model for the documentation")
public class Employee {

    private Long employeeId;
    @NotEmpty(message = "Please provide a firstName")
    private String firstName;
    @NotEmpty(message = "Please provide a LastName")
    private String lastName;
    @NotNull(message = "Please provide a gender")
    private Gender gender;
    @NotNull(message = "Please provide a departmentId")
    private Long departmentId;
    @NotEmpty(message = "Please provide a jobTitle")
    private String jobTitle;
    @Past(message = "Only past time")
    private LocalDate dateOfBirth;

    public Employee() {
    }

    public Employee(
            final String firstName,
            final String lastName,
            final String gender,
            final Long departmentId,
            final String jobTitle,
            final LocalDate dateOfBirth
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = Gender.valueOf(gender.toUpperCase());
        this.departmentId = departmentId;
        this.jobTitle = jobTitle;
        this.dateOfBirth = dateOfBirth;
    }

    @ApiModelProperty(value = "Unique number id", example = "1", required = true, position = 1)
    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(final Long employeeId) {
        this.employeeId = employeeId;
    }

    @ApiModelProperty(value = "Employee firstname", example = "John", required = true, position = 2)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    @ApiModelProperty(value = "Employee lastname", example = "Smith", required = true, position = 3)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    @ApiModelProperty(value = "Gender identity", allowableValues = "MALE, FEMALE", required = true, position = 5)
    public String getGender() {
        return gender.toString();
    }

    public void setGender(final String gender) {
        this.gender = Gender.valueOf(gender.toUpperCase());
    }

    @ApiModelProperty(value = "Employee's department number", example = "9", required = true, position = 6)
    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(final Long departmentId) {
        this.departmentId = departmentId;
    }

    @ApiModelProperty(value = "Employee's job title", example = "boss", required = true, position = 7)
    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(final String jobTitle) {
        this.jobTitle = jobTitle;
    }

    @ApiModelProperty(value = "Employee's birthday", example = "2007-12-25", required = true, position = 4)
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(final LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Employee employee = (Employee) o;
        return employeeId.equals(employee.employeeId)
                && firstName.equals(employee.firstName)
                && lastName.equals(employee.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, departmentId, firstName, lastName);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender=" + gender +
                ", departmentId=" + departmentId +
                ", jobTitle='" + jobTitle + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }
}
