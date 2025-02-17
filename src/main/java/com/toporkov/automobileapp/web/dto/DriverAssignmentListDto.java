package com.toporkov.automobileapp.web.dto;

import java.util.List;

public class DriverAssignmentListDto {

    private List<DriverAssignmentDto> driverAssignments;

    public DriverAssignmentListDto(List<DriverAssignmentDto> driverAssignments) {
        this.driverAssignments = driverAssignments;
    }

    public List<DriverAssignmentDto> getDriverAssignments() {
        return driverAssignments;
    }

    public void setDriverAssignments(List<DriverAssignmentDto> driverAssignments) {
        this.driverAssignments = driverAssignments;
    }
}
