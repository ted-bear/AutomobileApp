package com.toporkov.automobileapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "vehicle_model")
public class VehicleModel {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Имя бренда не должно быть пустым")
    @Column(name = "brand_name", nullable = false)
    private String brandName;

    @NotBlank(message = "Имя модели не должно быть пустым")
    @Column(name = "model_name", nullable = false)
    private String modelName;

    @NotBlank(message = "Тип кузова не должен быть пустым")
    @Column(name = "body_type", nullable = false)
    private String bodyType;

    @NotBlank(message = "Привод не должен быть пустым")
    @Column(name = "drive", nullable = false)
    private String drive;

    @NotBlank(message = "Тип двигателя не должен быть пустым")
    @Column(name = "engine_type", nullable = false)
    private String engineType;

    @Min(value = 10, message = "Мощность двигателя не может быть меньше 10 л.с.")
    @Column(name = "engine_power", nullable = false)
    private int enginePower;

    @Min(value = 1, message = "Мощность двигателя не может быть меньше 1 л.")
    @Column(name = "engine_volume", nullable = false)
    private double engineVolume;

    @Min(value = 0, message = "Максимальная не может быть меньше 1 км/ч")
    @Column(name = "max_speed", nullable = false)
    private int maxSpeed;

    @NotBlank(message = "Трансмиссия не должна быть пустой")
    @Column(name = "transmission", nullable = false)
    private String transmission;

    @Min(value = 1, message = "Количество сидений не может быть меньше одного")
    @Column(name = "seats_number", nullable = false)
    private int seatsNumber;

    @Min(value = 1, message = "Количество дверей не может быть меньше одного")
    @Column(name = "doors_number", nullable = false)
    private int doorsNumber;

    @NotBlank(message = "Подвеска не должна быть пустой")
    @Column(name = "suspension", nullable = false)
    private String suspension;

    @Column(name = "is_active")
    private boolean isActive;

    @OneToMany(mappedBy = "vehicleModel")
    private List<Vehicle> vehicles = new ArrayList<>();

    public VehicleModel() {
    }

    public VehicleModel(
        final String brandName,
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
        final String suspension
    ) {
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
        vehicle.setVehicleModel(this);
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
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
        VehicleModel that = (VehicleModel) o;
        return id == that.id &&
            enginePower == that.enginePower &&
            Double.compare(engineVolume, that.engineVolume) == 0 &&
            maxSpeed == that.maxSpeed &&
            seatsNumber == that.seatsNumber &&
            doorsNumber == that.doorsNumber &&
            Objects.equals(brandName, that.brandName) &&
            Objects.equals(modelName, that.modelName) &&
            Objects.equals(bodyType, that.bodyType) &&
            Objects.equals(drive, that.drive) &&
            Objects.equals(engineType, that.engineType) &&
            Objects.equals(transmission, that.transmission) &&
            Objects.equals(suspension, that.suspension);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brandName, modelName, bodyType,
            drive, engineType, enginePower, engineVolume,
            maxSpeed, transmission, seatsNumber, doorsNumber, suspension);
    }
}
