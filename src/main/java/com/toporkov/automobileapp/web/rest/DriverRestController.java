package com.toporkov.automobileapp.web.rest;

import com.toporkov.automobileapp.model.Manager;
import com.toporkov.automobileapp.service.DriverService;
import com.toporkov.automobileapp.util.SecurityUtil;
import com.toporkov.automobileapp.web.dto.DriverDTO;
import com.toporkov.automobileapp.web.dto.DriverListDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/drivers")
public class DriverRestController {

    private final DriverService driverService;

    public DriverRestController(final DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping
    public DriverListDTO getAllDrivers() {
        final Manager manager = SecurityUtil.getCurrentManager();
        final List<DriverDTO> driverDTOList = driverService.findAllByManager(manager);
        return new DriverListDTO(driverDTOList);
    }
}
