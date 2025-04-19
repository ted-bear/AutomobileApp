package com.toporkov.automobileapp.web.dto.domain.enterprise;

import com.opencsv.bean.CsvBindByName;
import com.toporkov.automobileapp.web.dto.validation.OnCreate;
import com.toporkov.automobileapp.web.dto.validation.OnUpdate;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class EnterpriseCsvDTO {

    @CsvBindByName(column = "id", required = true)
    private String id;

    @NotNull(message = "name must not be null",
        groups = {OnCreate.class, OnUpdate.class})
    @Length(min = 1,
        max = 255,
        message = "country length must be smaller than 255 symbols",
        groups = {OnCreate.class, OnUpdate.class})
    @CsvBindByName(column = "name", required = true)
    private String name;

    @NotNull(message = "country must not be null",
        groups = {OnCreate.class, OnUpdate.class})
    @Length(min = 1,
        max = 255,
        message = "country length must be smaller than 255 symbols",
        groups = {OnCreate.class, OnUpdate.class})
    @CsvBindByName(column = "country", required = true)
    private String country;

    @NotNull(message = "city must not be null",
        groups = {OnCreate.class, OnUpdate.class})
    @Length(min = 1,
        max = 255,
        message = "city length must be smaller than 255 symbols",
        groups = {OnCreate.class, OnUpdate.class})
    @CsvBindByName(column = "city", required = true)
    private String city;

    @NotNull(message = "employeesNumber must not be null",
        groups = {OnCreate.class, OnUpdate.class})
    @Min(value = 1,
        message = "employeesNumber length must be a positive number",
        groups = {OnCreate.class, OnUpdate.class})
    @CsvBindByName(column = "employeesNumber", required = true)
    private Integer employeesNumber;

    @NotNull(message = "timezone must not be null")
    @CsvBindByName(column = "timezone", required = true)
    private String timezone;

}
