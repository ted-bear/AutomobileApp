package com.toporkov.automobileapp.web.rest;

import com.toporkov.automobileapp.model.VehicleModel;
import com.toporkov.automobileapp.service.VehicleModelService;
import com.toporkov.automobileapp.web.dto.VehicleModelDto;
import com.toporkov.automobileapp.web.dto.VehicleModelListDto;
import com.toporkov.automobileapp.web.mapper.VehicleModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/vehicleModels")
public class VehicleModelRestController {

    private final VehicleModelService vehicleModelService;
    private final VehicleModelMapper vehicleModelMapper;

    public VehicleModelRestController(final VehicleModelService vehicleModelService,
                                      final VehicleModelMapper vehicleModelMapper) {
        this.vehicleModelService = vehicleModelService;
        this.vehicleModelMapper = vehicleModelMapper;
    }

    @GetMapping
    public VehicleModelListDto getAllModels() {
        List<VehicleModel> vehicleModels = vehicleModelService.findAll();
        return new VehicleModelListDto(
                vehicleModels.stream()
                        .map(vehicleModelMapper::mapEntityToDto)
                        .toList()
        );
    }

    @GetMapping("/{id}")
    public VehicleModelDto showVehicle(@PathVariable("id") int id) {
        final VehicleModel vehicleToShow = vehicleModelService.getById(id);
        return vehicleModelMapper.mapEntityToDto(vehicleToShow);
    }
}
