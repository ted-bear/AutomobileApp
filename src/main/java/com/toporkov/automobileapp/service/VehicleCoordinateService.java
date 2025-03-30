package com.toporkov.automobileapp.service;

import com.toporkov.automobileapp.model.VehicleCoordinate;
import com.toporkov.automobileapp.repository.VehicleCoordinateRepository;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class VehicleCoordinateService {

    private final GeometryFactory geometryFactory;
    private final VehicleCoordinateRepository vehicleCoordinateRepository;

    public VehicleCoordinateService(final GeometryFactory geometryFactory,
                                    final VehicleCoordinateRepository vehicleCoordinateRepository) {
        this.geometryFactory = geometryFactory;
        this.vehicleCoordinateRepository = vehicleCoordinateRepository;
    }

    public List<VehicleCoordinate> findAllByTime(final int vehicleId,
                                                 final Instant startTime,
                                                 final Instant stopTime) {

        return vehicleCoordinateRepository
                .findAllByVehicleIdAndCreateAtBetween(vehicleId, startTime, stopTime);
    }

    private Point getPoint(Double latitude, Double longitude) {
        return geometryFactory.createPoint(new Coordinate(longitude, latitude));
    }
}
