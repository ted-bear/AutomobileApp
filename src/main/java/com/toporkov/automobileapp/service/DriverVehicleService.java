package com.toporkov.automobileapp.service;

import com.toporkov.automobileapp.model.Driver;
import com.toporkov.automobileapp.model.DriverVehicle;
import com.toporkov.automobileapp.model.Vehicle;
import com.toporkov.automobileapp.repository.DriverVehicleRepository;
import com.toporkov.automobileapp.util.exception.DriverAlreadyActiveException;
import com.toporkov.automobileapp.util.exception.VehicleAlreadyActiveException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Optional;

@Service
@Transactional
public class DriverVehicleService {

    private final DriverService driverService;
    private final VehicleService vehicleService;
    private final DriverVehicleRepository driverVehicleRepository;

    public DriverVehicleService(final DriverService driverService,
                                final VehicleService vehicleService,
                                final DriverVehicleRepository driverVehicleRepository) {
        this.driverService = driverService;
        this.vehicleService = vehicleService;
        this.driverVehicleRepository = driverVehicleRepository;
    }

    public void setActiveDriver(Integer driverId, Integer vehicleId) {
        Assert.notNull(driverId, "driverId argument is null");
        Assert.notNull(vehicleId, "vehicleId argument is null");

        validateDriversAssignments(driverId);
        validateVehicleAssignments(vehicleId);

        final Optional<DriverVehicle> driverVehicleOptional =
                driverVehicleRepository.findByVehicleIdAndDriverId(vehicleId, driverId);

        if (driverVehicleOptional.isPresent()) {
            final DriverVehicle driverVehicle = driverVehicleOptional.get();
            driverVehicle.setActive(true);
            driverVehicleRepository.save(driverVehicle);
        } else {
            createNewDriverAssignment(driverId, vehicleId);
        }

    }

    private void createNewDriverAssignment(Integer driverId, Integer vehicleId) {
        final Vehicle vehicle = vehicleService.getById(vehicleId);
        final Driver driver = driverService.getById(driverId);
        driverVehicleRepository.save(new DriverVehicle(driver, vehicle, true));
    }

    private void validateDriversAssignments(Integer driverId) {
        final Optional<DriverVehicle> driverVehicle = driverVehicleRepository.findByDriverId(driverId);

        if (driverVehicle.isPresent() && driverVehicle.get().getActive()) {
            throw new DriverAlreadyActiveException();
        }
    }

    private void validateVehicleAssignments(Integer driverId) {
        final Optional<DriverVehicle> driverVehicle = driverVehicleRepository.findByVehicleId(driverId);
        if (driverVehicle.isPresent() && driverVehicle.get().getActive()) {
            throw new VehicleAlreadyActiveException();
        }
    }
}
