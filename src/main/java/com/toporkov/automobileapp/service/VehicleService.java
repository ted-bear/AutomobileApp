package com.toporkov.automobileapp.service;

import com.toporkov.automobileapp.model.Vehicle;
import com.toporkov.automobileapp.repository.VehicleRepository;
import com.toporkov.automobileapp.util.exception.VehicleNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleService(final VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public List<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }

    public Vehicle getById(Integer id) {
        Assert.notNull(id, "Vehicle id shouldn't be null");

        return vehicleRepository.findById(id).orElseThrow(VehicleNotFoundException::new);
    }

    @Transactional
    public void save(Vehicle vehicle) {
        Assert.notNull(vehicle, "Vehicle to save shouldn't be null");

        vehicleRepository.save(vehicle);
    }

    @Transactional
    public void update(Integer id, Vehicle vehicle) {
        Assert.notNull(id, "Vehicle to update id shouldn't be null");
        Assert.notNull(vehicle, "Vehicle to update shouldn't be null");

        vehicle.setId(id);
        vehicleRepository.save(vehicle);
    }

    @Transactional
    public void delete(Integer id) {
        Assert.notNull(id, "Vehicle to delete id shouldn't be null");

        vehicleRepository.deleteById(id);
    }
}
