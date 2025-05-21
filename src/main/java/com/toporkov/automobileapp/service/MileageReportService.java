package com.toporkov.automobileapp.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

import com.toporkov.automobileapp.model.Report;
import com.toporkov.automobileapp.model.Trip;
import com.toporkov.automobileapp.model.Vehicle;
import com.toporkov.automobileapp.model.VehicleCoordinate;
import com.toporkov.automobileapp.repository.TripRepository;
import com.toporkov.automobileapp.repository.VehicleCoordinateRepository;
import com.toporkov.automobileapp.repository.VehicleRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MileageReportService {

    private final VehicleCoordinateRepository vehicleCoordinateRepository;
    private final TripRepository tripRepository;
    private final VehicleRepository vehicleRepository;

    @Transactional
    public Report generateMileageReport(
        UUID vehicleId, Report.PeriodType periodType,
        LocalDateTime start, LocalDateTime end
    ) {

        Vehicle vehicle = vehicleRepository.findById(vehicleId)
            .orElseThrow(() -> new EntityNotFoundException("Vehicle not found"));

        List<VehicleCoordinate> vehicleCoordinates = vehicleCoordinateRepository
            .findAllByVehicleIdAndCreateAtBetween(vehicleId, start.toInstant(ZoneOffset.UTC),
                end.toInstant(ZoneOffset.UTC));

        Report report = new Report();
        report.setName("Отчёт о пробеге: " + vehicle.getNumber());
        report.setPeriodType(periodType);
        report.setStartDate(start);
        report.setStopDate(end);
        report.setGeneratedAt(LocalDateTime.now());
        report.calculateMileageResults(vehicleCoordinates);

        return report;
    }

    @Transactional
    public Report generateTripsReport(
        UUID vehicleId, Report.PeriodType periodType,
        LocalDateTime start, LocalDateTime end
    ) {

        Vehicle vehicle = vehicleRepository.findById(vehicleId)
            .orElseThrow(() -> new EntityNotFoundException("Vehicle not found"));

        List<Trip> vehicleTrips = tripRepository
            .findAllByVehicleIdAndStartedAtAfterAndEndedAtBefore(vehicleId, start.toInstant(ZoneOffset.UTC),
                end.toInstant(ZoneOffset.UTC));

        Report report = new Report();
        report.setName("Отчёт о поездках: " + vehicle.getNumber());
        report.setPeriodType(periodType);
        report.setStartDate(start);
        report.setStopDate(end);
        report.setGeneratedAt(LocalDateTime.now());
        report.calculateTripsResults(vehicleTrips);

        return report;
    }


}
