package com.toporkov.automobileapp.web.mapper.converter;

import java.util.UUID;

import com.toporkov.automobileapp.model.Enterprise;
import com.toporkov.automobileapp.service.EnterpriseService;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

@Component
public class EnterpriseIdToEnterpriseConverter extends AbstractConverter<UUID, Enterprise> {

    private final EnterpriseService enterpriseService;

    public EnterpriseIdToEnterpriseConverter(EnterpriseService enterpriseService) {
        this.enterpriseService = enterpriseService;
    }

    @Override
    protected Enterprise convert(UUID id) {
        return enterpriseService.getById(id);
    }
}
