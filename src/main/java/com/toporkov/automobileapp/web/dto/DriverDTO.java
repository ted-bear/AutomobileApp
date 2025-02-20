package com.toporkov.automobileapp.web.dto;

import java.math.BigDecimal;

public class DriverDTO {

    private Integer id;

    private String firstname;

    private String lastname;

    private BigDecimal salary;

    private Integer drivingExperience;

    private Integer activeVehicleId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getActiveVehicleId() {
        return activeVehicleId;
    }

    public void setActiveVehicleId(Integer activeVehicleId) {
        this.activeVehicleId = activeVehicleId;
    }
}
