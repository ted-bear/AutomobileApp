package com.toporkov.automobileapp.web.mapper;

import com.toporkov.automobileapp.model.VehicleCoordinate;
import com.toporkov.automobileapp.web.dto.domain.VehicleCoordinateDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class VehicleCoordinateMapper {

    private final ModelMapper modelMapper;

    public VehicleCoordinateMapper(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public VehicleCoordinateDTO mapEntityToDTO(VehicleCoordinate entity) {
        return modelMapper.map(entity, VehicleCoordinateDTO.class);
    }
}
