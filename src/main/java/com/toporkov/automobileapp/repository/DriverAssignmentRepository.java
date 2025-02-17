package com.toporkov.automobileapp.repository;

import com.toporkov.automobileapp.model.DriverAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverAssignmentRepository extends JpaRepository<DriverAssignment, Integer> {

    Optional<DriverAssignment> findByDriverId(Integer driverId);

    Optional<DriverAssignment> findByVehicleId(Integer vehicleId);

    Optional<DriverAssignment> findByVehicleIdAndDriverId(Integer vehicleId, Integer driverId);
}
