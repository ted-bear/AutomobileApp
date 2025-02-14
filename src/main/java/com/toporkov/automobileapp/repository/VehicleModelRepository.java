package com.toporkov.automobileapp.repository;

import com.toporkov.automobileapp.model.VehicleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleModelRepository extends JpaRepository<VehicleModel, Integer> {

    List<VehicleModel> findAllByIsActiveTrue();
}
