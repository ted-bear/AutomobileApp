package com.toporkov.automobileapp.web.mapper.converter;

import java.util.List;

import com.toporkov.automobileapp.model.Vehicle;
import org.modelmapper.AbstractConverter;
import org.springframework.util.Assert;

public class VehicleListToIdListConverter extends AbstractConverter<List<Vehicle>, List<String>> {

    @Override
    protected List<String> convert(List<Vehicle> vehicles) {
        Assert.notNull(vehicles, "vehicles argument is null");
        return vehicles.stream().map(v -> v.getId().toString()).toList();
    }
}
