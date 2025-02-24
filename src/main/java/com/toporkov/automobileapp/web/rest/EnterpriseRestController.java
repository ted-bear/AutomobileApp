package com.toporkov.automobileapp.web.rest;

import com.toporkov.automobileapp.model.Enterprise;
import com.toporkov.automobileapp.service.EnterpriseService;
import com.toporkov.automobileapp.util.exception.EnterpriseNotSaveException;
import com.toporkov.automobileapp.web.dto.domain.enterprise.EnterpriseDTO;
import com.toporkov.automobileapp.web.dto.domain.enterprise.EnterpriseListDTO;
import com.toporkov.automobileapp.web.dto.validation.OnCreate;
import com.toporkov.automobileapp.web.dto.validation.OnUpdate;
import com.toporkov.automobileapp.web.mapper.EnterpriseMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping
    public ResponseEntity<HttpStatus> createEnterprise(@Validated(OnCreate.class)
                                                       @RequestBody EnterpriseDTO enterpriseDTO,
                                                       BindingResult bindingResult) {
        validateEnterpriseDTO(bindingResult);

        enterpriseService.save(enterpriseMapper.mapDtoToEntity(enterpriseDTO));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateEnterprise(@PathVariable("id") Integer id,
                                                       @Validated(OnUpdate.class)
                                                       @RequestBody EnterpriseDTO enterpriseDTO,
                                                       BindingResult bindingResult) {
        validateEnterpriseDTO(bindingResult);

        enterpriseService.update(id, enterpriseMapper.mapDtoToEntity(enterpriseDTO));
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteEnterprise(@PathVariable("id") Integer id) {
        enterpriseService.delete(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    private static void validateEnterpriseDTO(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errBuilder = new StringBuilder();
            final List<FieldError> errs = bindingResult.getFieldErrors();

            errs.forEach(err -> errBuilder
                    .append(err.getField())
                    .append(" - ")
                    .append(err.getDefaultMessage())
                    .append(";\n")
            );

            throw new EnterpriseNotSaveException(errBuilder.toString());
        }
    }
}
