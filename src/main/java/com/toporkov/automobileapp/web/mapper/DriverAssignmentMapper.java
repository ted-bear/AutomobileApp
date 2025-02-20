package com.toporkov.automobileapp.web.mapper;

import com.toporkov.automobileapp.model.DriverAssignment;
import com.toporkov.automobileapp.web.dto.DriverAssignmentDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class DriverAssignmentMapper {

    private final ModelMapper modelMapper;

    public DriverAssignmentMapper(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public DriverAssignment mapDtoToEntity(DriverAssignmentDTO dto) {
        return modelMapper.map(dto, DriverAssignment.class);
    }

    public DriverAssignmentDTO mapEntityToDto(DriverAssignment entity) {
        return modelMapper.map(entity, DriverAssignmentDTO.class);
    }
}
