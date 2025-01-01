package com.projects.plane.dto.flight;

import com.projects.plane.model.enums.FlightStatus;
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
public class FlightRequestDto {

    @NotNull(message = "Aircraft ID cannot be null")
    private UUID aircraftId;

    @NotNull(message = "Departure airport ID cannot be null")
    private UUID departureAirportId;

    @NotNull(message = "Arrival airport ID cannot be null")
    private UUID arrivalAirportId;

    @NotNull(message = "Arrival airport ID cannot be null")
    private String flightNumber;

    @NotNull(message = "Departure date and time cannot be null")
    @Future(message = "Departure time must be in the future")
    private LocalDateTime departureDateTime;

    @NotNull(message = "Arrival date and time cannot be null")
    private LocalDateTime arrivalDateTime;

    @NotNull(message = "Flight status cannot be null")
    private FlightStatus flightStatus;
}
