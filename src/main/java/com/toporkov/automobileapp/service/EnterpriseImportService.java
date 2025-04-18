package com.toporkov.automobileapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.toporkov.automobileapp.model.Enterprise;
import com.toporkov.automobileapp.repository.EnterpriseRepository;
import com.toporkov.automobileapp.web.dto.domain.ImportResult;
import com.toporkov.automobileapp.web.dto.domain.enterprise.EnterpriseCsvDTO;
import com.toporkov.automobileapp.web.mapper.EnterpriseMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnterpriseImportService {

    private final EnterpriseRepository enterpriseRepository;
    private final EnterpriseMapper mapper;
    private final Validator validator;

    @Transactional
    public ImportResult importEnterprises(List<EnterpriseCsvDTO> csvDtos, Class<?>... groups) {
        List<Enterprise> validEnterprises = new ArrayList<>();
        List<String> errors = new ArrayList<>();

        for (int i = 0; i < csvDtos.size(); i++) {
            EnterpriseCsvDTO dto = csvDtos.get(i);
            Set<ConstraintViolation<EnterpriseCsvDTO>> violations = validator.validate(dto, groups);

            if (violations.isEmpty()) {
                Enterprise enterprise = mapper.mapCsvDtoToEntity(dto);
                enterprise.setActive(true);
                validEnterprises.add(enterprise);
            } else {
                errors.add(String.format("Line %d: %s", i + 1,
                    violations.stream()
                        .map(ConstraintViolation::getMessage)
                        .collect(Collectors.joining(", "))));
            }
        }

        List<Enterprise> saved = enterpriseRepository.saveAll(validEnterprises);

        return new ImportResult(
            saved.size(),
            errors.size(),
            errors
        );
    }
}
