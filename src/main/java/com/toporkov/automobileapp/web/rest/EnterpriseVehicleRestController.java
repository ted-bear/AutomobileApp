package com.toporkov.automobileapp.web.rest;

import java.io.IOException;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.toporkov.automobileapp.service.VehicleService;
import com.toporkov.automobileapp.web.dto.domain.vehicle.VehicleDTO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/enterprise")
public class EnterpriseVehicleRestController {

    private final VehicleService vehicleService;

    public EnterpriseVehicleRestController(final VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping("/{enterpriseId}/vehicles")
    public Page<VehicleDTO> findAllByEnterprise(
            @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "10") Integer size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "true") Boolean ascending,
        @PathVariable("enterpriseId") Integer enterpriseId
    ) {
        final Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        final Pageable pageable = PageRequest.of(page, size, sort);

        return vehicleService.findAllByEnterpriseId(pageable, enterpriseId);
    }

    @GetMapping("/{enterpriseId}/vehicles-export")
    public void exportAllByEnterprise(HttpServletResponse response)
        throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        //set file name and content type
        String filename = "vehicles_by_enterprise.csv";

        response.setContentType("text/csv");
        response.setCharacterEncoding("UTF-8");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=\"" + filename + "\"");

        //create a csv writer
        var writer = new StatefulBeanToCsvBuilder<VehicleDTO>(response.getWriter())
            .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
            .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
            .withOrderedResults(false)
            .build();

        //write all users to csv file
        writer.write(vehicleService.findAll());
    }

}
