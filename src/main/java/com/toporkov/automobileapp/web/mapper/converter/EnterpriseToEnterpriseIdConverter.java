package com.toporkov.automobileapp.web.mapper.converter;

import com.toporkov.automobileapp.model.Enterprise;
import org.modelmapper.AbstractConverter;
import org.springframework.util.Assert;

public class EnterpriseToEnterpriseIdConverter extends AbstractConverter<Enterprise, String> {

    @Override
    protected String convert(Enterprise enterprise) {
        Assert.notNull(enterprise, "enterprise argument is null");

        return enterprise.getId().toString();
    }
}
