package com.projects.plane.service.impl;

import com.projects.plane.dto.aircraft.AircraftRequestDto;
import com.projects.plane.dto.aircraft.AircraftResponseDto;
import com.projects.plane.exception.ResourceNotFoundException;
import com.projects.plane.mapper.AircraftMapper;
import com.projects.plane.model.Aircraft;
import com.projects.plane.model.Airline;
import com.projects.plane.model.Airplane;
import com.projects.plane.repository.AircraftRepository;
import com.projects.plane.repository.AirlineRepository;
import com.projects.plane.repository.AirplaneRepository;
import com.projects.plane.service.AircraftService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AircraftServiceImpl implements AircraftService {

    private AircraftRepository aircraftRepository;
    private AirlineRepository airlineRepository;
    private AirplaneRepository airplaneRepository;

    @Override
    public AircraftResponseDto createAircraft(AircraftRequestDto aircraftRequestDto) {
        Airline airline = null;
        if (aircraftRequestDto.getAirlineId() != null) {
            airline = airlineRepository.findById(aircraftRequestDto.getAirlineId())
                    .orElseThrow(() -> new ResourceNotFoundException("Airline not found with ID: " + aircraftRequestDto.getAirlineId()));
        }
        Airplane airplane = airplaneRepository.findById(aircraftRequestDto.getAirplaneId())
                .orElseThrow(() -> new ResourceNotFoundException("Airplane not found with ID: " + aircraftRequestDto.getAirplaneId()));

        Aircraft aircraft = AircraftMapper.mapToAircraft(aircraftRequestDto, airline, airplane);
        Aircraft savedAircraft = aircraftRepository.save(aircraft);

        return AircraftMapper.mapToAircraftResponseDto(savedAircraft);
    }

    @Override
    public AircraftResponseDto updateAircraft(UUID id, AircraftRequestDto dto) {
        Aircraft existingAircraft = aircraftRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aircraft not found with ID: " + id));

        Airline airline = null;
        if (dto.getAirlineId() != null) {
            airline = airlineRepository.findById(dto.getAirlineId())
                    .orElseThrow(() -> new ResourceNotFoundException("Airline not found with ID: " + dto.getAirlineId()));
        }

        Airplane airplane = airplaneRepository.findById(dto.getAirplaneId())
                .orElseThrow(() -> new ResourceNotFoundException("Airplane not found with ID: " + dto.getAirplaneId()));

        existingAircraft.setRegistrationNumber(dto.getRegistrationNumber());
        existingAircraft.setOperatingStatus(dto.getOperatingStatus());
        existingAircraft.setAirline(airline);
        existingAircraft.setAirplane(airplane);

        Aircraft updatedAircraft = aircraftRepository.save(existingAircraft);

        return AircraftMapper.mapToAircraftResponseDto(updatedAircraft);
    }

    @Override
    public AircraftResponseDto getAircraftById(UUID id) {
        Aircraft aircraft = aircraftRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aircraft not found with ID: " + id));
        return AircraftMapper.mapToAircraftResponseDto(aircraft);
    }

    @Override
    public List<AircraftResponseDto> getAllAircraft(UUID airplaneId, UUID airlineId) {
        List<Aircraft> aircraft;

        if (airplaneId != null && airlineId != null) {
            aircraft = aircraftRepository.findByAirplaneIdAndAirlineId(airplaneId, airlineId);
        }
        else if (airplaneId != null) {
            aircraft = aircraftRepository.findByAirplaneId(airplaneId);
        }
        else if (airlineId != null) {
            aircraft = aircraftRepository.findByAirlineId(airlineId);
        }
        else {
            aircraft = aircraftRepository.findAll();
        }

        return aircraft
                .stream()
                .map(AircraftMapper::mapToAircraftResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAircraft(UUID id) {
        if (!aircraftRepository.existsById(id)) {
            throw new ResourceNotFoundException("Aircraft not found with ID: " + id);
        }
        aircraftRepository.deleteById(id);
    }
}
