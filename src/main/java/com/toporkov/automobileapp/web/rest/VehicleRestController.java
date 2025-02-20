package com.toporkov.automobileapp.web.rest;

import com.toporkov.automobileapp.model.Vehicle;
import com.toporkov.automobileapp.service.VehicleService;
import com.toporkov.automobileapp.web.dto.VehicleDTO;
import com.toporkov.automobileapp.web.dto.VehicleListDTO;
import com.toporkov.automobileapp.web.mapper.VehicleMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleRestController {

    private final VehicleService vehicleService;
    private final VehicleMapper vehicleMapper;

    public VehicleRestController(final VehicleService vehicleService,
                                 final VehicleMapper vehicleMapper) {
        this.vehicleService = vehicleService;
        this.vehicleMapper = vehicleMapper;
    }

    @GetMapping
    public VehicleListDTO getAll() {
        final List<Vehicle> vehicles = vehicleService.findAll();
        final List<VehicleDTO> vehicleDTOS = vehicles.stream().map(vehicleMapper::mapEntityToDto).toList();
        return new VehicleListDTO(vehicleDTOS);
    }

    @GetMapping("/{id}")
    public VehicleDTO getVehicle(@PathVariable("id") int id) {
        final Vehicle vehicle = vehicleService.getById(id);
        return vehicleMapper.mapEntityToDto(vehicle);
    }
}
