package com.toporkov.automobileapp.web.rest;

import com.toporkov.automobileapp.service.TripService;
import com.toporkov.automobileapp.service.VehicleCoordinateService;
import com.toporkov.automobileapp.web.dto.domain.VehicleCoordinateDTO;
import com.toporkov.automobileapp.web.dto.domain.trip.TripDTO;
import com.toporkov.automobileapp.web.mapper.VehicleCoordinateMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.List;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME;

@RestController
@RequestMapping("/api/v1/trips")
public class TripRestController {

    private final VehicleCoordinateService vehicleCoordinateService;
    private final VehicleCoordinateMapper vehicleCoordinateMapper;
    private final TripService tripService;

    public TripRestController(final VehicleCoordinateService vehicleCoordinateService,
                              final VehicleCoordinateMapper vehicleCoordinateMapper,
                              final TripService tripService) {
        this.vehicleCoordinateService = vehicleCoordinateService;
        this.vehicleCoordinateMapper = vehicleCoordinateMapper;
        this.tripService = tripService;
    }

    @GetMapping
    public ResponseEntity<List<VehicleCoordinateDTO>> getTripsCoordinates(
            @RequestParam @DateTimeFormat(iso = DATE_TIME) ZonedDateTime start,
            @RequestParam @DateTimeFormat(iso = DATE_TIME) ZonedDateTime stop
    ) {
        var coordinatesTrips = vehicleCoordinateService.findAllByTrips(
                start.toInstant(),
                stop.toInstant()
        );

        return ResponseEntity.ok(coordinatesTrips.stream()
                .map(vehicleCoordinateMapper::mapEntityToDTO)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<TripDTO>> getTrips(
            @PathVariable("id") Integer vehicleId,
            @RequestParam @DateTimeFormat(iso = DATE_TIME) ZonedDateTime start,
            @RequestParam @DateTimeFormat(iso = DATE_TIME) ZonedDateTime stop
    ) {
        var trips = tripService.findTripsBetween(
                vehicleId,
                start.toInstant(),
                stop.toInstant()
        );

        return ResponseEntity.ok(trips);
    }
}
