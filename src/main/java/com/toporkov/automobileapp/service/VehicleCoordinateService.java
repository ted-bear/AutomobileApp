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
import java.util.List;

@Service
@Transactional(readOnly = true)
public class VehicleCoordinateService {

    private final GeometryFactory geometryFactory;
    private final VehicleCoordinateRepository vehicleCoordinateRepository;
    private final VehicleRepository vehicleRepository;

    public VehicleCoordinateService(final GeometryFactory geometryFactory,
                                    final VehicleCoordinateRepository vehicleCoordinateRepository,
                                    final VehicleRepository vehicleRepository) {
        this.geometryFactory = geometryFactory;
        this.vehicleCoordinateRepository = vehicleCoordinateRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public List<VehicleCoordinate> findAllByTime(final int vehicleId,
                                                 final Instant startTime,
                                                 final Instant stopTime) {

        return vehicleCoordinateRepository
                .findAllByVehicleIdAndCreateAtBetween(vehicleId, startTime, stopTime);
    }

    @Transactional
    public void saveCoordinate(final CreateCoordinateDTO createCoordinateDTO) {
        var vehicleCoordinate = mapDtoToEntity(createCoordinateDTO);
        vehicleCoordinateRepository.save(vehicleCoordinate);
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

        vehicleCoordinate.setCreateAt(Instant.now());
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
