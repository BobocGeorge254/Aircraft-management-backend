package com.projects.plane.controller;

import com.projects.plane.dto.aircraft.AircraftRequestDto;
import com.projects.plane.dto.aircraft.AircraftResponseDto;
import com.projects.plane.service.AircraftService;
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
@RequestMapping("/api/aircraft")
@Tag(name = "Aircraft", description = "Endpoints for managing aircraft data.")
public class AircraftController {

    private AircraftService aircraftService;

    @PostMapping
    @Operation(summary = "Create a new aircraft")
    public ResponseEntity<AircraftResponseDto> createAircraft(@RequestBody AircraftRequestDto dto) {
        AircraftResponseDto savedAircraft = aircraftService.createAircraft(dto);
        return new ResponseEntity<>(savedAircraft, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing aircraft")
    public ResponseEntity<AircraftResponseDto> updateAircraft(@PathVariable("id") UUID id, @RequestBody AircraftRequestDto dto) {
        AircraftResponseDto updatedAircraft = aircraftService.updateAircraft(id, dto);
        return new ResponseEntity<>(updatedAircraft, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an aircraft by ID")
    public ResponseEntity<AircraftResponseDto> getAircraftById(@PathVariable("id") UUID id) {
        AircraftResponseDto aircraft = aircraftService.getAircraftById(id);
        return new ResponseEntity<>(aircraft, HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Get all aircraft")
    public ResponseEntity<List<AircraftResponseDto>> getAllAircraft(
            @RequestParam(value = "airplaneId", required = false) UUID airplaneId,
            @RequestParam(value = "airlineId", required = false) UUID airlineId
    ) {
        List<AircraftResponseDto> aircraft = aircraftService.getAllAircraft(airplaneId, airlineId);
        return new ResponseEntity<>(aircraft, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an aircraft")
    public ResponseEntity<Void> deleteAircraft(@PathVariable("id") UUID id) {
        aircraftService.deleteAircraft(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
