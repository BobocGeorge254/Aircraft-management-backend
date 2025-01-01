package com.projects.plane.model;

import com.projects.plane.model.enums.FlightStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "flight")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "aircraft_id", nullable = false)
    private Aircraft aircraft;

    @ManyToOne
    @JoinColumn(name = "departure_airport_id", nullable = false)
    private Airport departureAirport;

    @ManyToOne
    @JoinColumn(name = "arrival_airport_id", nullable = false)
    private Airport arrivalAirport;

    @Column(name = "flight_number")
    private String flightNumber;

    @Column(name = "departure_time")
    private LocalDateTime departureDateTime;

    @Column(name = "arrival_time")
    private LocalDateTime arrivalDateTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private FlightStatus flightStatus;

}
