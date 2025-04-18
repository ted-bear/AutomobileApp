package com.toporkov.automobileapp.web.rest;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.UUID;

import com.opencsv.bean.CsvToBeanBuilder;
import com.toporkov.automobileapp.service.VehicleImportService;
import com.toporkov.automobileapp.service.VehicleService;
import com.toporkov.automobileapp.util.validator.VehicleValidator;
import com.toporkov.automobileapp.web.dto.domain.ImportResult;
import com.toporkov.automobileapp.web.dto.domain.vehicle.VehicleCsvDTO;
import com.toporkov.automobileapp.web.dto.domain.vehicle.VehicleDTO;
import com.toporkov.automobileapp.web.dto.validation.OnCreate;
import com.toporkov.automobileapp.web.dto.validation.OnUpdate;
import com.toporkov.automobileapp.web.mapper.VehicleMapper;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.UnsupportedMediaTypeException;

@RestController
@RequestMapping("/api/v1/vehicles")
public class VehicleRestController {

    private final VehicleService vehicleService;
    private final VehicleImportService vehicleImportService;
    private final VehicleValidator vehicleValidator;
    private final VehicleMapper vehicleMapper;

    public VehicleRestController(
        final VehicleService vehicleService,
        final VehicleImportService vehicleImportService,
        final VehicleValidator vehicleValidator,
        final VehicleMapper vehicleMapper
    ) {
        this.vehicleService = vehicleService;
        this.vehicleImportService = vehicleImportService;
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

    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImportResult> importEnterprises(
        @RequestParam("file") @Valid MultipartFile file
    ) throws IOException {

        if (file.isEmpty()) {
            throw new BadRequestException("File is empty");
        }

        if (!"text/csv".equals(file.getContentType())) {
            throw new UnsupportedMediaTypeException("Only CSV files are supported");
        }

        List<VehicleCsvDTO> vehicles = new CsvToBeanBuilder<VehicleCsvDTO>(
            new InputStreamReader(file.getInputStream()))
            .withType(VehicleCsvDTO.class)
            .withThrowExceptions(true)
            .build()
            .parse();

        ImportResult result = vehicleImportService.importVehicles(vehicles, Default.class, OnCreate.class);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public VehicleDTO getVehicle(@PathVariable("id") UUID id) {
        return vehicleService.getById(id);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createVehicle(
        @Validated(OnCreate.class)
        @RequestBody VehicleDTO vehicleDTO,
        BindingResult bindingResult
    ) {
        vehicleValidator.validate(vehicleDTO, bindingResult);
        vehicleService.save(vehicleMapper.mapDtoToEntity(vehicleDTO));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateVehicle(
        @PathVariable("id") UUID id,
        @Validated(OnUpdate.class)
        @RequestBody VehicleDTO vehicleDTO,
        BindingResult bindingResult
    ) {
        vehicleDTO.setId(id);
        vehicleValidator.validate(vehicleDTO, bindingResult);
        vehicleService.update(id, vehicleMapper.mapDtoToEntity(vehicleDTO));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteVehicle(@PathVariable("id") UUID id) {
        vehicleService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
