package com.toporkov.automobileapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "vehicle")
public class Vehicle {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "number", nullable = false)
    private String number;

    @Column(name = "year", nullable = false)
    private int year;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "body_type", nullable = false)
    private String bodyType;

    @Column(name = "mileage", nullable = false)
    private int mileage;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "condition", nullable = false)
    private String condition;

    @Column(name = "fuel_type", nullable = false)
    private String fuelType;

    @Column(name = "transmission", nullable = false)
    private String transmission;

    public Vehicle() {
    }

    public Vehicle(final String number,
                   final int year,
                   final String color,
                   final String bodyType,
                   final int mileage,
                   final double price,
                   final String condition,
                   final String fuelType,
                   final String transmission) {
        this.number = number;
        this.year = year;
        this.color = color;
        this.bodyType = bodyType;
        this.mileage = mileage;
        this.price = price;
        this.condition = condition;
        this.fuelType = fuelType;
        this.transmission = transmission;
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

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
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

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }
}
