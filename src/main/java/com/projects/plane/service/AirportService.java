package com.projects.plane.service;

import com.projects.plane.dto.airport.AirportRequestDto;
import com.projects.plane.dto.airport.AirportResponseDto;

import java.util.List;
import java.util.UUID;

public interface AirportService {
    AirportResponseDto createAirport(AirportRequestDto airportRequestDto);
    AirportResponseDto updateAirport(UUID id, AirportRequestDto airportRequestDto);
    AirportResponseDto getAirportById(UUID id);
    List<AirportResponseDto> getAllAirports();
    void deleteAirport(UUID id);
}
