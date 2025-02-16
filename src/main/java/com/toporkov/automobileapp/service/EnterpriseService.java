package com.toporkov.automobileapp.service;

import com.toporkov.automobileapp.model.Enterprise;
import com.toporkov.automobileapp.repository.EnterpriseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class EnterpriseService {

    private final EnterpriseRepository enterpriseRepository;

    public EnterpriseService(EnterpriseRepository enterpriseRepository) {
        this.enterpriseRepository = enterpriseRepository;
    }

    public List<Enterprise> findAll() {
        return enterpriseRepository.findAll();
    }
}
