package com.toporkov.automobileapp.web.rest;

import com.toporkov.automobileapp.model.Manager;
import com.toporkov.automobileapp.service.VehicleService;
import com.toporkov.automobileapp.util.SecurityUtil;
import com.toporkov.automobileapp.web.dto.domain.vehicle.VehicleDTO;
import com.toporkov.automobileapp.web.dto.domain.vehicle.VehicleListDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleRestController {

    private final VehicleService vehicleService;

    public VehicleRestController(final VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping
    public VehicleListDTO findAll() {
        final Manager manager = SecurityUtil.getCurrentManager();
        final List<VehicleDTO> vehicleDTOs = vehicleService.findAllByManager(manager);
        return new VehicleListDTO(vehicleDTOs);
    }

    @GetMapping("/{id}")
    public VehicleDTO getVehicle(@PathVariable("id") int id) {
        return vehicleService.getById(id);
    }
}
