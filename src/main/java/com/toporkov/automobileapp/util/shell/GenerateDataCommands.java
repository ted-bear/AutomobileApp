package com.toporkov.automobileapp.util.shell;

import java.util.List;
import java.util.UUID;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class GenerateDataCommands {

    private final GenerateDataService generateDataService;

    public GenerateDataCommands(
        final GenerateDataService generateDataService
    ) {
        this.generateDataService = generateDataService;
    }

    @ShellMethod(key = "generate-data",
        value = "Generate N random vehicles and drivers for specific enterprise.")
    public String generateVehiclesAndDrivers(
        @ShellOption(defaultValue = "1") Integer number,
        @ShellOption(defaultValue = "") List<UUID> enterpriseIds
    ) {
        generateDataService.generateVehiclesAndDrivers(number, enterpriseIds);
        return "Generation is successful";
    }

    //      long      latitude
//    30.274033, 60.007767
//    30.295577, 60.006566
//    30.285320, 60.014610
//    30.285062, 60.003777
    @ShellMethod(key = "generate-track",
        value = "Generate track for the vehicle.")
    public void generateTrackForVehicle(
        @ShellOption final Double minLatitude,
        @ShellOption final Double maxLatitude,
        @ShellOption final Double minLongitude,
        @ShellOption final Double maxLongitude
    ) {
        generateDataService.generateTrack(minLatitude, maxLatitude, minLongitude, maxLongitude);
    }

}
