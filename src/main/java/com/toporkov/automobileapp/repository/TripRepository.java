package com.toporkov.automobileapp.repository;

import com.toporkov.automobileapp.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Integer> {

    List<Trip> findAllByStartedAtAfterAndEndedAtBefore(Instant startedAt, Instant endedAt);
}
