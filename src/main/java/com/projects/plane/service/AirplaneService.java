package com.projects.plane.service;

import com.projects.plane.dto.airplane.AirplaneRequestDto;
import com.projects.plane.dto.airplane.AirplaneResponseDto;

import java.util.List;
import java.util.UUID;

public interface AirplaneService {
    AirplaneResponseDto createAirplane(AirplaneRequestDto airplaneRequestDto);
    AirplaneResponseDto updateAirplane(UUID id, AirplaneRequestDto airplaneRequestDto);
    AirplaneResponseDto getAirplaneById(UUID id);
    List<AirplaneResponseDto> getAllAirplanes();
    void deleteAirplane(UUID id);
}
