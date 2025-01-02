package com.projects.plane.model;

import com.projects.plane.model.enums.FlightStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
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

    @NotNull(message = "Aircraft cannot be null.")
    @ManyToOne
    @JoinColumn(name = "aircraft_id", nullable = false)
    private Aircraft aircraft;

    @NotNull(message = "Departure airport cannot be null.")
    @ManyToOne
    @JoinColumn(name = "departure_airport_id", nullable = false)
    private Airport departureAirport;

    @NotNull(message = "Arrival airport cannot be null.")
    @ManyToOne
    @JoinColumn(name = "arrival_airport_id", nullable = false)
    private Airport arrivalAirport;

    @NotNull(message = "Flight number cannot be null.")
    @Size(min = 2, max = 10, message = "Flight number should be between 2 and 10 characters.")
    @Column(name = "flight_number")
    private String flightNumber;

    @NotNull(message = "Departure time cannot be null.")
    @Future(message = "Departure time must be in the future.")
    @Column(name = "departure_time")
    private LocalDateTime departureDateTime;

    @NotNull(message = "Arrival time cannot be null.")
    @Column(name = "arrival_time")
    private LocalDateTime arrivalDateTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private FlightStatus flightStatus;

}
