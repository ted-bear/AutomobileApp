package com.toporkov.automobileapp.web.mapper;

import com.toporkov.automobileapp.model.Vehicle;
import com.toporkov.automobileapp.web.dto.domain.vehicle.VehicleDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class VehicleMapper {

    private final ModelMapper modelMapper;

    public VehicleMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Vehicle mapDtoToEntity(VehicleDTO vehicleDto) {
        return modelMapper.map(vehicleDto, Vehicle.class);
    }

    public VehicleDTO mapEntityToDto(Vehicle vehicle) {
        return modelMapper.map(vehicle, VehicleDTO.class);
    }
}
