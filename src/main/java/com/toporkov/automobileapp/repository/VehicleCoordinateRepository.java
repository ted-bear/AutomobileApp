package com.toporkov.automobileapp.repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.toporkov.automobileapp.model.VehicleCoordinate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleCoordinateRepository extends JpaRepository<VehicleCoordinate, Long> {

    List<VehicleCoordinate> findAllByVehicleIdAndCreateAtBetween(UUID vehicleId, Instant start, Instant end);

    VehicleCoordinate findByVehicleIdAndCreateAt(UUID vehicleId, Instant createAt);
}
