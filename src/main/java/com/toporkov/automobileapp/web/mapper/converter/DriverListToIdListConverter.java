package com.toporkov.automobileapp.web.mapper.converter;

import java.util.List;
import java.util.UUID;

import com.toporkov.automobileapp.model.Driver;
import org.modelmapper.AbstractConverter;
import org.springframework.util.Assert;

public class DriverListToIdListConverter extends AbstractConverter<List<Driver>, List<UUID>> {

    @Override
    protected List<UUID> convert(List<Driver> drivers) {
        Assert.notNull(drivers, "drivers argument is null");
        return drivers.stream().map(Driver::getId).toList();
    }
}
