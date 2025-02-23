package com.toporkov.automobileapp.web.dto.domain.vehicle;

import java.util.List;

public class VehicleListDTO {

    private List<VehicleDTO> vehicles;

    public VehicleListDTO() {
    }

    public VehicleListDTO(List<VehicleDTO> vehicles) {
        this.vehicles = vehicles;
    }

    public List<VehicleDTO> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<VehicleDTO> vehicles) {
        this.vehicles = vehicles;
    }
}
