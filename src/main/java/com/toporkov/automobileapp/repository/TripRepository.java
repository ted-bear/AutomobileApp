package com.toporkov.automobileapp.repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.toporkov.automobileapp.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRepository extends JpaRepository<Trip, UUID> {

    List<Trip> findAllByStartedAtAfterAndEndedAtBefore(
        Instant startedAt,
        Instant endedAt
    );

    List<Trip> findAllByVehicleIdAndStartedAtAfterAndEndedAtBefore(
        UUID vehicleId,
        Instant startedAt,
        Instant endedAt
    );
}
