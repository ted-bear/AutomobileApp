package com.toporkov.automobileapp.web.dto;

import java.util.List;

public class DriverListDto {

    private List<DriverDto> drivers;

    public DriverListDto() {
    }

    public DriverListDto(List<DriverDto> drivers) {
        this.drivers = drivers;
    }

    public List<DriverDto> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<DriverDto> drivers) {
        this.drivers = drivers;
    }
}
