package com.toporkov.automobileapp.web.mapper.converter;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.toporkov.automobileapp.model.Enterprise;
import com.toporkov.automobileapp.service.EnterpriseService;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class EnterpriseIdListToEnterpriseSetConverter extends AbstractConverter<List<UUID>, Set<Enterprise>> {

    private final EnterpriseService enterpriseService;

    public EnterpriseIdListToEnterpriseSetConverter(EnterpriseService enterpriseService) {
        this.enterpriseService = enterpriseService;
    }

    @Override
    protected Set<Enterprise> convert(List<UUID> enterpriseIds) {
        Assert.notNull(enterpriseIds, "enterpriseIds argument is null");
        return enterpriseIds.stream()
            .map(enterpriseService::getById)
            .collect(Collectors.toSet());
    }
}
