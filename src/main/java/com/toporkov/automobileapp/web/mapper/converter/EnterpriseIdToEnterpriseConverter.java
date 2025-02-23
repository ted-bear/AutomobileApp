package com.toporkov.automobileapp.web.mapper.converter;

import com.toporkov.automobileapp.model.Enterprise;
import com.toporkov.automobileapp.service.EnterpriseService;
import org.modelmapper.AbstractConverter;

public class EnterpriseIdToEnterpriseConverter extends AbstractConverter<Integer, Enterprise> {

    private final EnterpriseService enterpriseService;

    public EnterpriseIdToEnterpriseConverter(EnterpriseService enterpriseService) {
        this.enterpriseService = enterpriseService;
    }

    @Override
    protected Enterprise convert(Integer id) {
        return enterpriseService.getById(id);
    }
}
