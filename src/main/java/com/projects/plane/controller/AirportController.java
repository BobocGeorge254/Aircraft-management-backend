package com.projects.plane.controller;

import com.projects.plane.dto.airport.AirportRequestDto;
import com.projects.plane.dto.airport.AirportResponseDto;
import com.projects.plane.service.AirportService;
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
@RequestMapping("/api/airport")
@Tag(
        name = "Airport",
        description = "Endpoints for managing airport data. This entity represents generic airport models, including details such as name, location, ICAO code, IATA code, and runway specifications."
)
public class AirportController {

    private AirportService airportService;

    @PostMapping
    @Operation(summary = "Create a new airport")
    public ResponseEntity<AirportResponseDto> createAirport(@Valid @RequestBody AirportRequestDto airportRequestDto) {
        AirportResponseDto savedAirport = airportService.createAirport(airportRequestDto);
        return new ResponseEntity<>(savedAirport, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing airport")
    public ResponseEntity<AirportResponseDto> updateAirport(@PathVariable("id") UUID id, @RequestBody AirportRequestDto airportRequestDto) {
        AirportResponseDto updatedAirport = airportService.updateAirport(id, airportRequestDto);
        return new ResponseEntity<>(updatedAirport, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an existing airport by ID")
    public ResponseEntity<AirportResponseDto> getAirportById(@PathVariable("id") UUID id) {
        AirportResponseDto airport = airportService.getAirportById(id);
        return new ResponseEntity<>(airport, HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Get all airports")
    public ResponseEntity<List<AirportResponseDto>> getAllAirports() {
        List<AirportResponseDto> airports = airportService.getAllAirports();
        return new ResponseEntity<>(airports, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an existing airport")
    public ResponseEntity<Void> deleteAirport(@PathVariable("id") UUID id) {
        airportService.deleteAirport(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
