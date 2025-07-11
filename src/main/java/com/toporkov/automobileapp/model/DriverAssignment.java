package com.toporkov.automobileapp.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "driver_assignment")
public class DriverAssignment {

    @Id
    @Column(columnDefinition = "UUID")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "driver_id",
        referencedColumnName = "id")
    private Driver driver;

    @ManyToOne
    @JoinColumn(name = "vehicle_id",
        referencedColumnName = "id")
    private Vehicle vehicle;

    @Column(name = "is_active")
    private Boolean isActive;

    public DriverAssignment() {
    }

    public DriverAssignment(
        final Driver driver,
        final Vehicle vehicle,
        final Boolean isActive
    ) {
        this.driver = driver;
        this.vehicle = vehicle;
        this.isActive = isActive;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DriverAssignment that = (DriverAssignment) o;
        return id == that.id && Objects.equals(driver, that.driver) && Objects.equals(vehicle, that.vehicle)
            && Objects.equals(isActive, that.isActive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, driver, vehicle, isActive);
    }
}
