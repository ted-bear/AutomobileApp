package com.toporkov.automobileapp.repository;

import java.util.List;
import java.util.UUID;

import com.toporkov.automobileapp.model.Driver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Integer> {

    Page<Driver> findByEnterpriseIdIn(List<UUID> enterpriseIds, Pageable pageable);

}
