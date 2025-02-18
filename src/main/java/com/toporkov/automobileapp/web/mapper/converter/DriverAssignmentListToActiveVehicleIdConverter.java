package com.toporkov.automobileapp.web.mapper.converter;

import com.toporkov.automobileapp.model.DriverAssignment;
import org.modelmapper.AbstractConverter;
import org.springframework.util.Assert;

import java.util.List;

public class DriverAssignmentListToActiveVehicleIdConverter
        extends AbstractConverter<List<DriverAssignment>, Integer> {

    @Override
    protected Integer convert(List<DriverAssignment> driverAssignments) {
        Assert.notNull(driverAssignments, "driverAssignments argument is null");

        return driverAssignments.stream()
                .filter(DriverAssignment::getActive)
                .map(driverAssignment -> driverAssignment.getVehicle().getId())
                .findAny()
                .orElse(null);
    }
}
