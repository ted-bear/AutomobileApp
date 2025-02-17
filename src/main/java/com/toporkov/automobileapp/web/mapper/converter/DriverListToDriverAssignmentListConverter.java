package com.toporkov.automobileapp.web.mapper.converter;

import com.toporkov.automobileapp.model.Driver;
import com.toporkov.automobileapp.web.dto.DriverAssignmentDto;
import org.modelmapper.AbstractConverter;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

public class DriverListToDriverAssignmentListConverter extends AbstractConverter<List<Driver>, List<DriverAssignmentDto>> {

    @Override
    protected List<DriverAssignmentDto> convert(List<Driver> drivers) {
        Assert.notNull(drivers, "drivers argument is null");
        List<DriverAssignmentDto> driverAssignmentDtoList = new ArrayList<>();

        drivers.forEach(driver -> driver.getDriverAssignments()
                .forEach(assignment -> driverAssignmentDtoList.add(
                        new DriverAssignmentDto(
                                assignment.getDriver().getId(),
                                assignment.getVehicle().getId(),
                                assignment.getActive()
                        )
                )));

        return driverAssignmentDtoList;
    }
}
