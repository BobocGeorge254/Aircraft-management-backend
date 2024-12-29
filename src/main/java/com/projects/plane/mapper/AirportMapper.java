package com.projects.plane.mapper;

import com.projects.plane.dto.airport.AirportRequestDto;
import com.projects.plane.dto.airport.AirportResponseDto;
import com.projects.plane.model.Airline;
import com.projects.plane.model.Airport;

import java.util.stream.Collectors;

public class AirportMapper {

    public static AirportResponseDto mapToAirportResponseDto(Airport airport) {
        return new AirportResponseDto(
                airport.getId(),
                airport.getName(),
                airport.getIataCode(),
                airport.getIcaoCode(),
                airport.getCountry(),
                airport.getCity(),
                airport.getLatitude(),
                airport.getLongitude(),
                airport.getNumberOfRunways(),
                airport.getAirlines()
                        .stream()
                        .map(Airline::getName)
                        .collect(Collectors.toList())
        );
    }

    public static Airport mapToAirport(AirportRequestDto airportRequestDto) {
        return new Airport(
                null,
                airportRequestDto.getName(),
                airportRequestDto.getIataCode(),
                airportRequestDto.getIcaoCode(),
                airportRequestDto.getCountry(),
                airportRequestDto.getCity(),
                airportRequestDto.getLatitude(),
                airportRequestDto.getLongitude(),
                airportRequestDto.getNumberOfRunways(),
                null
        );
    }
}
