package com.toporkov.automobileapp.web.rest;

import com.toporkov.automobileapp.model.Manager;
import com.toporkov.automobileapp.model.Vehicle;
import com.toporkov.automobileapp.service.VehicleService;
import com.toporkov.automobileapp.util.SecurityUtil;
import com.toporkov.automobileapp.util.exception.VehicleNotSavedException;
import com.toporkov.automobileapp.util.validator.VehicleValidator;
import com.toporkov.automobileapp.web.dto.domain.vehicle.VehicleDTO;
import com.toporkov.automobileapp.web.dto.domain.vehicle.VehicleListDTO;
import com.toporkov.automobileapp.web.dto.validation.OnCreate;
import com.toporkov.automobileapp.web.dto.validation.OnUpdate;
import com.toporkov.automobileapp.web.mapper.VehicleMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
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
    public VehicleListDTO findAll() {
        final Manager manager = SecurityUtil.getCurrentManager();
        final List<VehicleDTO> vehicleDTOs = vehicleService.findAllByManager(manager);
        return new VehicleListDTO(vehicleDTOs);
    }

    @GetMapping("/{id}")
    public VehicleDTO getVehicle(@PathVariable("id") int id) {
        return vehicleService.getById(id);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createVehicle(@Validated(OnCreate.class) @RequestBody VehicleDTO vehicleDTO,
                                                    BindingResult bindingResult) {
        final Vehicle vehicle = validateVehicleDTO(vehicleDTO, bindingResult);
        vehicleService.save(vehicle);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> updateVehicle(@PathVariable("id") Integer id,
                                                    @Validated(OnUpdate.class)
                                                    @RequestBody VehicleDTO vehicleDTO,
                                                    BindingResult bindingResult) {
        final Vehicle vehicle = validateVehicleDTO(vehicleDTO, bindingResult);
        vehicleService.update(id, vehicle);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteVehicle(@PathVariable("id") int id) {
        vehicleService.delete(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    private Vehicle validateVehicleDTO(VehicleDTO vehicleDTO, BindingResult bindingResult) {
        final Vehicle vehicle = vehicleMapper.mapDtoToEntity(vehicleDTO);
        vehicleValidator.validate(vehicle, bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder errBuilder = new StringBuilder();
            final List<FieldError> errs = bindingResult.getFieldErrors();

            errs.forEach(err -> errBuilder
                    .append(err.getField())
                    .append(" - ")
                    .append(err.getDefaultMessage())
                    .append(";\n")
            );

            throw new VehicleNotSavedException(errBuilder.toString());
        }
        return vehicle;
    }
}
