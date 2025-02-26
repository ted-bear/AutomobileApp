package com.toporkov.automobileapp.web.dto.domain.driver;

import java.util.List;

public class DriverListDTO {

    private List<DriverDTO> drivers;

    public DriverListDTO() {
    }

    public DriverListDTO(List<DriverDTO> drivers) {
        this.drivers = drivers;
    }

    public List<DriverDTO> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<DriverDTO> drivers) {
        this.drivers = drivers;
    }
}
