package com.toporkov.automobileapp.web.rest;

import com.toporkov.automobileapp.model.Manager;
import com.toporkov.automobileapp.service.DriverService;
import com.toporkov.automobileapp.util.SecurityUtil;
import com.toporkov.automobileapp.web.dto.DriverDTO;
import com.toporkov.automobileapp.web.dto.DriverListDTO;
import com.toporkov.automobileapp.web.mapper.DriverMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public DriverListDTO getAllDrivers() {
        final Manager manager = SecurityUtil.getCurrentManager();
        final List<DriverDTO> driverDTOList = driverService.findAllByManager(manager)
                .stream()
                .map(driverMapper::mapEntityToDto)
                .toList();
        return new DriverListDTO(driverDTOList);
    }
}
