package com.toporkov.automobileapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.Objects;

@Entity
@Table(name = "vehicle")
public class Vehicle {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Номер автомобиля не должен быть пустым")
    @Pattern(regexp = "[а-я][0-9]{3}[а-я]{2}", message = "Номер автомобиля должен быть в формате x999xx")
    @Column(name = "number", nullable = false)
    private String number;

    @Min(value = 1900, message = "Год выпуска должен быть больше 1900")
    @Column(name = "year", nullable = false)
    private int year;

    @NotBlank(message = "Цвет не должен быть пустым")
    @Column(name = "color", nullable = false)
    private String color;

    @Min(value = 0, message = "Пробег не может быть меньше 0")
    @Column(name = "mileage", nullable = false)
    private int mileage;

    @Min(value = 0, message = "Цена не может быть меньше 0")
    @Column(name = "price", nullable = false)
    private double price;

    @NotNull(message = "Цена не должна быть пустой")
    @Column(name = "condition", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Condition condition;

    @Column(name = "is_active")
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "vehicle_model_id",
            referencedColumnName = "id")
    private VehicleModel vehicleModel;

    @ManyToOne
    @JoinColumn(name = "enterprise_id", referencedColumnName = "id")
    private Enterprise enterprise;

    public Vehicle() {
    }

    public Vehicle(final String number,
                   final int year,
                   final String color,
                   final int mileage,
                   final double price,
                   final Condition condition) {
        this.number = number;
        this.year = year;
        this.color = color;
        this.mileage = mileage;
        this.price = price;
        this.condition = condition;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
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

    public VehicleModel getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(VehicleModel vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Enterprise getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return id == vehicle.id && year == vehicle.year && mileage == vehicle.mileage && Double.compare(price, vehicle.price) == 0 && Objects.equals(number, vehicle.number) && Objects.equals(color, vehicle.color) && condition == vehicle.condition;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, year, color, mileage, price, condition);
    }
}
