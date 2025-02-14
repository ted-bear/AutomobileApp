package com.toporkov.automobileapp.web.mapper;

import com.toporkov.automobileapp.model.VehicleModel;
import com.toporkov.automobileapp.web.dto.VehicleModelDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class VehicleModelMapper {

    private final ModelMapper modelMapper;

    public VehicleModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public VehicleModel mapDtoToEntity(VehicleModelDto vehicleModelDto) {
        return modelMapper.map(vehicleModelDto, VehicleModel.class);
    }

    public VehicleModelDto mapEntityToDto(VehicleModel vehicleModel) {
        return modelMapper.map(vehicleModel, VehicleModelDto.class);
    }
}
