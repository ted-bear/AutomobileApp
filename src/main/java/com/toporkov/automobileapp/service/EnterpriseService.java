package com.toporkov.automobileapp.service;

import com.toporkov.automobileapp.model.Enterprise;
import com.toporkov.automobileapp.model.Manager;
import com.toporkov.automobileapp.repository.EnterpriseRepository;
import com.toporkov.automobileapp.util.exception.EnterpriseNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class EnterpriseService {

    private final EnterpriseRepository enterpriseRepository;
    private final ManagerService managerService;

    public EnterpriseService(final EnterpriseRepository enterpriseRepository,
                             final ManagerService managerService) {
        this.enterpriseRepository = enterpriseRepository;
        this.managerService = managerService;
    }

    public List<Enterprise> findAll() {
        final Manager currentManager = managerService.getCurrentManager();
        return enterpriseRepository.findAllByManagersContains(currentManager);
    }

    public Enterprise getById(Integer enterpriseId) {
        Assert.notNull(enterpriseId, "enterpriseId argument is null");

        return enterpriseRepository.findById(enterpriseId)
                .orElseThrow(EnterpriseNotFoundException::new);
    }

    @Transactional
    public void save(Enterprise enterprise) {
        enterprise.setActive(true);
        enterpriseRepository.save(enterprise);
    }

    @Transactional
    public void update(Integer id, Enterprise enterprise) {
        enterprise.setId(id);
        enterprise.setActive(true);
        enterpriseRepository.save(enterprise);
    }

    @Transactional
    public void delete(Integer id) {
        enterpriseRepository.findById(id).ifPresent(enterprise -> enterprise.setActive(false));
    }
}
