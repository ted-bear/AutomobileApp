package com.toporkov.automobileapp.web.mapper.converter;

import com.toporkov.automobileapp.model.Enterprise;
import com.toporkov.automobileapp.service.EnterpriseService;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class EnterpriseIdListToEnterpriseSetConverter extends AbstractConverter<List<Integer>, Set<Enterprise>> {

    private final EnterpriseService enterpriseService;

    public EnterpriseIdListToEnterpriseSetConverter(EnterpriseService enterpriseService) {
        this.enterpriseService = enterpriseService;
    }

    @Override
    protected Set<Enterprise> convert(List<Integer> enterpriseIds) {
        Assert.notNull(enterpriseIds, "enterpriseIds argument is null");
        return enterpriseIds.stream()
                .map(enterpriseService::getById)
                .collect(Collectors.toSet());
    }
}
