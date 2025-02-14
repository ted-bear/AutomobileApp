package com.toporkov.automobileapp.repository;

import com.toporkov.automobileapp.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    Optional<Vehicle> findByNumber(String number);

    List<Vehicle> findAllByIsActiveTrue();
}
