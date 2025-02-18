package com.toporkov.automobileapp.web.rest;

import com.toporkov.automobileapp.model.Driver;
import com.toporkov.automobileapp.service.DriverService;
import com.toporkov.automobileapp.web.dto.DriverDto;
import com.toporkov.automobileapp.web.dto.DriverListDto;
import com.toporkov.automobileapp.web.mapper.DriverMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/drivers")
public class DriverRestController {

    private final DriverService driverService;
    private final DriverMapper driverMapper;

    public DriverRestController(final DriverService driverService,
                                final DriverMapper driverMapper) {
        this.driverService = driverService;
        this.driverMapper = driverMapper;
    }

    @GetMapping
    public DriverListDto getAllDrivers(@RequestParam(value = "enterpriseId", required = false) Integer enterpriseId) {
        final List<Driver> allDrivers = driverService.findAll(enterpriseId);
        final List<DriverDto> driverDtoList = allDrivers.stream()
                .map(driverMapper::mapEntityToDto)
                .toList();
        return new DriverListDto(driverDtoList);
    }
}
