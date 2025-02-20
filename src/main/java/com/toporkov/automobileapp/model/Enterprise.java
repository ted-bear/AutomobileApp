package com.toporkov.automobileapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "enterprise")
public class Enterprise {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "employees_number")
    private Integer employeesNumber;

    @OneToMany(mappedBy = "enterprise")
    private List<Vehicle> vehicles = new ArrayList<>();

    @OneToMany(mappedBy = "enterprise")
    private List<Driver> drivers = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "manager_enterprise",
            joinColumns = @JoinColumn(name = "enterprise_id"),
            inverseJoinColumns = @JoinColumn(name = "manager_id")
    )
    private Set<Manager> managers;

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

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

    public void addVehicle(Vehicle vehicle) {
        vehicle.setEnterprise(this);
        vehicles.add(vehicle);
    }

    public void removeVehicle(Vehicle vehicle) {
        vehicles.remove(vehicle);
        vehicle.setEnterprise(null);
    }

    public void addDriver(Driver driver) {
        driver.setEnterprise(this);
        drivers.add(driver);
    }

    public void removeDriver(Driver driver) {
        drivers.remove(driver);
        driver.setEnterprise(null);
    }

    public Set<Manager> getManagers() {
        return managers;
    }

    public void setManagers(Set<Manager> managers) {
        this.managers = managers;
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

    public void addManager(Manager manager) {
        managers.add(manager);
        manager.getEnterprises().add(this);
    }
}
