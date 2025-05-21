package com.toporkov.automobileapp.web.rest;

import java.time.ZonedDateTime;
import java.util.UUID;

import com.toporkov.automobileapp.model.Report;
import com.toporkov.automobileapp.service.MileageReportService;
import com.toporkov.automobileapp.web.dto.domain.ReportDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
public class ReportRestController {

    private final MileageReportService reportService;

    @PostMapping("/mileage")
    public ResponseEntity<ReportDTO> generateMileageReport(
        @RequestParam UUID vehicleId,
        @RequestParam Report.PeriodType periodType,
        @RequestParam @DateTimeFormat(iso = DATE_TIME) ZonedDateTime start,
        @RequestParam @DateTimeFormat(iso = DATE_TIME) ZonedDateTime end
    ) {

        Report report =
            reportService.generateMileageReport(vehicleId, periodType, start.toLocalDateTime(), end.toLocalDateTime());
        return ResponseEntity.ok(mapToDto(report, "MILEAGE"));
    }

    @PostMapping("/trips")
    public ResponseEntity<ReportDTO> generateTripsReport(
        @RequestParam UUID vehicleId,
        @RequestParam Report.PeriodType periodType,
        @RequestParam @DateTimeFormat(iso = DATE_TIME) ZonedDateTime start,
        @RequestParam @DateTimeFormat(iso = DATE_TIME) ZonedDateTime end
    ) {

        Report report =
            reportService.generateTripsReport(vehicleId, periodType, start.toLocalDateTime(), end.toLocalDateTime());
        return ResponseEntity.ok(mapToDto(report, "TRIPS"));
    }

    private ReportDTO mapToDto(Report report, String reportType) {
        ReportDTO dto = new ReportDTO();
        dto.setName(report.getName());
        dto.setReportType(reportType);
        dto.setPeriodType(report.getPeriodType());
        dto.setStartDate(report.getStartDate());
        dto.setEndDate(report.getStopDate());
        dto.setGeneratedAt(report.getGeneratedAt());
        dto.setResults(report.getResults());
        return dto;
    }
}
