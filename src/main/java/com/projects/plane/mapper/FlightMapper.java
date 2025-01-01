package com.projects.plane.mapper;

import com.projects.plane.dto.airport.FlightAirportDto;
import com.projects.plane.dto.flight.FlightRequestDto;
import com.projects.plane.dto.flight.FlightResponseDto;
import com.projects.plane.model.Flight;
import com.projects.plane.model.Aircraft;
import com.projects.plane.model.Airport;

public class FlightMapper {

    public static FlightResponseDto mapToFlightResponseDto(Flight flight) {
        return new FlightResponseDto(
                flight.getId(),
                AircraftMapper.mapToAircraftResponseDto(flight.getAircraft()),
                new FlightAirportDto(
                        flight.getDepartureAirport().getName(),
                        flight.getDepartureAirport().getIataCode(),
                        flight.getDepartureAirport().getIcaoCode(),
                        flight.getDepartureAirport().getCountry()
                ),
                new FlightAirportDto(
                        flight.getArrivalAirport().getName(),
                        flight.getArrivalAirport().getIataCode(),
                        flight.getArrivalAirport().getIcaoCode(),
                        flight.getArrivalAirport().getCountry()
                ),
                flight.getFlightNumber(),
                flight.getDepartureDateTime(),
                flight.getArrivalDateTime(),
                flight.getFlightStatus()
        );
    }


    public static Flight mapToFlight(FlightRequestDto flightRequestDto, Aircraft aircraft, Airport departureAirport, Airport arrivalAirport) {
        return new Flight(
            null,
            aircraft,
            departureAirport,
            arrivalAirport,
            flightRequestDto.getFlightNumber(),
            flightRequestDto.getDepartureDateTime(),
            flightRequestDto.getArrivalDateTime(),
            flightRequestDto.getFlightStatus()
        );
    }


}
