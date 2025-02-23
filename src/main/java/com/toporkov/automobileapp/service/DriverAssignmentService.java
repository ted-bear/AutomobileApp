package com.toporkov.automobileapp.service;

import com.toporkov.automobileapp.model.Driver;
import com.toporkov.automobileapp.model.DriverAssignment;
import com.toporkov.automobileapp.model.Vehicle;
import com.toporkov.automobileapp.repository.DriverAssignmentRepository;
import com.toporkov.automobileapp.repository.DriverRepository;
import com.toporkov.automobileapp.repository.VehicleRepository;
import com.toporkov.automobileapp.util.exception.DriverAlreadyActiveException;
import com.toporkov.automobileapp.util.exception.VehicleAlreadyActiveException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Optional;

@Service
@Transactional
public class DriverAssignmentService {

    private final DriverRepository driverRepository;
    private final VehicleRepository vehicleRepository;
    private final DriverAssignmentRepository driverAssignmentRepository;

    public DriverAssignmentService(final DriverRepository driverRepository,
                                   final VehicleRepository vehicleRepository,
                                   final DriverAssignmentRepository driverAssignmentRepository) {
        this.driverRepository = driverRepository;
        this.vehicleRepository = vehicleRepository;
        this.driverAssignmentRepository = driverAssignmentRepository;
    }

    public void setActiveDriver(Integer driverId, Integer vehicleId) {
        Assert.notNull(driverId, "driverId argument is null");
        Assert.notNull(vehicleId, "vehicleId argument is null");

        validateDriversAssignments(driverId);
        validateVehicleAssignments(vehicleId);

        final Optional<DriverAssignment> driverVehicleOptional =
                driverAssignmentRepository.findByVehicleIdAndDriverId(vehicleId, driverId);

        if (driverVehicleOptional.isPresent()) {
            final DriverAssignment driverAssignment = driverVehicleOptional.get();
            driverAssignment.setActive(true);
            driverAssignmentRepository.save(driverAssignment);
        } else {
            createNewDriverAssignment(driverId, vehicleId);
        }

    }

    private void createNewDriverAssignment(Integer driverId, Integer vehicleId) {
        final Vehicle vehicle = vehicleRepository.findById(vehicleId).orElse(null);
        final Driver driver = driverRepository.findById(driverId).orElse(null);
        driverAssignmentRepository.save(new DriverAssignment(driver, vehicle, true));
    }

    private void validateDriversAssignments(Integer driverId) {
        final Optional<DriverAssignment> driverVehicle = driverAssignmentRepository.findByDriverId(driverId);

        if (driverVehicle.isPresent() && driverVehicle.get().getActive()) {
            throw new DriverAlreadyActiveException();
        }
    }

    private void validateVehicleAssignments(Integer driverId) {
        final Optional<DriverAssignment> driverVehicle = driverAssignmentRepository.findByVehicleId(driverId);
        if (driverVehicle.isPresent() && driverVehicle.get().getActive()) {
            throw new VehicleAlreadyActiveException();
        }
    }
}
