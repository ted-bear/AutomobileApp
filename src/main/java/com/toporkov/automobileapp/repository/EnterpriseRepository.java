package com.toporkov.automobileapp.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.toporkov.automobileapp.model.Enterprise;
import com.toporkov.automobileapp.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnterpriseRepository extends JpaRepository<Enterprise, Integer> {

    List<Enterprise> findAllByManagersContains(Manager manager);

    Optional<Enterprise> findById(UUID id);

}
