package com.toporkov.automobileapp.web.mapper.converter;

import java.util.List;
import java.util.UUID;

import com.toporkov.automobileapp.model.DriverAssignment;
import org.modelmapper.AbstractConverter;
import org.springframework.util.Assert;

public class DriverAssignmentListToActiveVehicleIdConverter
    extends AbstractConverter<List<DriverAssignment>, UUID> {

    @Override
    protected UUID convert(List<DriverAssignment> driverAssignments) {
        Assert.notNull(driverAssignments, "driverAssignments argument is null");

        return driverAssignments.stream()
            .filter(DriverAssignment::getActive)
            .map(driverAssignment -> driverAssignment.getVehicle().getId())
            .findAny()
            .orElse(null);
    }
}
