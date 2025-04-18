package com.toporkov.automobileapp.web.mapper.converter;

import java.time.Instant;

import com.opencsv.bean.AbstractBeanField;

public class InstantConverter extends AbstractBeanField<Instant> {
    @Override
    protected Instant convert(String value) {
        return Instant.parse(value);
    }
}
