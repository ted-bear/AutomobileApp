package com.toporkov.automobileapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "enterprise")
public class Enterprise {

    @Id
    @Column(columnDefinition = "UUID")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "employees_number")
    private Integer employeesNumber;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "timezone")
    private String timezone;

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

    public Enterprise(
        final String name,
        final String country,
        final String city,
        final Integer employeesNumber,
        final String timezone
    ) {
        this.name = name;
        this.country = country;
        this.city = city;
        this.employeesNumber = employeesNumber;
        this.timezone = timezone;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Enterprise that = (Enterprise) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(country, that.country)
            && Objects.equals(city, that.city) && Objects.equals(employeesNumber, that.employeesNumber);
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
