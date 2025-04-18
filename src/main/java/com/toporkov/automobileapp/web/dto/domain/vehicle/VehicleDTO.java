package com.toporkov.automobileapp.web.dto.domain.vehicle;

import java.time.ZonedDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.toporkov.automobileapp.model.Condition;
import com.toporkov.automobileapp.web.dto.validation.OnCreate;
import com.toporkov.automobileapp.web.dto.validation.OnUpdate;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public class VehicleDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @NotNull(message = "number must not be null",
        groups = {OnCreate.class, OnUpdate.class})
    @Length(min = 1,
        max = 255,
        message = "number length must be smaller than 255 symbols",
        groups = {OnCreate.class, OnUpdate.class})
    @Pattern(regexp = "[А-Я][0-9]{3}[А-Я]{2}")
    private String number;

    @NotNull(message = "year must not be null",
        groups = {OnCreate.class, OnUpdate.class})
    @Min(value = 1900,
        message = "year length must be more then 1900",
        groups = {OnCreate.class, OnUpdate.class})
    private Integer year;

    @NotNull(message = "color must not be null",
        groups = {OnCreate.class, OnUpdate.class})
    @Length(min = 1,
        max = 255,
        message = "color length must be smaller than 255 symbols",
        groups = {OnCreate.class, OnUpdate.class})
    private String color;

    @NotNull(message = "mileage must not be null",
        groups = {OnCreate.class, OnUpdate.class})
    @Min(value = 0,
        message = "mileage length must be a positive number",
        groups = {OnCreate.class, OnUpdate.class})
    private Integer mileage;

    @NotNull(message = "price must not be null",
        groups = {OnCreate.class, OnUpdate.class})
    @Min(value = 0,
        message = "price length must be a positive number",
        groups = {OnCreate.class, OnUpdate.class})
    private Double price;

    @NotNull(message = "condition must not be null",
        groups = {OnCreate.class, OnUpdate.class})
    private Condition condition;

    @NotNull(message = "purchaseDate must not be null",
        groups = {OnCreate.class, OnUpdate.class})
    private ZonedDateTime purchaseDate;

    @NotNull(message = "vehicleModelId must not be null",
        groups = {OnCreate.class, OnUpdate.class})
    @Min(value = 0,
        message = "vehicleModelId length must be a positive number",
        groups = {OnCreate.class, OnUpdate.class})
    private Integer vehicleModelId;

    @NotNull(message = "enterpriseId must not be null",
        groups = {OnCreate.class, OnUpdate.class})
    @Min(value = 0,
        message = "enterpriseId length must be a positive number",
        groups = {OnCreate.class, OnUpdate.class})
    private Integer enterpriseId;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public ZonedDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(ZonedDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Integer getVehicleModelId() {
        return vehicleModelId;
    }

    public void setVehicleModelId(Integer vehicleModelId) {
        this.vehicleModelId = vehicleModelId;
    }

    public Integer getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Integer enterpriseId) {
        this.enterpriseId = enterpriseId;
    }
}
