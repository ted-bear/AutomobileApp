package com.toporkov.automobileapp.web.dto.domain.trip;

import java.time.Instant;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import com.toporkov.automobileapp.web.mapper.converter.InstantConverter;
import lombok.Data;

@Data
public class TripCsvDTO {

    @CsvBindByName(column = "startedAt", required = true)
    @CsvCustomBindByName(column = "startedAt", converter = InstantConverter.class)
    private Instant startedAt;

    @CsvBindByName(column = "endedAt", required = true)
    @CsvCustomBindByName(column = "endedAt", converter = InstantConverter.class)
    private Instant endedAt;

    @CsvBindByName(column = "vehicleId", required = true)
    private Integer vehicleId;
}
