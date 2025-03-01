package com.toporkov.automobileapp.repository;

import com.toporkov.automobileapp.model.Driver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Integer> {

    Page<Driver> findByEnterpriseIdIn(List<Integer> enterpriseIds, Pageable pageable);

}
