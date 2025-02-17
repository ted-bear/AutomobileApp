package com.toporkov.automobileapp.web.mapper;

import com.toporkov.automobileapp.model.DriverAssignment;
import com.toporkov.automobileapp.web.dto.DriverAssignmentDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class DriverAssignmentMapper {

    private final ModelMapper modelMapper;

    public DriverAssignmentMapper(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public DriverAssignment mapDtoToEntity(DriverAssignmentDto dto) {
        return modelMapper.map(dto, DriverAssignment.class);
    }

    public DriverAssignmentDto mapEntityToDto(DriverAssignment entity) {
        return modelMapper.map(entity, DriverAssignmentDto.class);
    }
}
