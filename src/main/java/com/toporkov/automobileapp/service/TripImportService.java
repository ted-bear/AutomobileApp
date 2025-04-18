package com.toporkov.automobileapp.service;

import java.util.ArrayList;
import java.util.List;

import com.toporkov.automobileapp.model.Trip;
import com.toporkov.automobileapp.model.Vehicle;
import com.toporkov.automobileapp.repository.TripRepository;
import com.toporkov.automobileapp.repository.VehicleRepository;
import com.toporkov.automobileapp.web.dto.domain.ImportResult;
import com.toporkov.automobileapp.web.dto.domain.trip.TripCsvDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TripImportService {

    private final TripRepository tripRepository;
    private final VehicleRepository vehicleRepository;

    @Transactional
    public ImportResult importTrips(List<TripCsvDTO> csvDtos) {
        List<Trip> validEnterprises = new ArrayList<>();
        List<String> errors = new ArrayList<>();

        for (TripCsvDTO dto : csvDtos) {
            Vehicle vehicle = vehicleRepository.findById(dto.getVehicleId()).orElse(null);
            validEnterprises.add(new Trip(dto.getStartedAt(), dto.getEndedAt(), vehicle));
        }

        List<Trip> saved = tripRepository.saveAll(validEnterprises);

        return new ImportResult(
            saved.size(),
            0,
            errors
        );
    }
}
