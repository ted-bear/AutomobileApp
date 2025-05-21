package com.toporkov.automobileapp.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.toporkov.automobileapp.util.CoordinatesUtil;

public class Report {
    private String name;

    private PeriodType periodType;

    private LocalDateTime startDate;

    private LocalDateTime stopDate;

    private LocalDateTime generatedAt;

    private Map<String, Double> results;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PeriodType getPeriodType() {
        return periodType;
    }

    public void setPeriodType(PeriodType periodType) {
        this.periodType = periodType;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getStopDate() {
        return stopDate;
    }

    public void setStopDate(LocalDateTime stopDate) {
        this.stopDate = stopDate;
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

    public void calculateMileageResults(List<VehicleCoordinate> vehicleCoordinates) {
        Map<String, Double> mileageByPeriod = new HashMap<>();

        switch (getPeriodType()) {
            case DAY -> {
                var dates =
                    vehicleCoordinates.stream().map(m -> LocalDate.ofInstant(m.getCreateAt(), ZoneId.systemDefault()))
                        .distinct()
                        .toList();
                dates.forEach(date -> {
                    var coordsByDate = vehicleCoordinates.stream()
                        .filter(m -> LocalDate.ofInstant(m.getCreateAt(), ZoneId.systemDefault()).equals(date))
                        .toList();

                    mileageByPeriod.put(date.toString(), CoordinatesUtil.calculateTotalDistance(coordsByDate));
                });
            }
            case MONTH -> {
                var months = vehicleCoordinates.stream()
                    .map(m -> LocalDateTime.ofInstant(m.getCreateAt(), ZoneId.systemDefault()).getMonth()).toList();
                months.forEach(month -> {
                    var coordsByDate = vehicleCoordinates.stream()
                        .filter(
                            m -> LocalDate.ofInstant(m.getCreateAt(), ZoneId.systemDefault()).getMonth().equals(month))
                        .toList();

                    mileageByPeriod.put(month.toString(), CoordinatesUtil.calculateTotalDistance(coordsByDate));
                });
            }
            case YEAR -> {
                var years = vehicleCoordinates.stream()
                    .map(m -> LocalDateTime.ofInstant(m.getCreateAt(), ZoneId.systemDefault()).getYear()).toList();
                years.forEach(year -> {
                    var coordsByDate = vehicleCoordinates.stream()
                        .filter(m -> LocalDate.ofInstant(m.getCreateAt(), ZoneId.systemDefault()).getYear() == year)
                        .toList();

                    mileageByPeriod.put(year.toString(), CoordinatesUtil.calculateTotalDistance(coordsByDate));
                });
            }
        }

        setResults(mileageByPeriod);
    }

    public void calculateTripsResults(List<Trip> trips) {
        Map<String, Double> mileageByPeriod = new HashMap<>();

        switch (getPeriodType()) {
            case DAY -> {
                var dates =
                    trips.stream().map(m -> LocalDate.ofInstant(m.getStartedAt(), ZoneId.systemDefault())).distinct()
                        .toList();
                dates.forEach(date -> {
                    var coordsByDate = trips.stream()
                        .filter(m -> LocalDate.ofInstant(m.getStartedAt(), ZoneId.systemDefault()).equals(date))
                        .toList();

                    mileageByPeriod.put(date.toString(), coordsByDate.size() * 1.);
                });
            }
            case MONTH -> {
                var dates =
                    trips.stream().map(m -> LocalDate.ofInstant(m.getStartedAt(), ZoneId.systemDefault()).getMonth())
                        .distinct()
                        .toList();
                dates.forEach(date -> {
                    var coordsByDate = trips.stream()
                        .filter(
                            m -> LocalDate.ofInstant(m.getStartedAt(), ZoneId.systemDefault()).getMonth().equals(date))
                        .toList();

                    mileageByPeriod.put(date.toString(), coordsByDate.size() * 1.);
                });
            }
            case YEAR -> {
                var years =
                    trips.stream().map(m -> LocalDate.ofInstant(m.getStartedAt(), ZoneId.systemDefault()).getYear())
                        .distinct()
                        .toList();
                years.forEach(year -> {
                        var coordsByDate = trips.stream()
                            .filter(m -> LocalDate.ofInstant(m.getStartedAt(), ZoneId.systemDefault()).getYear() == year)
                            .toList();

                        mileageByPeriod.put(year.toString(), coordsByDate.size() * 1.);
                    }
                );
            }
        }

        setResults(mileageByPeriod);
    }

    public enum PeriodType {
        DAY, MONTH, YEAR
    }
}
