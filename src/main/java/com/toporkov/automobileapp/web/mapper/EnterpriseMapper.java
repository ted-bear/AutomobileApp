package com.toporkov.automobileapp.web.mapper;

import com.toporkov.automobileapp.model.Enterprise;
import com.toporkov.automobileapp.web.dto.domain.enterprise.EnterpriseCsvDTO;
import com.toporkov.automobileapp.web.dto.domain.enterprise.EnterpriseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class EnterpriseMapper {

    private final ModelMapper modelMapper;

    public EnterpriseMapper(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Enterprise mapDtoToEntity(EnterpriseDTO dto) {
        return modelMapper.map(dto, Enterprise.class);
    }

    public Enterprise mapCsvDtoToEntity(EnterpriseCsvDTO dto) {
        return modelMapper.map(dto, Enterprise.class);
    }

    public EnterpriseDTO mapEntityToDto(Enterprise entity) {
        return modelMapper.map(entity, EnterpriseDTO.class);
    }
}
