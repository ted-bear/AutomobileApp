package com.toporkov.automobileapp.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.toporkov.automobileapp.model.Enterprise;
import com.toporkov.automobileapp.model.Manager;
import com.toporkov.automobileapp.repository.EnterpriseRepository;
import com.toporkov.automobileapp.util.exception.EnterpriseNotFoundException;
import com.toporkov.automobileapp.web.dto.domain.enterprise.TimezoneDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@Transactional(readOnly = true)
public class EnterpriseService {

    private final EnterpriseRepository enterpriseRepository;
    private final ManagerService managerService;

    public EnterpriseService(
        final EnterpriseRepository enterpriseRepository,
        final ManagerService managerService
    ) {
        this.enterpriseRepository = enterpriseRepository;
        this.managerService = managerService;
    }

    public List<Enterprise> findAll() {
        final Manager currentManager = managerService.getCurrentManager();
        Assert.notNull(currentManager, "Текущий менеджер не найден");
        return enterpriseRepository.findAllByManagersContains(currentManager);
    }

    public Enterprise getById(UUID enterpriseId) {
        Assert.notNull(enterpriseId, "ID предприятия не может быть null");
        return findById(enterpriseId)
            .orElseThrow(() -> new EnterpriseNotFoundException("Предприятие с ID " + enterpriseId + " не найдено"));
    }

    @Transactional
    public void save(Enterprise enterprise) {
        Assert.notNull(enterprise, "Объект предприятия не может быть null");

        if (enterprise.getTimezone() == null) {
            enterprise.setTimezone("UTC");
        }

        enterprise.setActive(true);
        enterpriseRepository.save(enterprise);
    }

    @Transactional
    public void updateTimezone(Integer id, TimezoneDTO timezoneDTO) {
        Assert.notNull(id, "ID предприятия не может быть null");
        Assert.notNull(timezoneDTO, "Временная зона не может быть null");

        enterpriseRepository
            .findById(id)
            .ifPresent(enterprise -> enterprise.setTimezone(timezoneDTO.getTimezone()));
    }

    @Transactional
    public void update(UUID id, Enterprise enterprise) {
        Assert.notNull(id, "ID предприятия не может быть null");
        Assert.notNull(enterprise, "Объект предприятия не может быть null");

        enterprise.setId(id);
        enterprise.setActive(true);
        enterpriseRepository.save(enterprise);
    }

    @Transactional
    public void delete(UUID id) {
        Assert.notNull(id, "ID предприятия не может быть null");
        findById(id)
            .ifPresent(enterprise -> {
                enterprise.setActive(false);
                enterpriseRepository.save(enterprise);
            });
    }

    private Optional<Enterprise> findById(UUID id) {
        return enterpriseRepository.findById(id);
    }
}
