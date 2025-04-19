package com.toporkov.automobileapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.toporkov.automobileapp.model.Condition;
import com.toporkov.automobileapp.model.Enterprise;
import com.toporkov.automobileapp.model.Vehicle;
import com.toporkov.automobileapp.repository.EnterpriseRepository;
import com.toporkov.automobileapp.repository.VehicleRepository;
import com.toporkov.automobileapp.web.dto.domain.ImportResult;
import com.toporkov.automobileapp.web.dto.domain.enterprise.EnterpriseCsvDTO;
import com.toporkov.automobileapp.web.dto.domain.vehicle.VehicleCsvDTO;
import com.toporkov.automobileapp.web.mapper.EnterpriseMapper;
import com.toporkov.automobileapp.web.mapper.VehicleMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnterpriseImportService {

    private final EnterpriseRepository enterpriseRepository;
    private final VehicleRepository vehicleRepository;
    private final EnterpriseMapper enterpriseMapper;
    private final VehicleMapper vehicleMapper;
    private final Validator validator;

    private static Optional<Vehicle> findRelationError(ArrayList<Vehicle> validVehicles, List<UUID> enterprisesIds) {
        return validVehicles.stream()
            .filter(vehicle -> vehicle.getEnterprise() == null)
            .filter(vehicle -> !enterprisesIds.contains(vehicle.getEnterprise().getId()))
            .findAny();
    }

    private static <T> String getErrorMessages(Set<ConstraintViolation<T>> violations) {
        return violations.stream()
            .map(ConstraintViolation::getMessage)
            .collect(Collectors.joining(", "));
    }

    @Transactional
    public ImportResult importEnterprises(
        final List<EnterpriseCsvDTO> csvDtos,
        final Class<?>... groups
    ) {
        var validEnterprises = new ArrayList<Enterprise>();
        var errors = new ArrayList<String>();

        for (EnterpriseCsvDTO dto : csvDtos) {
            var violations = validator.validate(dto, groups);

            if (violations.isEmpty()) {
                Enterprise enterprise = enterpriseMapper.mapCsvDtoToEntity(dto);
                enterprise.setActive(true);
                validEnterprises.add(enterprise);
            } else {
                errors.add(getErrorMessages(violations));
            }
        }

        var saved = enterpriseRepository.saveAll(validEnterprises);

        return new ImportResult(
            saved.size(),
            errors.size(),
            errors
        );
    }

    @Transactional
    public ImportResult importEnterprisesAndVehicles(
        final List<EnterpriseCsvDTO> enterpriseCsvDTOS,
        final List<VehicleCsvDTO> vehicleCsvDTOS,
        final Class<?>... groups
    ) {
        var validEnterprises = new ArrayList<Enterprise>();
        var enterpriseErrors = new ArrayList<String>();

        for (EnterpriseCsvDTO dto : enterpriseCsvDTOS) {
            var violations = validator.validate(dto, groups);

            if (violations.isEmpty()) {
                var enterprise = enterpriseMapper.mapCsvDtoToEntity(dto);
                enterprise.setActive(true);
                validEnterprises.add(enterprise);
            } else {
                enterpriseErrors.add(getErrorMessages(violations));
            }
        }

        enterpriseRepository.saveAll(validEnterprises);

        var validVehicles = new ArrayList<Vehicle>();
        var vehicleErrors = new ArrayList<String>();

        for (VehicleCsvDTO dto : vehicleCsvDTOS) {
            var violations = validator.validate(dto, groups);

            if (violations.isEmpty()) {
                var vehicle = vehicleMapper.mapCsvDtoToEntity(dto);
                vehicle.setActive(true);
                vehicle.setPurchaseDate(dto.getPurchaseDate().toInstant());
                vehicle.setCondition(Condition.getByDescription(dto.getCondition()));
                validVehicles.add(vehicle);
            } else {
                vehicleErrors.add(getErrorMessages(violations));
            }
        }

        var enterprisesIds = validEnterprises.stream().map(Enterprise::getId).toList();
        var vehicleWithoutRelation = findRelationError(validVehicles, enterprisesIds);

        if (vehicleWithoutRelation.isPresent()) {
            return new ImportResult(
                0,
                1,
                List.of("Vehicle has a null or not existing relation with Enterprise")
            );
        }

        vehicleRepository.saveAll(validVehicles);
        vehicleErrors.addAll(enterpriseErrors);

        return new ImportResult(
            validEnterprises.size() + validVehicles.size(),
            vehicleErrors.size(),
            vehicleErrors
        );
    }
}
