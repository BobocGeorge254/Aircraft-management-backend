package com.projects.plane.service.impl;

import com.projects.plane.dto.airline.AirlineRequestDto;
import com.projects.plane.dto.airline.AirlineResponseDto;
import com.projects.plane.exception.ResourceNotFoundException;
import com.projects.plane.mapper.AirlineMapper;
import com.projects.plane.model.Airline;
import com.projects.plane.model.Airport;
import com.projects.plane.repository.AirlineRepository;
import com.projects.plane.repository.AirportRepository;
import com.projects.plane.service.AirlineService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AirlineServiceImpl implements AirlineService {
    private AirlineRepository airlineRepository;
    private AirportRepository airportRepository;

    @Override
    public AirlineResponseDto createAirline(AirlineRequestDto airlineRequestDto) {
        Airline airline = AirlineMapper.mapToAirline(airlineRequestDto);
        Airline savedAirline = airlineRepository.save(airline);

        return AirlineMapper.mapToAirlineResponseDto(savedAirline);
    }

    @Override
    public AirlineResponseDto updateAirline(UUID id, AirlineRequestDto airlineRequestDto) {
        Airline existingAirline = airlineRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Airline not found with ID: " + id));

        existingAirline.setName(airlineRequestDto.getName());
        existingAirline.setIataCode(airlineRequestDto.getIataCode());
        existingAirline.setIcaoCode(airlineRequestDto.getIcaoCode());
        existingAirline.setCountry(airlineRequestDto.getCountry());
        existingAirline.setHeadquarters(airlineRequestDto.getHeadquarters());
        existingAirline.setOperatingStatus(airlineRequestDto.getOperatingStatus());
        existingAirline.setFoundedYear(airlineRequestDto.getFoundedYear());

        Airline updateAirline = airlineRepository.save(existingAirline);

        return AirlineMapper.mapToAirlineResponseDto(updateAirline);
    }

    @Override
    public AirlineResponseDto getAirlineById(UUID id) {
        Airline airline = airlineRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Airline not found with ID: " + id));
        return AirlineMapper.mapToAirlineResponseDto(airline);
    }

    @Override
    public List<AirlineResponseDto> getAllAirlines() {
        return airlineRepository
                .findAll()
                .stream()
                .map(AirlineMapper::mapToAirlineResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAirline(UUID id) {
        if (!airlineRepository.existsById(id)) {
            throw new ResourceNotFoundException("Airline not found with ID: " + id);
        }
        airlineRepository.deleteById(id);
    }

    @Override
    public AirlineResponseDto addHubToAirline(UUID airlineId, UUID airportId) {
        Airline airline = airlineRepository.findById(airlineId)
                .orElseThrow(() -> new ResourceNotFoundException("Airline not found with ID: " + airlineId));
        Airport airport = airportRepository.findById(airportId)
                .orElseThrow(() -> new ResourceNotFoundException("Airport not found with ID: " + airportId));

        airline.getHubs().add(airport);
        airlineRepository.save(airline);

        return AirlineMapper.mapToAirlineResponseDto(airline);
    }


}
