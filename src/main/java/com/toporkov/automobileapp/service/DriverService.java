package com.toporkov.automobileapp.service;

import com.toporkov.automobileapp.model.Driver;
import com.toporkov.automobileapp.model.Manager;
import com.toporkov.automobileapp.repository.DriverRepository;
import com.toporkov.automobileapp.util.exception.DriverNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
public class DriverService {

    private final DriverRepository driverRepository;
    private final ManagerService managerService;

    public DriverService(final DriverRepository driverRepository,
                         final ManagerService managerService) {
        this.driverRepository = driverRepository;
        this.managerService = managerService;
    }

    public List<Driver> findAllByManager(Manager manager) {
        final Manager ctxManager = managerService.getById(manager.getId());
        return ctxManager.getEnterprises()
                .stream()
                .flatMap(enterprise -> findAllByEnterprise(enterprise.getId()).stream())
                .toList();
    }

    public List<Driver> findAllByEnterprise(Integer enterpriseId) {
        return driverRepository.findAll()
                .stream()
                .filter(driver -> enterpriseId == null || Objects.equals(driver.getEnterprise().getId(), enterpriseId))
                .toList();
    }

    public Driver getById(Integer id) {
        Assert.notNull(id, "Driver id shouldn't be null");

        return driverRepository.findById(id).orElseThrow(DriverNotFoundException::new);
    }
}
