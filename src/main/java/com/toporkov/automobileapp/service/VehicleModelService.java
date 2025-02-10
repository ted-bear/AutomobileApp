package com.toporkov.automobileapp.service;

import com.toporkov.automobileapp.model.VehicleModel;
import com.toporkov.automobileapp.repository.VehicleModelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleModelService {

    private final VehicleModelRepository vehicleModelRepository;

    public VehicleModelService(VehicleModelRepository vehicleModelRepository) {
        this.vehicleModelRepository = vehicleModelRepository;
    }

    public List<VehicleModel> findAll() {
        return vehicleModelRepository.findAll();
    }
}
