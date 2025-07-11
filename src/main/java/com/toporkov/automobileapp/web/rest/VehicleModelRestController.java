package com.toporkov.automobileapp.web.rest;

import java.util.List;

import com.toporkov.automobileapp.model.VehicleModel;
import com.toporkov.automobileapp.service.VehicleModelService;
import com.toporkov.automobileapp.web.dto.domain.vehicleModel.VehicleModelDTO;
import com.toporkov.automobileapp.web.dto.domain.vehicleModel.VehicleModelListDTO;
import com.toporkov.automobileapp.web.mapper.VehicleModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/vehicleModels")
public class VehicleModelRestController {

    private final VehicleModelService vehicleModelService;
    private final VehicleModelMapper vehicleModelMapper;

    public VehicleModelRestController(
        final VehicleModelService vehicleModelService,
        final VehicleModelMapper vehicleModelMapper
    ) {
        this.vehicleModelService = vehicleModelService;
        this.vehicleModelMapper = vehicleModelMapper;
    }

    @GetMapping
    public VehicleModelListDTO getAllModels() {
        List<VehicleModel> vehicleModels = vehicleModelService.findAll();
        return new VehicleModelListDTO(
            vehicleModels.stream()
                .map(vehicleModelMapper::mapEntityToDto)
                .toList()
        );
    }

    @GetMapping("/{id}")
    public VehicleModelDTO showVehicle(@PathVariable("id") int id) {
        final VehicleModel vehicleToShow = vehicleModelService.getById(id);
        return vehicleModelMapper.mapEntityToDto(vehicleToShow);
    }
}
