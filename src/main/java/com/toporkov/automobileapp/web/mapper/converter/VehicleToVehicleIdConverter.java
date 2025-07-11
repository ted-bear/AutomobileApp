package com.toporkov.automobileapp.web.mapper.converter;

import java.util.UUID;

import com.toporkov.automobileapp.model.Vehicle;
import org.modelmapper.AbstractConverter;
import org.springframework.util.Assert;

public class VehicleToVehicleIdConverter extends AbstractConverter<Vehicle, UUID> {

    @Override
    protected UUID convert(Vehicle vehicle) {
        Assert.notNull(vehicle, "vehicle argument is null");

        return vehicle.getId();
    }
}
