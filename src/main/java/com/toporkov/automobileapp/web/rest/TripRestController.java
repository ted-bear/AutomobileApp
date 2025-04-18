package com.toporkov.automobileapp.web.rest;

import java.io.IOException;
import java.io.InputStreamReader;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.toporkov.automobileapp.service.TripImportService;
import com.toporkov.automobileapp.service.TripService;
import com.toporkov.automobileapp.service.VehicleCoordinateService;
import com.toporkov.automobileapp.web.dto.domain.ImportResult;
import com.toporkov.automobileapp.web.dto.domain.VehicleCoordinateDTO;
import com.toporkov.automobileapp.web.dto.domain.trip.TripCsvDTO;
import com.toporkov.automobileapp.web.dto.domain.trip.TripDTO;
import com.toporkov.automobileapp.web.mapper.VehicleCoordinateMapper;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.UnsupportedMediaTypeException;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME;

@RestController
@RequestMapping("/api/v1/trips")
public class TripRestController {

    private final VehicleCoordinateService vehicleCoordinateService;
    private final TripImportService importService;
    private final VehicleCoordinateMapper vehicleCoordinateMapper;
    private final TripService tripService;

    public TripRestController(
        final VehicleCoordinateService vehicleCoordinateService, final TripImportService importService,
        final VehicleCoordinateMapper vehicleCoordinateMapper, final TripService tripService
    ) {
        this.vehicleCoordinateService = vehicleCoordinateService;
        this.importService = importService;
        this.vehicleCoordinateMapper = vehicleCoordinateMapper;
        this.tripService = tripService;
    }

    @GetMapping
    public ResponseEntity<List<VehicleCoordinateDTO>> getTripsCoordinates(
        @RequestParam @DateTimeFormat(iso = DATE_TIME) ZonedDateTime start,
        @RequestParam @DateTimeFormat(iso = DATE_TIME) ZonedDateTime stop
    ) {
        var coordinatesTrips = vehicleCoordinateService.findAllByTrips(start.toInstant(), stop.toInstant());

        return ResponseEntity.ok(coordinatesTrips.stream().map(vehicleCoordinateMapper::mapEntityToDTO).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<TripDTO>> getTrips(
        @PathVariable("id") UUID vehicleId, @RequestParam @DateTimeFormat(iso = DATE_TIME) ZonedDateTime start,
        @RequestParam @DateTimeFormat(iso = DATE_TIME) ZonedDateTime stop
    ) {
        var trips = tripService.findTripsBetween(vehicleId, start.toInstant(), stop.toInstant());

        return ResponseEntity.ok(trips);
    }

    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImportResult> importEnterprises(
        @RequestParam("file") @Valid MultipartFile file
    ) throws IOException {

        if (file.isEmpty()) {
            throw new BadRequestException("File is empty");
        }

        if (!"text/csv".equals(file.getContentType())) {
            throw new UnsupportedMediaTypeException("Only CSV files are supported");
        }

        List<TripCsvDTO> enterprises =
            new CsvToBeanBuilder<TripCsvDTO>(
                new InputStreamReader(file.getInputStream()))
                .withType(TripCsvDTO.class)
                .withThrowExceptions(true)
                .build()
                .parse();

        ImportResult result = importService.importTrips(enterprises);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}/export-csv")
    public void getTripsCsv(
        HttpServletResponse response, @PathVariable("id") UUID vehicleId,
        @RequestParam @DateTimeFormat(iso = DATE_TIME) ZonedDateTime start,
        @RequestParam @DateTimeFormat(iso = DATE_TIME) ZonedDateTime stop
    ) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        String filename = "trips.csv";

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename= \"" + filename + "\"");

        var writer =
            new StatefulBeanToCsvBuilder<TripDTO>(response.getWriter()).withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR).withOrderedResults(false).build();

        writer.write(tripService.findTripsBetween(vehicleId, start.toInstant(), stop.toInstant()));

    }
}
