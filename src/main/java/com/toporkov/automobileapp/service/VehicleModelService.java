package com.toporkov.automobileapp.service;

import java.util.List;

import com.toporkov.automobileapp.model.VehicleModel;
import com.toporkov.automobileapp.repository.VehicleModelRepository;
import com.toporkov.automobileapp.util.exception.VehicleModelNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@Transactional(readOnly = true)
public class VehicleModelService {

    private final VehicleModelRepository vehicleModelRepository;

    public VehicleModelService(VehicleModelRepository vehicleModelRepository) {
        this.vehicleModelRepository = vehicleModelRepository;
    }

    public List<VehicleModel> findAll() {
        return vehicleModelRepository.findAllByIsActiveTrue();
    }

    public VehicleModel getById(Integer id) {
        Assert.notNull(id, "VehicleModel id shouldn't be null");

        return vehicleModelRepository.findById(id).orElseThrow(VehicleModelNotFoundException::new);
    }

    @Transactional
    public void save(VehicleModel vehicleModel) {
        Assert.notNull(vehicleModel, "VehicleModel shouldn't be null");
        vehicleModelRepository.save(vehicleModel);
    }

    @Transactional
    public void update(Integer id, VehicleModel vehicleModel) {
        Assert.notNull(vehicleModel, "VehicleModel shouldn't be null");
        Assert.notNull(id, "VehicleModel id shouldn't be null");

        vehicleModel.setId(id);
        vehicleModelRepository.save(vehicleModel);
    }

    @Transactional
    public void delete(Integer id) {
        Assert.notNull(id, "VehicleModel id shouldn't be null");
        vehicleModelRepository
            .findById(id)
            .ifPresent(vehicleModel -> {
                vehicleModel.setActive(false);
                vehicleModelRepository.save(vehicleModel);
            });
    }
}
