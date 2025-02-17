package com.toporkov.automobileapp.web.mapper.converter;

import com.toporkov.automobileapp.model.Driver;
import org.modelmapper.AbstractConverter;
import org.springframework.util.Assert;

import java.util.List;

public class DriverListToIdListConverter extends AbstractConverter<List<Driver>, List<Integer>> {

    @Override
    protected List<Integer> convert(List<Driver> drivers) {
        Assert.notNull(drivers, "drivers argument is null");
        return drivers.stream().map(Driver::getId).toList();
    }
}
