package com.toporkov.automobileapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vehicle_model")
public class VehicleModel {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "brand_name", nullable = false)
    private String brandName;

    @Column(name = "model_name", nullable = false)
    private String modelName;

    @Column(name = "body_type", nullable = false)
    private String bodyType;

    @Column(name = "drive", nullable = false)
    private String drive;

    @Column(name = "engine_type", nullable = false)
    private String engineType;

    @Column(name = "engine_power", nullable = false)
    private int enginePower;

    @Column(name = "engine_volume", nullable = false)
    private double engineVolume;

    @Column(name = "max_speed", nullable = false)
    private int maxSpeed;

    @Column(name = "transmission", nullable = false)
    private String transmission;

    @Column(name = "seats_number", nullable = false)
    private int seatsNumber;

    @Column(name = "doors_number", nullable = false)
    private int doorsNumber;

    @Column(name = "suspension", nullable = false)
    private String suspension;

    @OneToMany(mappedBy = "vehicleModel")
    private List<Vehicle> vehicles = new ArrayList<>();

    public VehicleModel() {
    }

    public VehicleModel(final String brandName,
                        final String modelName,
                        final String bodyType,
                        final String drive,
                        final String engineType,
                        final int enginePower,
                        final double engineVolume,
                        final int maxSpeed,
                        final String transmission,
                        final int seatsNumber,
                        final int doorsNumber,
                        final String suspension) {
        this.brandName = brandName;
        this.modelName = modelName;
        this.bodyType = bodyType;
        this.drive = drive;
        this.engineType = engineType;
        this.enginePower = enginePower;
        this.engineVolume = engineVolume;
        this.maxSpeed = maxSpeed;
        this.transmission = transmission;
        this.seatsNumber = seatsNumber;
        this.doorsNumber = doorsNumber;
        this.suspension = suspension;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public int getSeatsNumber() {
        return seatsNumber;
    }

    public void setSeatsNumber(int seatsNumber) {
        this.seatsNumber = seatsNumber;
    }

    public int getDoorsNumber() {
        return doorsNumber;
    }

    public void setDoorsNumber(int doorsNumber) {
        this.doorsNumber = doorsNumber;
    }

    public String getSuspension() {
        return suspension;
    }

    public void setSuspension(String suspension) {
        this.suspension = suspension;
    }

    public void addVehicle(Vehicle vehicle) {
        vehicle.setModel(this);
        vehicles.add(vehicle);
    }

    public int getId() {
        return id;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public String getDrive() {
        return drive;
    }

    public void setDrive(String transmission) {
        this.drive = transmission;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public int getEnginePower() {
        return enginePower;
    }

    public void setEnginePower(int enginePower) {
        this.enginePower = enginePower;
    }

    public double getEngineVolume() {
        return engineVolume;
    }

    public void setEngineVolume(double engineVolume) {
        this.engineVolume = engineVolume;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
}
