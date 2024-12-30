package com.projects.plane.service;

import com.projects.plane.dto.aircraft.AircraftRequestDto;
import com.projects.plane.dto.aircraft.AircraftResponseDto;

import java.util.List;
import java.util.UUID;

public interface AircraftService {
    AircraftResponseDto createAircraft(AircraftRequestDto dto);
    AircraftResponseDto updateAircraft(UUID id, AircraftRequestDto dto);
    AircraftResponseDto getAircraftById(UUID id);
    List<AircraftResponseDto> getAllAircraft(UUID airplaneId, UUID airlineId);
    void deleteAircraft(UUID id);
}
