package com.toporkov.automobileapp.web.mapper.converter;

import java.util.UUID;

import com.toporkov.automobileapp.model.Enterprise;
import org.modelmapper.AbstractConverter;
import org.springframework.util.Assert;

public class EnterpriseToEnterpriseIdConverter extends AbstractConverter<Enterprise, UUID> {

    @Override
    protected UUID convert(Enterprise enterprise) {
        Assert.notNull(enterprise, "enterprise argument is null");

        return enterprise.getId();
    }
}
