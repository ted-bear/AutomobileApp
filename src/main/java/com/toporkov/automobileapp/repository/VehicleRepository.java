package com.toporkov.automobileapp.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.toporkov.automobileapp.model.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    Page<Vehicle> findByIsActiveTrueAndEnterpriseIdIn(List<UUID> enterpriseIds, Pageable pageable);

    Page<Vehicle> findAllByEnterpriseId(UUID enterpriseId, Pageable pageable);

    Optional<Vehicle> findByNumber(String number);

    List<Vehicle> findAllByIsActiveTrue();

    Optional<Vehicle> findById(UUID id);
}
