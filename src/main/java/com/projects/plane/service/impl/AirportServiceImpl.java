package com.projects.plane.service.impl;

import com.projects.plane.dto.airport.AirportRequestDto;
import com.projects.plane.dto.airport.AirportResponseDto;
import com.projects.plane.exception.ResourceNotFoundException;
import com.projects.plane.mapper.AirportMapper;
import com.projects.plane.model.Airport;
import com.projects.plane.repository.AirportRepository;
import com.projects.plane.service.AirportService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AirportServiceImpl implements AirportService {

    private final AirportRepository airportRepository;

    @Override
    public AirportResponseDto createAirport(AirportRequestDto airportRequestDto) {
        Airport airport = AirportMapper.mapToAirport(airportRequestDto);
        Airport savedAirport = airportRepository.save(airport);

        return AirportMapper.mapToAirportResponseDto(savedAirport);
    }

    @Override
    public AirportResponseDto updateAirport(UUID id, AirportRequestDto airportRequestDto) {
        Airport existingAirport = airportRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Airport not found with ID: " + id));

        existingAirport.setName(airportRequestDto.getName());
        existingAirport.setIataCode(airportRequestDto.getIataCode());
        existingAirport.setIcaoCode(airportRequestDto.getIcaoCode());
        existingAirport.setCountry(airportRequestDto.getCountry());
        existingAirport.setCity(airportRequestDto.getCity());
        existingAirport.setLatitude(airportRequestDto.getLatitude());
        existingAirport.setLongitude(airportRequestDto.getLongitude());
        existingAirport.setNumberOfRunways(airportRequestDto.getNumberOfRunways());

        Airport updatedAirport = airportRepository.save(existingAirport);

        return AirportMapper.mapToAirportResponseDto(updatedAirport);
    }

    @Override
    public AirportResponseDto getAirportById(UUID id) {
        Airport airport = airportRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Airport not found with ID: " + id));
        return AirportMapper.mapToAirportResponseDto(airport);
    }

    @Override
    public List<AirportResponseDto> getAllAirports() {
        return airportRepository
                .findAll()
                .stream()
                .map(AirportMapper::mapToAirportResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAirport(UUID id) {
        if (!airportRepository.existsById(id)) {
            throw new ResourceNotFoundException("Airport not found with ID: " + id);
        }
        airportRepository.deleteById(id);
    }
}
