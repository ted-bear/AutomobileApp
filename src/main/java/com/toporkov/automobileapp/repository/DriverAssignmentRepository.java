package com.toporkov.automobileapp.repository;

import java.util.Optional;
import java.util.UUID;

import com.toporkov.automobileapp.model.DriverAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverAssignmentRepository extends JpaRepository<DriverAssignment, UUID> {

    Optional<DriverAssignment> findByDriverId(UUID driverId);

    Optional<DriverAssignment> findByVehicleId(UUID vehicleId);

    Optional<DriverAssignment> findByVehicleIdAndDriverId(UUID vehicleId, UUID driverId);
}
