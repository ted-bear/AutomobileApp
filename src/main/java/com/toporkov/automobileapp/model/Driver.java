package com.toporkov.automobileapp.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "driver")
public class Driver {

    @Id
    @Column(columnDefinition = "UUID")
    private UUID id;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "salary")
    private BigDecimal salary;

    @Column(name = "driving_experience")
    private Integer drivingExperience;

    @ManyToOne
    @JoinColumn(name = "enterprise_id",
        referencedColumnName = "id")
    private Enterprise enterprise;

    @OneToMany(mappedBy = "driver")
    private List<DriverAssignment> driverAssignments = new ArrayList<>();

    public Driver() {
    }

    public Driver(
        final String firstname,
        final String lastname,
        final BigDecimal salary,
        final Integer drivingExperience
    ) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.salary = salary;
        this.drivingExperience = drivingExperience;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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

    public Enterprise getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
    }

    public List<DriverAssignment> getDriverAssignments() {
        return driverAssignments;
    }

    public void setDriverAssignments(List<DriverAssignment> driverAssignments) {
        this.driverAssignments = driverAssignments;
    }

    public void addDriverAssignment(DriverAssignment driverAssignment) {
        driverAssignments.add(driverAssignment);
        driverAssignment.setDriver(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Driver driver = (Driver) o;
        return Objects.equals(id, driver.id) && Objects.equals(firstname, driver.firstname) && Objects.equals(lastname,
            driver.lastname) && Objects.equals(salary, driver.salary) && Objects.equals(drivingExperience,
            driver.drivingExperience);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname, lastname, salary, drivingExperience);
    }
}
