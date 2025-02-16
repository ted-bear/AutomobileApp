package com.toporkov.automobileapp.repository;

import com.toporkov.automobileapp.model.DriverVehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverVehicleRepository extends JpaRepository<DriverVehicle, Integer> {

    Optional<DriverVehicle> findByDriverId(Integer driverId);

    Optional<DriverVehicle> findByVehicleId(Integer vehicleId);

    Optional<DriverVehicle> findByVehicleIdAndDriverId(Integer vehicleId, Integer driverId);
}
