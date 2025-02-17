package com.toporkov.automobileapp.web.mapper;

import com.toporkov.automobileapp.model.Driver;
import com.toporkov.automobileapp.web.dto.DriverDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class DriverMapper {

    private final ModelMapper modelMapper;

    public DriverMapper(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Driver mapDtoToEntity(DriverDto dto) {
        return modelMapper.map(dto, Driver.class);
    }

    public DriverDto mapEntityToDto(Driver entity) {
        return modelMapper.map(entity, DriverDto.class);
    }
}
