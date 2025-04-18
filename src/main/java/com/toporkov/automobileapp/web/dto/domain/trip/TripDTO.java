package com.toporkov.automobileapp.web.dto.domain.trip;

import java.time.Instant;
import java.util.UUID;

public class TripDTO {

    private Integer id;

    private Instant startedAt;

    private Instant endedAt;

    private UUID vehicleId;

    private Long startCoordinateId;

    private Long endCoordinateId;

    private String startAddress;

    private String endAddress;

    public TripDTO() {
    }

    public TripDTO(
        final Integer id,
        final Instant startedAt,
        final Instant endedAt,
        final UUID vehicleId,
        final Long startCoordinateId,
        final Long endCoordinateId,
        final String startAddress,
        final String endAddress
    ) {
        this.id = id;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.vehicleId = vehicleId;
        this.startCoordinateId = startCoordinateId;
        this.endCoordinateId = endCoordinateId;
        this.startAddress = startAddress;
        this.endAddress = endAddress;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Instant getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Instant startedAt) {
        this.startedAt = startedAt;
    }

    public Instant getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(Instant endedAt) {
        this.endedAt = endedAt;
    }

    public UUID getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(UUID vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Long getStartCoordinateId() {
        return startCoordinateId;
    }

    public void setStartCoordinateId(Long startCoordinateId) {
        this.startCoordinateId = startCoordinateId;
    }

    public Long getEndCoordinateId() {
        return endCoordinateId;
    }

    public void setEndCoordinateId(Long endCoordinateId) {
        this.endCoordinateId = endCoordinateId;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }
}
