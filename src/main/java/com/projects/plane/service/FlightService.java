package com.projects.plane.service;

import com.projects.plane.dto.flight.FlightRequestDto;
import com.projects.plane.dto.flight.FlightResponseDto;

import java.util.List;
import java.util.UUID;

public interface FlightService {
    FlightResponseDto createFlight(FlightRequestDto flightRequestDto);
    FlightResponseDto updateFlight(UUID flightId, FlightRequestDto flightRequestDto);
    FlightResponseDto getFlightById(UUID flightId);
    List<FlightResponseDto> getAllFlights(UUID aircraftId, UUID departureAirportId, UUID arrivalAirportId);
    void deleteFlight(UUID flightId);
}
