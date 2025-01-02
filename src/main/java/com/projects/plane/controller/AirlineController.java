package com.projects.plane.controller;

import com.projects.plane.dto.airline.AirlineRequestDto;
import com.projects.plane.dto.airline.AirlineResponseDto;
import com.projects.plane.service.AirlineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/airline")
@Tag(
        name = "Airline",
        description = "Endpoints for managing airline data. This entity represents generic airline models, including details such as IATA and ICAO codes, country, headquarters, operating status, and the founding year."
)
public class AirlineController {

    private AirlineService airlineService;

    @PostMapping
    @Operation(summary = "Create a new airline")
    public ResponseEntity<AirlineResponseDto> createAirline(@Valid @RequestBody AirlineRequestDto airlineRequestDto) {
        AirlineResponseDto savedAirline = airlineService.createAirline(airlineRequestDto);
        return new ResponseEntity<>(savedAirline, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing airline")
    public ResponseEntity<AirlineResponseDto> updateAirline(@PathVariable("id") UUID id, @RequestBody AirlineRequestDto airlineRequestDto) {
        AirlineResponseDto updatedAirline = airlineService.updateAirline(id, airlineRequestDto);
        return new ResponseEntity<>(updatedAirline, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an existing airline by ID")
    public ResponseEntity<AirlineResponseDto> getAirlineById(@PathVariable("id") UUID id) {
        AirlineResponseDto airline = airlineService.getAirlineById(id);
        return new ResponseEntity<>(airline, HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Get all airlines")
    public ResponseEntity<List<AirlineResponseDto>> getAllAirlines() {
        List<AirlineResponseDto> airlines = airlineService.getAllAirlines();
        return new ResponseEntity<>(airlines, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an existing airline")
    public ResponseEntity<Void> deleteAirline(@PathVariable("id") UUID id) {
        airlineService.deleteAirline(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{airlineId}/add-hub/{airportId}")
    @Operation(summary = "Add an airport as a hub for an airline")
    public ResponseEntity<AirlineResponseDto> addHubToAirline(@PathVariable("airlineId") UUID airlineId, @PathVariable("airportId") UUID airportId) {
        AirlineResponseDto updatedAirline = airlineService.addHubToAirline(airlineId, airportId);
        return new ResponseEntity<>(updatedAirline, HttpStatus.OK);
    }

}
