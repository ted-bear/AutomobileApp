package com.toporkov.automobileapp.web.rest;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.toporkov.automobileapp.service.EnterpriseImportService;
import com.toporkov.automobileapp.service.EnterpriseService;
import com.toporkov.automobileapp.web.dto.domain.ImportResult;
import com.toporkov.automobileapp.web.dto.domain.enterprise.EnterpriseCsvDTO;
import com.toporkov.automobileapp.web.dto.domain.enterprise.EnterpriseDTO;
import com.toporkov.automobileapp.web.dto.domain.enterprise.EnterpriseListDTO;
import com.toporkov.automobileapp.web.dto.domain.enterprise.TimezoneDTO;
import com.toporkov.automobileapp.web.dto.domain.vehicle.VehicleCsvDTO;
import com.toporkov.automobileapp.web.dto.validation.OnCreate;
import com.toporkov.automobileapp.web.dto.validation.OnUpdate;
import com.toporkov.automobileapp.web.mapper.EnterpriseMapper;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.UnsupportedMediaTypeException;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("/api/v1/enterprises")
public class EnterpriseRestController {

    private final static String EXPECTED_FILE_TYPE = "text/csv";

    private final EnterpriseService enterpriseService;
    private final EnterpriseMapper enterpriseMapper;
    private final EnterpriseImportService importService;

    public EnterpriseRestController(
        final EnterpriseService enterpriseService,
        final EnterpriseMapper enterpriseMapper,
        final EnterpriseImportService importService
    ) {
        this.enterpriseService = enterpriseService;
        this.enterpriseMapper = enterpriseMapper;
        this.importService = importService;
    }

    @GetMapping
    public EnterpriseListDTO getEnterprises() {
        // TODO: Перенести маппинг в слой контроллера
        final var enterprises = enterpriseService.findAll();
        final var enterpriseDTOList = enterprises.stream()
            .map(enterpriseMapper::mapEntityToDto)
            .toList();
        return new EnterpriseListDTO(enterpriseDTOList);
    }

    @GetMapping("/export-csv")
    public void exportEnterprises(HttpServletResponse response)
        throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        var filename = "enterprises.csv";

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename= \"" + filename + "\"");

        var writer = new StatefulBeanToCsvBuilder<EnterpriseDTO>(response.getWriter())
            .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
            .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
            .withOrderedResults(false)
            .build();

        writer.write(enterpriseService.findAll()
            .stream()
            .map(enterpriseMapper::mapEntityToDto)
            .toList()
        );
    }

    @PostMapping(value = "/import", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImportResult> importEnterprises(
        @RequestParam("file") @Valid MultipartFile file
    ) throws IOException {

        if (file.isEmpty()) {
            throw new BadRequestException("File is empty");
        }

        if (!EXPECTED_FILE_TYPE.equals(file.getContentType())) {
            throw new UnsupportedMediaTypeException("Only CSV files are supported");
        }

        var enterprises = new CsvToBeanBuilder<EnterpriseCsvDTO>(
            new InputStreamReader(file.getInputStream()))
            .withType(EnterpriseCsvDTO.class)
            .withThrowExceptions(true)
            .build()
            .parse();

        var result = importService.importEnterprises(enterprises, Default.class, OnCreate.class);
        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "/vehicles/import", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImportResult> importEnterprisesAndVehicles(
        @RequestParam("enterprises") MultipartFile enterprisesFile,
        @RequestParam("vehicles") MultipartFile vehiclesFile
    ) throws IOException {

        if (enterprisesFile.isEmpty() || vehiclesFile.isEmpty()) {
            throw new BadRequestException("File is empty");
        }

        var isEnterpriseFileContainsCsv = EXPECTED_FILE_TYPE.equals(enterprisesFile.getContentType());
        var isVehicleFileContainsCsv = EXPECTED_FILE_TYPE.equals(vehiclesFile.getContentType());

        if (!isEnterpriseFileContainsCsv || !isVehicleFileContainsCsv) {
            throw new UnsupportedMediaTypeException("Only CSV files are supported");
        }

        var enterprises = new CsvToBeanBuilder<EnterpriseCsvDTO>(
            new InputStreamReader(enterprisesFile.getInputStream()))
            .withType(EnterpriseCsvDTO.class)
            .withThrowExceptions(true)
            .build()
            .parse();

        var vehicles = new CsvToBeanBuilder<VehicleCsvDTO>(
            new InputStreamReader(vehiclesFile.getInputStream()))
            .withType(VehicleCsvDTO.class)
            .withThrowExceptions(true)
            .build()
            .parse();

        var importResult =
            importService.importEnterprisesAndVehicles(enterprises, vehicles, Default.class, OnCreate.class);

        return ResponseEntity.ok(importResult);
    }

    @GetMapping("/{id}")
    public EnterpriseDTO getEnterprise(@PathVariable("id") UUID id) {
        // TODO: Перенести маппинг в слой контроллера
        final var enterprise = enterpriseService.getById(id);
        return enterpriseMapper.mapEntityToDto(enterprise);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> updateTimezone(
        @PathVariable("id") Integer id,
        @RequestBody TimezoneDTO timezoneDTO
    ) {
        // TODO: Перенести маппинг в слой контроллера
        enterpriseService.updateTimezone(id, timezoneDTO);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createEnterprise(
        @Validated(OnCreate.class)
        @RequestBody EnterpriseDTO enterpriseDTO
    ) {
        // TODO: Перенести маппинг в слой контроллера
        enterpriseService.save(enterpriseMapper.mapDtoToEntity(enterpriseDTO));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateEnterprise(
        @PathVariable("id") UUID id,
        @Validated(OnUpdate.class)
        @RequestBody EnterpriseDTO enterpriseDTO
    ) {
        // TODO: Перенести маппинг в слой контроллера
        enterpriseService.update(id, enterpriseMapper.mapDtoToEntity(enterpriseDTO));
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteEnterprise(@PathVariable("id") UUID id) {
        enterpriseService.delete(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
