package com.toporkov.automobileapp.web.dto;

import com.toporkov.automobileapp.model.Condition;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class VehicleDto {

    private Integer id;

    @NotBlank(message = "Номер автомобиля не должен быть пустым")
    @Pattern(regexp = "[а-я][0-9]{3}[а-я]{2}", message = "Номер автомобиля должен быть в формате x999xx")
    private String number;

    @Min(value = 1900, message = "Год выпуска должен быть больше 1900")
    private int year;

    @NotBlank(message = "Цвет не должен быть пустым")
    private String color;

    @Min(value = 0, message = "Пробег не может быть меньше 0")
    private int mileage;

    @Min(value = 0, message = "Цена не может быть меньше 0")
    private double price;

    @NotNull(message = "Цена не должна быть пустой")
    private Condition condition;

    @NotNull(message = "Модель автомобиля не должна быть пустой")
    private Integer vehicleModelId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public Integer getVehicleModelId() {
        return vehicleModelId;
    }

    public void setVehicleModelId(Integer vehicleModelId) {
        this.vehicleModelId = vehicleModelId;
    }
}
