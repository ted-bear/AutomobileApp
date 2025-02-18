package com.toporkov.automobileapp.web.mapper.converter;

import com.toporkov.automobileapp.model.VehicleModel;
import org.modelmapper.AbstractConverter;
import org.springframework.util.Assert;

public class VehicleModelToVehicleModelIdConverter extends AbstractConverter<VehicleModel, Integer> {

    @Override
    protected Integer convert(VehicleModel vehicleModel) {
        Assert.notNull(vehicleModel, "vehicleModel argument is null");

        return vehicleModel.getId();
    }
}
