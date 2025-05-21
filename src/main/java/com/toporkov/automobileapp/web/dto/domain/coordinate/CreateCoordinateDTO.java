package com.toporkov.automobileapp.web.dto.domain.coordinate;

import java.time.Instant;

public class CreateCoordinateDTO {

    private Instant createAt;

    private Double latitude;

    private Double longitude;

    private String vehicleId;

    public CreateCoordinateDTO() {
    }

    public CreateCoordinateDTO(
        Instant createAt,
        Double latitude,
        Double longitude,
        String vehicleId
    ) {
        this.createAt = createAt;
        this.latitude = latitude;
        this.longitude = longitude;
        this.vehicleId = vehicleId;
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Instant createAt) {
        this.createAt = createAt;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }
}
