package com.toporkov.automobileapp.web.dto.domain;

public class DriverAssignmentDTO {

    private Integer driverId;

    private Integer vehicleId;

    private Boolean isActive;

    public DriverAssignmentDTO() {
    }

    public DriverAssignmentDTO(Integer driverId, Integer vehicleId, Boolean isActive) {
        this.driverId = driverId;
        this.vehicleId = vehicleId;
        this.isActive = isActive;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
