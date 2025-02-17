package com.toporkov.automobileapp.web.dto;

import java.util.List;

public class VehicleListDto {

    private List<VehicleDto> vehicles;

    public VehicleListDto() {
    }

    public VehicleListDto(List<VehicleDto> vehicles) {
        this.vehicles = vehicles;
    }

    public List<VehicleDto> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<VehicleDto> vehicles) {
        this.vehicles = vehicles;
    }
}
