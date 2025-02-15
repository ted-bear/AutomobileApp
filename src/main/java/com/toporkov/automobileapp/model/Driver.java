package com.toporkov.automobileapp.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Driver {

    private Long id;
    private String firstname;
    private String lastname;
    private BigDecimal salary;
    private Integer drivingExperience;


    public Driver() {
    }

    public Driver(final Long id,
                  final String firstname,
                  final String lastname,
                  final BigDecimal salary,
                  final Integer drivingExperience) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.salary = salary;
        this.drivingExperience = drivingExperience;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Integer getDrivingExperience() {
        return drivingExperience;
    }

    public void setDrivingExperience(Integer drivingExperience) {
        this.drivingExperience = drivingExperience;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Driver driver = (Driver) o;
        return Objects.equals(id, driver.id) && Objects.equals(firstname, driver.firstname) && Objects.equals(lastname, driver.lastname) && Objects.equals(salary, driver.salary) && Objects.equals(drivingExperience, driver.drivingExperience);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname, lastname, salary, drivingExperience);
    }
}
