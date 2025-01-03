package com.projects.plane.controller;

import com.projects.plane.dto.airplane.AirplaneRequestDto;
import com.projects.plane.dto.airplane.AirplaneResponseDto;
import com.projects.plane.service.AirplaneService;
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
@RequestMapping("/api/airplane")
@Tag(
        name = "Airplane",
        description = "Endpoints for managing airplane data. This entity represents generic airplane models, including details such as manufacturer, model, seating capacity, maximum takeoff weight (in kgs), and range (in kms). It is not related to specific airplanes currently being managed by the application. The specific airplanes (i.e., the actual aircraft being managed, tracked, and operated within the application) are represented within the 'Aircraft' entity, which holds more detailed and operational-specific information related to each aircraft instance."
)
public class AirplaneController {
    private AirplaneService airplaneService;

    @PostMapping
    @Operation(summary = "Create a new airplane.")
    public ResponseEntity<AirplaneResponseDto> createAirplane(@Valid @RequestBody AirplaneRequestDto airplaneRequestDto) {
        AirplaneResponseDto savedAirplane =  airplaneService.createAirplane(airplaneRequestDto);
        return new ResponseEntity<> (savedAirplane, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing airplane.")
    public ResponseEntity<AirplaneResponseDto> updateAirplane(@PathVariable("id") UUID id, @Valid @RequestBody AirplaneRequestDto airplaneRequestDto) {
        AirplaneResponseDto savedAirplane = airplaneService.updateAirplane(id, airplaneRequestDto);
        return new ResponseEntity<> (savedAirplane, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an existing airplane.")
    public ResponseEntity<AirplaneResponseDto> getAirplaneById(@PathVariable("id") UUID id) {
        AirplaneResponseDto airplane = airplaneService.getAirplaneById(id);
        return new ResponseEntity<> (airplane, HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Get all airplanes.")
    public ResponseEntity<List<AirplaneResponseDto>> getAllAirplanes() {
        List<AirplaneResponseDto> airplanes = airplaneService.getAllAirplanes();
        return new ResponseEntity<>(airplanes, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an existing airplane.")
    public ResponseEntity<Void> deleteAirplane(@PathVariable("id") UUID id) {
        airplaneService.deleteAirplane(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
