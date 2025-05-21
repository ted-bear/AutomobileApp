package com.toporkov.automobileapp.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.toporkov.automobileapp.model.DriverAssignment;
import com.toporkov.automobileapp.model.Enterprise;
import com.toporkov.automobileapp.model.Manager;
import com.toporkov.automobileapp.model.Vehicle;
import com.toporkov.automobileapp.model.VehicleModel;
import com.toporkov.automobileapp.repository.VehicleRepository;
import com.toporkov.automobileapp.util.SecurityUtil;
import com.toporkov.automobileapp.util.exception.ManagerDoNotHaveAccessException;
import com.toporkov.automobileapp.util.exception.VehicleNotDeletedException;
import com.toporkov.automobileapp.util.exception.VehicleNotFoundException;
import com.toporkov.automobileapp.web.dto.domain.vehicle.VehicleDTO;
import com.toporkov.automobileapp.web.mapper.VehicleMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final ManagerService managerService;
    private final VehicleMapper vehicleMapper;

    public VehicleService(
        final VehicleRepository vehicleRepository,
        final ManagerService managerService,
        final VehicleMapper vehicleMapper
    ) {
        this.vehicleRepository = vehicleRepository;
        this.managerService = managerService;
        this.vehicleMapper = vehicleMapper;
    }

    public Page<VehicleDTO> findAll(Pageable pageable) {
        final List<UUID> enterpriseIds = managerService.getCurrentManager()
            .getEnterprises()
            .stream()
            .map(Enterprise::getId)
            .toList();

        return vehicleRepository
            .findByIsActiveTrueAndEnterpriseIdIn(enterpriseIds, pageable)
            .map(vehicleMapper::mapEntityToDto);
    }

    public Page<VehicleDTO> findAllByEnterpriseId(Pageable pageable, UUID enterpriseId) {
        return vehicleRepository
            .findAllByEnterpriseId(enterpriseId, pageable)
            .map(vehicleMapper::mapEntityToDto);
    }

    public List<VehicleDTO> findAll() {
        return vehicleRepository
            .findAllByIsActiveTrue().stream()
            .map(vehicleMapper::mapEntityToDto)
            .toList();
    }

    private static void checkManagerAccess(UUID id, Manager currentManager) {
        final List<UUID> availableIds = currentManager.getEnterprises().stream()
            .flatMap(enterprise -> enterprise.getVehicles().stream())
            .map(Vehicle::getId)
            .toList();
        if (!availableIds.contains(id)) {
            throw new ManagerDoNotHaveAccessException();
        }
    }

    public Optional<Vehicle> getByNumber(String number) {
        return vehicleRepository.findByNumber(number);
    }

    private static void checkManagerAccessToEnterprise(Vehicle vehicle, Manager manager) {
        if (!manager.getEnterprises().contains(vehicle.getEnterprise())) {
            throw new ManagerDoNotHaveAccessException();
        }
    }

    public VehicleDTO getById(UUID id) {
        checkIdValidity(id);

        final Manager manager = managerService.getById(SecurityUtil.getCurrentManager().getId());
        checkManagerAccess(id, manager);

        return vehicleRepository
            .findById(id)
            .map(vehicleMapper::mapEntityToDto)
            .orElseThrow(VehicleNotFoundException::new);
    }

    @Transactional
    public void save(Vehicle vehicle) {
        final Manager manager = managerService.getCurrentManager();
        checkManagerAccessToEnterprise(vehicle, manager);

        vehicle.setId(UUID.randomUUID());
        vehicle.setActive(true);
        vehicle.setPurchaseDate(Instant.now());
        vehicle.getVehicleModel().addVehicle(vehicle);
        vehicleRepository.save(vehicle);
    }

    @Transactional
    public void update(UUID id, Vehicle vehicle) {
        final Manager manager = managerService.getCurrentManager();

        checkIdValidity(id);
        checkManagerAccess(id, manager);
        updateRelations(id, vehicle);

        vehicle.setId(id);
        vehicle.setActive(true);
        vehicleRepository.save(vehicle);
    }

    @Transactional
    public void delete(UUID id) {
        final Manager manager = managerService.getCurrentManager();
        checkIdValidity(id);
        checkManagerAccess(id, manager);

        vehicleRepository
            .findById(id)
            .ifPresent(vehicle -> {
                final Optional<DriverAssignment> activeDriver = vehicle.getDriverVehicles()
                    .stream()
                    .filter(DriverAssignment::getActive)
                    .findFirst();

                if (activeDriver.isPresent()) {
                    throw new VehicleNotDeletedException("Транспорт имеет активного водителя");
                }

                vehicle.setActive(false);
                vehicleRepository.save(vehicle);
            });
    }

    private void checkIdValidity(UUID id) {
        final List<UUID> idList = vehicleRepository.findAll().stream().map(Vehicle::getId).toList();

        if (!idList.contains(id)) {
            throw new VehicleNotFoundException();
        }
    }

    private void updateRelations(UUID id, Vehicle vehicle) {
        final Optional<Vehicle> optionalVehicle = vehicleRepository.findById(id);

        if (optionalVehicle.isPresent()) {
            final Vehicle vehicleToUpdate = optionalVehicle.get();
            final VehicleModel oldVehicleModel = vehicleToUpdate.getVehicleModel();
            final VehicleModel newVehicleModel = vehicle.getVehicleModel();

            if (oldVehicleModel.getId() != newVehicleModel.getId()) {
                oldVehicleModel.getVehicles().remove(vehicleToUpdate);
                newVehicleModel.addVehicle(vehicleToUpdate);
            }
        }
    }
}
