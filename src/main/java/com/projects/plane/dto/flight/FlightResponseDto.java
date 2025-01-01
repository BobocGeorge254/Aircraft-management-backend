package com.projects.plane.dto.flight;

import com.projects.plane.dto.aircraft.AircraftResponseDto;
import com.projects.plane.dto.airport.FlightAirportDto;
import com.projects.plane.model.enums.FlightStatus;
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
public class FlightResponseDto {
    private UUID id;
    private AircraftResponseDto aircraft;
    private FlightAirportDto departureAirport;
    private FlightAirportDto arrivalAirport;
    private String flightNumber;
    private LocalDateTime departureDateTime;
    private LocalDateTime arrivalDateTime;
    private FlightStatus flightStatus;

}
