package com.toporkov.automobileapp.web.dto.domain.enterprise;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.toporkov.automobileapp.web.dto.validation.OnCreate;
import com.toporkov.automobileapp.web.dto.validation.OnUpdate;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class EnterpriseDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String id;

    @NotNull(message = "name must not be null",
        groups = {OnCreate.class, OnUpdate.class})
    @Length(min = 1,
        max = 255,
        message = "country length must be smaller than 255 symbols",
        groups = {OnCreate.class, OnUpdate.class})
    private String name;

    @NotNull(message = "country must not be null",
        groups = {OnCreate.class, OnUpdate.class})
    @Length(min = 1,
        max = 255,
        message = "country length must be smaller than 255 symbols",
        groups = {OnCreate.class, OnUpdate.class})
    private String country;

    @NotNull(message = "city must not be null",
        groups = {OnCreate.class, OnUpdate.class})
    @Length(min = 1,
        max = 255,
        message = "city length must be smaller than 255 symbols",
        groups = {OnCreate.class, OnUpdate.class})
    private String city;

    @NotNull(message = "employeesNumber must not be null",
        groups = {OnCreate.class, OnUpdate.class})
    @Min(value = 1,
        message = "employeesNumber length must be a positive number",
        groups = {OnCreate.class, OnUpdate.class})
    private Integer employeesNumber;

    @NotNull(message = "timezone must not be null")
    private String timezone;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<String> vehicles;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<String> drivers;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public List<String> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<String> vehicles) {
        this.vehicles = vehicles;
    }

    public List<String> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<String> drivers) {
        this.drivers = drivers;
    }
}
