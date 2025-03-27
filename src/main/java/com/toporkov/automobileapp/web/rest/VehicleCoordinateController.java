package com.toporkov.automobileapp.web.rest;

import com.toporkov.automobileapp.service.VehicleCoordinateService;
import com.toporkov.automobileapp.web.dto.domain.VehicleCoordinateDTO;
import com.toporkov.automobileapp.web.dto.domain.VehicleCoordinateGeoJson;
import com.toporkov.automobileapp.web.mapper.VehicleCoordinateMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.List;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME;

@RestController
@RequestMapping("/api/v1/coordinates")
public class VehicleCoordinateController {

    private final VehicleCoordinateService vehicleCoordinateService;
    private final VehicleCoordinateMapper vehicleCoordinateMapper;

    public VehicleCoordinateController(final VehicleCoordinateService vehicleCoordinateService,
                                       final VehicleCoordinateMapper vehicleCoordinateMapper) {
        this.vehicleCoordinateService = vehicleCoordinateService;
        this.vehicleCoordinateMapper = vehicleCoordinateMapper;
    }

    @GetMapping("/{id}")
    public List<VehicleCoordinateDTO>
    findAllVehicleCoordinates(@PathVariable("id") int vehicleId,
                              @RequestParam @DateTimeFormat(iso = DATE_TIME) ZonedDateTime start,
                              @RequestParam @DateTimeFormat(iso = DATE_TIME) ZonedDateTime stop) {
        return vehicleCoordinateService.findAllByTime(vehicleId, start.toInstant(), stop.toInstant())
                .stream()
                .map(vehicleCoordinateMapper::mapEntityToDTO)
                .toList();
    }

    @GetMapping(value = "/geo/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FeatureCollection
    findAllVehicleGeoCoordinates(@PathVariable("id") int vehicleId,
                                 @RequestParam @DateTimeFormat(iso = DATE_TIME) ZonedDateTime start,
                                 @RequestParam @DateTimeFormat(iso = DATE_TIME) ZonedDateTime stop) {

        var coordinates = vehicleCoordinateService.findAllByTime(vehicleId, start.toInstant(), stop.toInstant());
        List<VehicleCoordinateGeoJson> features = coordinates.stream()
                .map(VehicleCoordinateGeoJson::fromEntity)
                .toList();

        FeatureCollection featureCollection = new FeatureCollection();
        featureCollection.setFeatures(features);
        return featureCollection;
    }

    private static class FeatureCollection {
        private final String type = "FeatureCollection";
        private List<VehicleCoordinateGeoJson> features;

        public String getType() {
            return type;
        }

        public List<VehicleCoordinateGeoJson> getFeatures() {
            return features;
        }

        public void setFeatures(List<VehicleCoordinateGeoJson> features) {
            this.features = features;
        }
    }
}
