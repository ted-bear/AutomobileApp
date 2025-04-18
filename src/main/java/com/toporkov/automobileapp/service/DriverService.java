package com.toporkov.automobileapp.service;

import java.util.List;
import java.util.UUID;

import com.toporkov.automobileapp.model.Driver;
import com.toporkov.automobileapp.model.Enterprise;
import com.toporkov.automobileapp.repository.DriverRepository;
import com.toporkov.automobileapp.util.exception.DriverNotFoundException;
import com.toporkov.automobileapp.web.dto.domain.driver.DriverDTO;
import com.toporkov.automobileapp.web.mapper.DriverMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@Transactional(readOnly = true)
public class DriverService {

    private final DriverRepository driverRepository;
    private final ManagerService managerService;
    private final DriverMapper driverMapper;

    public DriverService(
        final DriverRepository driverRepository,
        final ManagerService managerService,
        final DriverMapper driverMapper
    ) {
        this.driverRepository = driverRepository;
        this.managerService = managerService;
        this.driverMapper = driverMapper;
    }

    public Page<DriverDTO> findAll(Pageable pageable) {
        final List<UUID> enterpriseIds = managerService.getCurrentManager()
            .getEnterprises()
            .stream()
            .map(Enterprise::getId)
            .toList();

        return driverRepository
            .findByEnterpriseIdIn(enterpriseIds, pageable)
            .map(driverMapper::mapEntityToDto);
    }


    public Driver getById(Integer id) {
        Assert.notNull(id, "Driver id shouldn't be null");

        return driverRepository.findById(id).orElseThrow(DriverNotFoundException::new);
    }
}
