package com.toporkov.automobileapp.web.mapper;

import com.toporkov.automobileapp.model.VehicleModel;
import com.toporkov.automobileapp.web.dto.VehicleModelDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class VehicleModelMapper {

    private final ModelMapper modelMapper;

    public VehicleModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public VehicleModel mapDtoToEntity(VehicleModelDTO vehicleModelDto) {
        return modelMapper.map(vehicleModelDto, VehicleModel.class);
    }

    public VehicleModelDTO mapEntityToDto(VehicleModel vehicleModel) {
        return modelMapper.map(vehicleModel, VehicleModelDTO.class);
    }
}
