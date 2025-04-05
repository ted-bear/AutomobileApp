package com.toporkov.automobileapp.service;

import com.toporkov.automobileapp.model.VehicleCoordinate;
import com.toporkov.automobileapp.repository.VehicleCoordinateRepository;
import com.toporkov.automobileapp.repository.VehicleRepository;
import com.toporkov.automobileapp.web.dto.domain.coordinate.CreateCoordinateDTO;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class VehicleCoordinateService {

    private final GeometryFactory geometryFactory;
    private final VehicleCoordinateRepository vehicleCoordinateRepository;
    private final VehicleRepository vehicleRepository;
    private final TripService tripService;

    public VehicleCoordinateService(final GeometryFactory geometryFactory,
                                    final VehicleCoordinateRepository vehicleCoordinateRepository,
                                    final VehicleRepository vehicleRepository,
                                    final TripService tripService) {
        this.geometryFactory = geometryFactory;
        this.vehicleCoordinateRepository = vehicleCoordinateRepository;
        this.vehicleRepository = vehicleRepository;
        this.tripService = tripService;
    }

    public List<VehicleCoordinate> findAllByTime(final int vehicleId,
                                                 final Instant startTime,
                                                 final Instant stopTime) {

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

    private VehicleCoordinate mapDtoToEntity(CreateCoordinateDTO createCoordinateDTO) {
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
                .findById(createCoordinateDTO.getVehicleId())
                .orElseThrow(RuntimeException::new)
        );

        return vehicleCoordinate;
    }

    private Point getPoint(Double latitude, Double longitude) {
        return geometryFactory.createPoint(new Coordinate(longitude, latitude));
    }
}
