package com.toporkov.automobileapp.model;

import java.util.Objects;

public class Enterprise {

    private Long id;

    private String name;
    private String country;
    private String city;
    private Integer employeesNumber;

    public Enterprise() {
    }

    public Enterprise(final String name,
                      final String country,
                      final String city,
                      final Integer employeesNumber) {
        this.name = name;
        this.country = country;
        this.city = city;
        this.employeesNumber = employeesNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Enterprise that = (Enterprise) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(country, that.country) && Objects.equals(city, that.city) && Objects.equals(employeesNumber, that.employeesNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, country, city, employeesNumber);
    }
}
