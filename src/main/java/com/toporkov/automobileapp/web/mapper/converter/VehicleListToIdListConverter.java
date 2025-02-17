package com.toporkov.automobileapp.web.mapper.converter;

import com.toporkov.automobileapp.model.Vehicle;
import org.modelmapper.AbstractConverter;
import org.springframework.util.Assert;

import java.util.List;

public class VehicleListToIdListConverter extends AbstractConverter<List<Vehicle>, List<Integer>> {

    @Override
    protected List<Integer> convert(List<Vehicle> vehicles) {
        Assert.notNull(vehicles, "vehicles argument is null");
        return vehicles.stream().map(Vehicle::getId).toList();
    }
}
