package com.toporkov.automobileapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.toporkov.automobileapp.model.Condition;
import com.toporkov.automobileapp.model.Vehicle;
import com.toporkov.automobileapp.repository.VehicleRepository;
import com.toporkov.automobileapp.web.dto.domain.ImportResult;
import com.toporkov.automobileapp.web.dto.domain.vehicle.VehicleCsvDTO;
import com.toporkov.automobileapp.web.mapper.VehicleMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehicleImportService {

    private final VehicleRepository vehicleRepository;
    private final VehicleMapper mapper;
    private final Validator validator;

    @Transactional
    public ImportResult importVehicles(List<VehicleCsvDTO> csvDtos, Class<?>... groups) {
        List<Vehicle> validVehicles = new ArrayList<>();
        List<String> errors = new ArrayList<>();

        for (int i = 0; i < csvDtos.size(); i++) {
            VehicleCsvDTO dto = csvDtos.get(i);
            Set<ConstraintViolation<VehicleCsvDTO>> violations = validator.validate(dto, groups);

            if (violations.isEmpty()) {
                Vehicle vehicle = mapper.mapCsvDtoToEntity(dto);
                vehicle.setPurchaseDate(dto.getPurchaseDate().toInstant());
                vehicle.setCondition(Condition.getByDescription(dto.getCondition()));
                vehicle.setActive(true);
                validVehicles.add(vehicle);
            } else {
                errors.add(String.format("Line %d: %s", i + 1,
                    violations.stream()
                        .map(ConstraintViolation::getMessage)
                        .collect(Collectors.joining(", "))));
            }
        }

        List<Vehicle> saved = vehicleRepository.saveAll(validVehicles);

        return new ImportResult(
            saved.size(),
            errors.size(),
            errors
        );
    }
}
