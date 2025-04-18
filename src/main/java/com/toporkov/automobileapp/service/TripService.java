package com.toporkov.automobileapp.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.toporkov.automobileapp.client.GeoApiClient;
import com.toporkov.automobileapp.model.Trip;
import com.toporkov.automobileapp.model.VehicleCoordinate;
import com.toporkov.automobileapp.repository.TripRepository;
import com.toporkov.automobileapp.repository.VehicleCoordinateRepository;
import com.toporkov.automobileapp.web.dto.domain.trip.TripDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class TripService {

    private final TripRepository tripRepository;
    private final VehicleCoordinateRepository vehicleCoordinateRepository;
    private final GeoApiClient geoApiClient;

    public TripService(
        final TripRepository tripRepository,
        final VehicleCoordinateRepository vehicleCoordinateRepository,
        final GeoApiClient geoApiClient
    ) {
        this.tripRepository = tripRepository;
        this.vehicleCoordinateRepository = vehicleCoordinateRepository;
        this.geoApiClient = geoApiClient;
    }

    public List<Trip> findAllByInterval(
        final Instant startedAt,
        final Instant endedAt
    ) {
        return tripRepository.findAllByStartedAtAfterAndEndedAtBefore(startedAt, endedAt);
    }

    public List<TripDTO> findTripsBetween(
        final UUID vehicleId,
        final Instant startedAt,
        final Instant endedAt
    ) {
        var trips = tripRepository.findAllByVehicleIdAndStartedAtAfterAndEndedAtBefore(vehicleId, startedAt, endedAt);
        List<TripDTO> tripDTOList = new ArrayList<>();

        for (var trip : trips) {
            var startCoord = vehicleCoordinateRepository
                .findByVehicleIdAndCreateAt(vehicleId, trip.getStartedAt());
            var endCoord = vehicleCoordinateRepository
                .findByVehicleIdAndCreateAt(vehicleId, trip.getEndedAt());

            tripDTOList.add(mapEntityToDTO(trip, startCoord, endCoord));
        }

        return tripDTOList;
    }

    @Transactional
    public void saveTrip(final Trip trip) {
        tripRepository.save(trip);
    }

    private String findAddressByCoordinate(VehicleCoordinate vehicleCoordinate) {
        var addressSuggestions = geoApiClient
            .getAddressSuggestions(
                vehicleCoordinate.getLongitude(),
                vehicleCoordinate.getLatitude()
            ).getSuggestions();

        return !addressSuggestions.isEmpty() ? addressSuggestions.get(0).getValue() : "";
    }

    private TripDTO mapEntityToDTO(
        final Trip trip,
        final VehicleCoordinate startCoord,
        final VehicleCoordinate endCoord
    ) {
        var startAddress = findAddressByCoordinate(startCoord);
        var endAddress = findAddressByCoordinate(endCoord);

        return new TripDTO(
            trip.getId(),
            trip.getStartedAt(),
            trip.getEndedAt(),
            trip.getVehicle().getId(),
            startCoord.getId(),
            endCoord.getId(),
            startAddress,
            endAddress
        );
    }
}
