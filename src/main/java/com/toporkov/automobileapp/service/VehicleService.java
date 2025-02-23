package com.toporkov.automobileapp.service;

import com.toporkov.automobileapp.model.Manager;
import com.toporkov.automobileapp.model.Vehicle;
import com.toporkov.automobileapp.model.VehicleModel;
import com.toporkov.automobileapp.repository.VehicleRepository;
import com.toporkov.automobileapp.util.exception.VehicleNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final ManagerService managerService;

    public VehicleService(final VehicleRepository vehicleRepository,
                          final ManagerService managerService) {
        this.vehicleRepository = vehicleRepository;
        this.managerService = managerService;
    }

    public List<Vehicle> findAllByManager(Manager manager) {
        final Manager ctxManager = managerService.getById(manager.getId());
        return ctxManager.getEnterprises()
                .stream()
                .flatMap(enterprise -> findAllByEnterprise(enterprise.getId()).stream())
                .toList();
    }

    public List<Vehicle> findAllByEnterprise(Integer enterpriseId) {
        return vehicleRepository.findAll()
                .stream()
                .filter(vehicle -> enterpriseId == null || Objects.equals(vehicle.getEnterprise().getId(), enterpriseId))
                .toList();
    }

    public List<Vehicle> findAll() {
        return vehicleRepository.findAllByIsActiveTrue();
    }

    public Vehicle getById(Integer id) {
        Assert.notNull(id, "Vehicle id shouldn't be null");

        return vehicleRepository.findById(id).orElseThrow(VehicleNotFoundException::new);
    }

    public Optional<Vehicle> getByNumber(String number) {
        Assert.notNull(number, "Number shouldn't be null");
        return vehicleRepository.findByNumber(number);
    }

    @Transactional
    public void save(Vehicle vehicle) {
        Assert.notNull(vehicle, "Vehicle to save shouldn't be null");

        vehicle.getVehicleModel().addVehicle(vehicle);
        vehicleRepository.save(vehicle);
    }

    @Transactional
    public void update(Integer id, Vehicle vehicle) {
        Assert.notNull(id, "Vehicle to update id shouldn't be null");
        Assert.notNull(vehicle, "Vehicle to update shouldn't be null");

        updateRelations(id, vehicle);

        vehicle.setId(id);
        vehicleRepository.save(vehicle);
    }

    private void updateRelations(Integer id, Vehicle vehicle) {
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

    @Transactional
    public void delete(Integer id) {
        Assert.notNull(id, "Vehicle to delete id shouldn't be null");
        vehicleRepository
                .findById(id)
                .ifPresent(vehicle -> {
                    vehicle.setActive(false);
                    vehicleRepository.save(vehicle);
                });
    }
}
