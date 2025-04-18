package com.toporkov.automobileapp.web.dto.domain.vehicle;

import java.time.ZonedDateTime;
import java.util.UUID;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import com.toporkov.automobileapp.web.dto.validation.OnCreate;
import com.toporkov.automobileapp.web.dto.validation.OnUpdate;
import com.toporkov.automobileapp.web.mapper.converter.ZonedDateTimeConverter;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class VehicleCsvDTO {

    @CsvBindByName(column = "id", required = true)
    private UUID id;

    @NotNull(message = "number must not be null",
        groups = {OnCreate.class, OnUpdate.class})
    @Length(min = 1,
        max = 255,
        message = "number length must be smaller than 255 symbols",
        groups = {OnCreate.class, OnUpdate.class})
    @Pattern(regexp = "[А-Я][0-9]{3}[А-Я]{2}")
    @CsvBindByName(column = "number", required = true)
    private String number;

    @NotNull(message = "year must not be null",
        groups = {OnCreate.class, OnUpdate.class})
    @Min(value = 1900,
        message = "year length must be more then 1900",
        groups = {OnCreate.class, OnUpdate.class})
    @CsvBindByName(column = "year", required = true)
    private Integer year;

    @NotNull(message = "color must not be null",
        groups = {OnCreate.class, OnUpdate.class})
    @Length(min = 1,
        max = 255,
        message = "color length must be smaller than 255 symbols",
        groups = {OnCreate.class, OnUpdate.class})
    @CsvBindByName(column = "color", required = true)
    private String color;

    @NotNull(message = "mileage must not be null",
        groups = {OnCreate.class, OnUpdate.class})
    @Min(value = 0,
        message = "mileage length must be a positive number",
        groups = {OnCreate.class, OnUpdate.class})
    @CsvBindByName(column = "mileage", required = true)
    private Integer mileage;

    @NotNull(message = "price must not be null",
        groups = {OnCreate.class, OnUpdate.class})
    @Min(value = 0,
        message = "price length must be a positive number",
        groups = {OnCreate.class, OnUpdate.class})
    @CsvBindByName(column = "price", required = true)
    private Double price;

    @NotNull(message = "condition must not be null",
        groups = {OnCreate.class, OnUpdate.class})
    @CsvBindByName(column = "condition", required = true)
    private String condition;

    @NotNull(message = "purchaseDate must not be null",
        groups = {OnCreate.class, OnUpdate.class})
    @CsvBindByName(column = "purchaseDate", required = true)
    @CsvCustomBindByName(column = "purchaseDate", converter = ZonedDateTimeConverter.class)
    private ZonedDateTime purchaseDate;

    @NotNull(message = "vehicleModelId must not be null",
        groups = {OnCreate.class, OnUpdate.class})
    @Min(value = 0,
        message = "vehicleModelId length must be a positive number",
        groups = {OnCreate.class, OnUpdate.class})
    @CsvBindByName(column = "vehicleModelId", required = true)
    private Integer vehicleModelId;

    @NotNull(message = "enterpriseId must not be null",
        groups = {OnCreate.class, OnUpdate.class})
    @Min(value = 0,
        message = "enterpriseId length must be a positive number",
        groups = {OnCreate.class, OnUpdate.class})
    @CsvBindByName(column = "enterpriseId", required = true)
    private Integer enterpriseId;
}
