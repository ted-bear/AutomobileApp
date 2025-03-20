package com.toporkov.automobileapp.web.rest;

import com.toporkov.automobileapp.service.VehicleService;
import com.toporkov.automobileapp.util.validator.VehicleValidator;
import com.toporkov.automobileapp.web.dto.domain.vehicle.VehicleDTO;
import com.toporkov.automobileapp.web.dto.validation.OnCreate;
import com.toporkov.automobileapp.web.dto.validation.OnUpdate;
import com.toporkov.automobileapp.web.mapper.VehicleMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/vehicles")
public class VehicleRestController {

    private final VehicleService vehicleService;
    private final VehicleValidator vehicleValidator;
    private final VehicleMapper vehicleMapper;

    public VehicleRestController(final VehicleService vehicleService,
                                 final VehicleValidator vehicleValidator,
                                 final VehicleMapper vehicleMapper) {
        this.vehicleService = vehicleService;
        this.vehicleValidator = vehicleValidator;
        this.vehicleMapper = vehicleMapper;
    }

    @GetMapping
    public Page<VehicleDTO> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending
    ) {
        final Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        final Pageable pageable = PageRequest.of(page, size, sort);

        return vehicleService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public VehicleDTO getVehicle(@PathVariable("id") int id) {
        return vehicleService.getById(id);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createVehicle(@Validated(OnCreate.class)
                                                    @RequestBody VehicleDTO vehicleDTO,
                                                    BindingResult bindingResult) {
        vehicleValidator.validate(vehicleDTO, bindingResult);
        vehicleService.save(vehicleMapper.mapDtoToEntity(vehicleDTO));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateVehicle(@PathVariable("id") Integer id,
                                                    @Validated(OnUpdate.class)
                                                    @RequestBody VehicleDTO vehicleDTO,
                                                    BindingResult bindingResult) {
        vehicleDTO.setId(id);
        vehicleValidator.validate(vehicleDTO, bindingResult);
        vehicleService.update(id, vehicleMapper.mapDtoToEntity(vehicleDTO));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteVehicle(@PathVariable("id") int id) {
        vehicleService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
