package com.toporkov.automobileapp.web.rest;

import java.util.List;
import java.util.UUID;

import com.toporkov.automobileapp.util.shell.GenerateDataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/generate")
public class GenerateDataRestController {

    private final GenerateDataService generateDataService;

    public GenerateDataRestController(final GenerateDataService generateDataService) {
        this.generateDataService = generateDataService;
    }

    @GetMapping("/automobile")
    public void generateAutomobiles(
        @RequestParam(value = "enterpriseId", defaultValue = "d19a4311-24b5-4938-b90f-7961733a1f66")
        String enterpriseId,
        @RequestParam("number") int number
    ) {
        generateDataService.generateVehiclesAndDrivers(number, List.of(UUID.fromString(enterpriseId)));
    }
}
