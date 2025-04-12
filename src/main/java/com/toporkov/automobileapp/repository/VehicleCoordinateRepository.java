package com.toporkov.automobileapp.repository;

import com.toporkov.automobileapp.model.VehicleCoordinate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface VehicleCoordinateRepository extends JpaRepository<VehicleCoordinate, Long> {

    List<VehicleCoordinate> findAllByVehicleIdAndCreateAtBetween(int vehicleId, Instant start, Instant end);

    VehicleCoordinate findByVehicleIdAndCreateAt(int vehicleId, Instant createAt);
}
