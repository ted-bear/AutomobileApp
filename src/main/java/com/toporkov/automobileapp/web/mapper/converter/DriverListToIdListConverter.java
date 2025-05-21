package com.toporkov.automobileapp.web.mapper.converter;

import java.util.List;

import com.toporkov.automobileapp.model.Driver;
import org.modelmapper.AbstractConverter;
import org.springframework.util.Assert;

public class DriverListToIdListConverter extends AbstractConverter<List<Driver>, List<String>> {

    @Override
    protected List<String> convert(List<Driver> drivers) {
        Assert.notNull(drivers, "drivers argument is null");
        return drivers.stream().map(d -> d.getId().toString()).toList();
    }
}
