package com.toporkov.automobileapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
@Entity
@Table(name = "trip")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "started_at")
    private Instant startedAt;

    @Column(name = "ended_at")
    private Instant endedAt;

    @ManyToOne
    @JoinColumn(name = "vehicle_id",
        referencedColumnName = "id")
    private Vehicle vehicle;

    public Trip() {
    }

    public Trip(
        final Instant startedAt,
        final Instant endedAt,
        final Vehicle vehicle
    ) {
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.vehicle = vehicle;
    }
}
