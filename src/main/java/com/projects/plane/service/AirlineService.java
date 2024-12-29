package com.projects.plane.service;

import com.projects.plane.dto.airline.AirlineRequestDto;
import com.projects.plane.dto.airline.AirlineResponseDto;

import java.util.List;
import java.util.UUID;

public interface AirlineService {
    AirlineResponseDto createAirline(AirlineRequestDto airlineRequestDto);
    AirlineResponseDto updateAirline(UUID id, AirlineRequestDto airlineRequestDto);
    AirlineResponseDto getAirlineById(UUID id);
    List<AirlineResponseDto> getAllAirlines();
    void deleteAirline(UUID id);

    AirlineResponseDto addHubToAirline(UUID airlineId, UUID airportId);
}
