package com.projects.plane.controller;

import com.projects.plane.dto.flight.FlightRequestDto;
import com.projects.plane.dto.flight.FlightResponseDto;
import com.projects.plane.service.FlightService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/flights")
@Tag(name = "Flights", description = "Endpoints for managing flights data.")
public class FlightController {

    private FlightService flightService;

    @PostMapping
    @Operation(summary = "Create a new flight")
    public ResponseEntity<FlightResponseDto> createFlight(@RequestBody FlightRequestDto flightRequestDto) {
        FlightResponseDto savedFlight = flightService.createFlight(flightRequestDto);
        return new ResponseEntity<>(savedFlight, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing flight")
    public ResponseEntity<FlightResponseDto> updateFlight(@PathVariable("id") UUID id, @RequestBody FlightRequestDto flightRequestDto) {
        FlightResponseDto savedFlight = flightService.updateFlight(id, flightRequestDto);
        return new ResponseEntity<>(savedFlight, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get flight by ID")
    public ResponseEntity<FlightResponseDto> getFlightById(@PathVariable("id") UUID id) {
        FlightResponseDto flight = flightService.getFlightById(id);
        return new ResponseEntity<> (flight, HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Get all flights")
    public ResponseEntity<List<FlightResponseDto>> getAllFlights(
            @RequestParam(required = false) UUID aircraftId,
            @RequestParam(required = false) UUID departureAirportId,
            @RequestParam(required = false) UUID arrivalAirportId) {
        List<FlightResponseDto> flights = flightService.getAllFlights(aircraftId, departureAirportId, arrivalAirportId);
        return new ResponseEntity<> (flights, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an existing flight")
    public ResponseEntity<Void> deleteFlight(@PathVariable("id") UUID id) {
        flightService.deleteFlight(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
