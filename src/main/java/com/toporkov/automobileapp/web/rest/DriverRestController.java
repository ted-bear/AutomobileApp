package com.toporkov.automobileapp.web.rest;

import com.toporkov.automobileapp.service.DriverService;
import com.toporkov.automobileapp.web.dto.domain.driver.DriverDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/drivers")
public class DriverRestController {

    private final DriverService driverService;

    public DriverRestController(final DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping
    public Page<DriverDTO> getAllDrivers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending
    ) {
        final Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        final PageRequest pageRequest = PageRequest.of(page, size, sort);

        return driverService.findAll(pageRequest);
    }
}
