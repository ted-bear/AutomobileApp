package com.toporkov.automobileapp.web.mapper;

import com.toporkov.automobileapp.model.Manager;
import com.toporkov.automobileapp.web.dto.domain.RegistrationManagerDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ManagerMapper {

    private final ModelMapper modelMapper;

    public ManagerMapper(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Manager mapDtoToEntity(RegistrationManagerDTO dto) {
        return modelMapper.map(dto, Manager.class);
    }

}
