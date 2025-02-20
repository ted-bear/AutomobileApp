package com.toporkov.automobileapp.web.rest;

import com.toporkov.automobileapp.model.Enterprise;
import com.toporkov.automobileapp.service.EnterpriseService;
import com.toporkov.automobileapp.web.dto.EnterpriseDTO;
import com.toporkov.automobileapp.web.dto.EnterpriseListDTO;
import com.toporkov.automobileapp.web.mapper.EnterpriseMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/enterprises")
public class EnterpriseRestController {

    private final EnterpriseService enterpriseService;
    private final EnterpriseMapper enterpriseMapper;

    public EnterpriseRestController(final EnterpriseService enterpriseService,
                                    final EnterpriseMapper enterpriseMapper) {
        this.enterpriseService = enterpriseService;
        this.enterpriseMapper = enterpriseMapper;
    }

    @GetMapping
    public EnterpriseListDTO getAllDrivers() {
        final List<Enterprise> allDrivers = enterpriseService.findAll();
        final List<EnterpriseDTO> enterpriseDTOList = allDrivers.stream()
                .map(enterpriseMapper::mapEntityToDto)
                .toList();
        return new EnterpriseListDTO(enterpriseDTOList);
    }

    @GetMapping("/{id}")
    public EnterpriseDTO getEnterprise(@PathVariable("id") Integer id) {
        final Enterprise enterprise = enterpriseService.getById(id);
        return enterpriseMapper.mapEntityToDto(enterprise);
    }
}
