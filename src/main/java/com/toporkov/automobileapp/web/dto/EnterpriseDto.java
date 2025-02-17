package com.toporkov.automobileapp.web.dto;

import java.util.List;

public class EnterpriseDto {

    private Integer id;

    private String name;

    private String country;

    private String city;

    private Integer employeesNumber;

    private List<Integer> vehicles;

    private List<Integer> drivers;

    private List<DriverAssignmentDto> driverAssignments;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getEmployeesNumber() {
        return employeesNumber;
    }

    public void setEmployeesNumber(Integer employeesNumber) {
        this.employeesNumber = employeesNumber;
    }

    public List<Integer> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Integer> vehicles) {
        this.vehicles = vehicles;
    }

    public List<Integer> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Integer> drivers) {
        this.drivers = drivers;
    }

    public List<DriverAssignmentDto> getDriverAssignments() {
        return driverAssignments;
    }

    public void setDriverAssignments(List<DriverAssignmentDto> driverAssignments) {
        this.driverAssignments = driverAssignments;
    }
}
