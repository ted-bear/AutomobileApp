package com.toporkov.automobileapp.service;

import com.toporkov.automobileapp.model.Trip;
import com.toporkov.automobileapp.repository.TripRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class TripService {

    private final TripRepository tripRepository;

    public TripService(final TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    public List<Trip> findAllByInterval(Instant startedAt, Instant endedAt) {
        return tripRepository.findAllByStartedAtAfterAndEndedAtBefore(startedAt, endedAt);
    }

    @Transactional
    public void saveTrip(Trip trip) {
        tripRepository.save(trip);
    }
}
