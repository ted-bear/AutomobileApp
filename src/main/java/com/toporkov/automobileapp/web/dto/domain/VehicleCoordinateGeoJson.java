package com.toporkov.automobileapp.web.dto.domain;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.toporkov.automobileapp.model.VehicleCoordinate;

public class VehicleCoordinateGeoJson {
    private String type = "Feature";

    @JsonProperty("geometry")
    private Point coordinate;

    private Properties properties;

    public static VehicleCoordinateGeoJson fromEntity(VehicleCoordinate entity) {
        VehicleCoordinateGeoJson dto = new VehicleCoordinateGeoJson();
        dto.setCoordinate(new Point(new Double[] {entity.getLongitude(), entity.getLatitude()}));

        Properties props = new Properties();
        props.setId(entity.getId());
        props.setVehicleId(entity.getVehicle().getId());
        props.setCreateAt(entity.getCreateAt().toString());

        dto.setProperties(props);
        return dto;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Point getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Point coordinate) {
        this.coordinate = coordinate;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    static class Point {
        private String type = "Point";
        private Double[] coordinates;

        public Point(Double[] coordinates) {
            this.coordinates = coordinates;
        }

        public Double[] getCoordinates() {
            return coordinates;
        }

        public void setCoordinates(Double[] coordinates) {
            this.coordinates = coordinates;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    static class Properties {
        private Long id;
        private UUID vehicleId;
        private String createAt;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public UUID getVehicleId() {
            return vehicleId;
        }

        public void setVehicleId(UUID vehicleId) {
            this.vehicleId = vehicleId;
        }

        public String getCreateAt() {
            return createAt;
        }

        public void setCreateAt(String createAt) {
            this.createAt = createAt;
        }
    }
}
