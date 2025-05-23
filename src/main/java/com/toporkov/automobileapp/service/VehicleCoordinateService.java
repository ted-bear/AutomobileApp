package com.toporkov.automobileapp.service;

import com.toporkov.automobileapp.model.Trip;
import com.toporkov.automobileapp.model.VehicleCoordinate;
import com.toporkov.automobileapp.repository.VehicleCoordinateRepository;
import com.toporkov.automobileapp.repository.VehicleRepository;
import com.toporkov.automobileapp.util.exception.InvalidTripTimeException;
import com.toporkov.automobileapp.web.dto.domain.coordinate.CreateCoordinateDTO;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class VehicleCoordinateService {

    private final GeometryFactory geometryFactory;
    private final VehicleCoordinateRepository vehicleCoordinateRepository;
    private final VehicleRepository vehicleRepository;
    private final TripService tripService;

    public VehicleCoordinateService(
        final GeometryFactory geometryFactory,
        final VehicleCoordinateRepository vehicleCoordinateRepository,
        final VehicleRepository vehicleRepository,
        final TripService tripService
    ) {
        this.geometryFactory = geometryFactory;
        this.vehicleCoordinateRepository = vehicleCoordinateRepository;
        this.vehicleRepository = vehicleRepository;
        this.tripService = tripService;
    }

    public List<VehicleCoordinate> findAllByTime(
        final UUID vehicleId,
        final Instant startTime,
        final Instant stopTime
    ) {

        return vehicleCoordinateRepository
            .findAllByVehicleIdAndCreateAtBetween(vehicleId, startTime, stopTime);
    }

    public List<VehicleCoordinate> findAllByTrips(final Instant startTime, final Instant stopTime) {
        var tripsInInterval = tripService.findAllByInterval(startTime, stopTime);
        List<VehicleCoordinate> tripCoordinates = new ArrayList<>();

        for (var trip : tripsInInterval) {
            tripCoordinates.addAll(
                findAllByTime(
                    trip.getVehicle().getId(),
                    trip.getStartedAt(),
                    trip.getEndedAt()
                )
            );
        }

        return tripCoordinates;
    }

    @Transactional
    public void saveAllCoordinates(final List<CreateCoordinateDTO> coordinateDTOList) {
        vehicleCoordinateRepository.saveAll(
            coordinateDTOList.stream()
                .map(this::mapDtoToEntity)
                .toList()
        );
    }

    @Transactional
    public void saveTripAndCoordinates(final List<CreateCoordinateDTO> coordinateDTOList,
                                       final UUID vehicleId) {
        final var startTime = coordinateDTOList.get(0).getCreateAt();
        final var stopTime = coordinateDTOList.get(coordinateDTOList.size() - 1).getCreateAt();

        var allByTime = findAllByTime(vehicleId, startTime, stopTime);

        if (!allByTime.isEmpty()) {
            throw new InvalidTripTimeException("Время поездки накладывается с уже существующими");
        }

        var trip = new Trip();
        var vehicle = vehicleRepository.findById(vehicleId).orElseThrow(RuntimeException::new);
        trip.setVehicle(vehicle);
        trip.setStartedAt(startTime);
        trip.setEndedAt(stopTime);

        tripService.saveTrip(trip);
        saveAllCoordinates(coordinateDTOList);
    }

    private VehicleCoordinate mapDtoToEntity(final CreateCoordinateDTO createCoordinateDTO) {
        var vehicleCoordinate = new VehicleCoordinate();

        vehicleCoordinate.setCreateAt(createCoordinateDTO.getCreateAt());
        vehicleCoordinate.setLatitude(createCoordinateDTO.getLatitude());
        vehicleCoordinate.setLongitude(createCoordinateDTO.getLongitude());
        vehicleCoordinate.setPosition(
            getPoint(
                createCoordinateDTO.getLatitude(),
                createCoordinateDTO.getLongitude()
            )
        );
        vehicleCoordinate.setVehicle(vehicleRepository
            .findById(UUID.fromString(createCoordinateDTO.getVehicleId()))
            .orElseThrow(RuntimeException::new)
        );

        return vehicleCoordinate;
    }

    private Point getPoint(Double latitude, Double longitude) {
        return geometryFactory.createPoint(new Coordinate(longitude, latitude));
    }
}
