package com.projects.plane.mapper;

import com.projects.plane.dto.airline.AirlineRequestDto;
import com.projects.plane.dto.airline.AirlineResponseDto;
import com.projects.plane.model.Airline;

import java.util.stream.Collectors;

public class AirlineMapper {
    public static AirlineResponseDto mapToAirlineResponseDto(Airline airline) {
        AirlineResponseDto response = new AirlineResponseDto(
                airline.getId(),
                airline.getName(),
                airline.getIataCode(),
                airline.getIcaoCode(),
                airline.getCountry(),
                airline.getHeadquarters(),
                airline.getOperatingStatus(),
                airline.getFoundedYear(),
                airline.getHubs()
                        .stream()
                        .map(AirportMapper::mapToAirportResponseDto)
                        .collect(Collectors.toList())
        );
        return response;
    }

    public static Airline mapToAirline(AirlineRequestDto airlineRequestDto) {
        return new Airline(
                null,
                airlineRequestDto.getName(),
                airlineRequestDto.getIataCode(),
                airlineRequestDto.getIcaoCode(),
                airlineRequestDto.getCountry(),
                airlineRequestDto.getHeadquarters(),
                airlineRequestDto.getOperatingStatus(),
                airlineRequestDto.getFoundedYear(),
                null
        );
    }
}
