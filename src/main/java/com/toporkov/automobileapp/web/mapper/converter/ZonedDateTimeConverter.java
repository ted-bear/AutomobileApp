package com.toporkov.automobileapp.web.mapper.converter;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.opencsv.bean.AbstractBeanField;

public class ZonedDateTimeConverter extends AbstractBeanField<ZonedDateTime> {

    @Override
    protected ZonedDateTime convert(String value) {
        return ZonedDateTime.parse(value, DateTimeFormatter.ISO_ZONED_DATE_TIME);
    }
}
