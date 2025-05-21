package com.toporkov.automobileapp.web.dto.domain;

import java.time.LocalDateTime;
import java.util.Map;

import com.toporkov.automobileapp.model.Report;

public class ReportDTO {
    private String name;
    private String reportType;
    private Report.PeriodType periodType;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime generatedAt;
    private Map<String, Double> results;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public Report.PeriodType getPeriodType() {
        return periodType;
    }

    public void setPeriodType(Report.PeriodType periodType) {
        this.periodType = periodType;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(LocalDateTime generatedAt) {
        this.generatedAt = generatedAt;
    }

    public Map<String, Double> getResults() {
        return results;
    }

    public void setResults(Map<String, Double> results) {
        this.results = results;
    }
}
