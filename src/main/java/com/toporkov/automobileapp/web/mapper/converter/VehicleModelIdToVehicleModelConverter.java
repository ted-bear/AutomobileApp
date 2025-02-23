package com.toporkov.automobileapp.web.mapper.converter;

import com.toporkov.automobileapp.model.VehicleModel;
import com.toporkov.automobileapp.service.VehicleModelService;
import org.modelmapper.AbstractConverter;

public class VehicleModelIdToVehicleModelConverter extends AbstractConverter<Integer, VehicleModel> {

    private final VehicleModelService vehicleModelService;

    public VehicleModelIdToVehicleModelConverter(VehicleModelService vehicleModelService) {
        this.vehicleModelService = vehicleModelService;
    }

    @Override
    protected VehicleModel convert(Integer id) {
        return vehicleModelService.getById(id);
    }
}
