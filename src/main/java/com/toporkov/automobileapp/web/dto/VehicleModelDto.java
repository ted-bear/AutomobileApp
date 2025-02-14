package com.toporkov.automobileapp.web.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class VehicleModelDto {

    private Integer id;

    @NotBlank(message = "Имя бренда не должно быть пустым")
    private String brandName;

    @NotBlank(message = "Имя модели не должно быть пустым")
    private String modelName;

    @NotBlank(message = "Тип кузова не должен быть пустым")
    private String bodyType;

    @NotBlank(message = "Привод не должен быть пустым")
    private String drive;

    @NotBlank(message = "Тип двигателя не должен быть пустым")
    private String engineType;

    @Min(value = 10, message = "Мощность двигателя не может быть меньше 10 л.с.")
    private int enginePower;

    @Min(value = 1, message = "Мощность двигателя не может быть меньше 1 л.")
    private double engineVolume;

    @Min(value = 0, message = "Максимальная не может быть меньше 1 км/ч")
    private int maxSpeed;

    @NotBlank(message = "Трансмиссия не должна быть пустой")
    private String transmission;

    @Min(value = 1, message = "Количество сидений не может быть меньше одного")
    private int seatsNumber;

    @Min(value = 1, message = "Количество дверей не может быть меньше одного")
    private int doorsNumber;

    @NotBlank(message = "Подвеска не должна быть пустой")
    private String suspension;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
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

    public void setDrive(String drive) {
        this.drive = drive;
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
}
