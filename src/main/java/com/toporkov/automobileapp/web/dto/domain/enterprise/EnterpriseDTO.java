package com.toporkov.automobileapp.web.dto.domain.enterprise;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.toporkov.automobileapp.web.dto.validation.OnCreate;
import com.toporkov.automobileapp.web.dto.validation.OnUpdate;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public class EnterpriseDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    @NotNull(message = "name must not be null")
    @Length(min = 1,
            max = 255,
            message = "country length must be smaller than 255 symbols",
            groups = {OnCreate.class, OnUpdate.class})
    private String name;

    @NotNull(message = "country must not be null")
    @Length(min = 1,
            max = 255,
            message = "country length must be smaller than 255 symbols",
            groups = {OnCreate.class, OnUpdate.class})
    private String country;

    @NotNull(message = "city must not be null")
    @Length(min = 1,
            max = 255,
            message = "city length must be smaller than 255 symbols",
            groups = {OnCreate.class, OnUpdate.class})
    private String city;

    @NotNull(message = "employeesNumber must not be null")
    @Min(value = 1,
            message = "employeesNumber length must be a positive number",
            groups = {OnCreate.class, OnUpdate.class})
    private Integer employeesNumber;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Integer> vehicles;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Integer> drivers;

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
}
