package com.projects.plane.mapper;

import com.projects.plane.dto.aircraft.AircraftRequestDto;
import com.projects.plane.dto.aircraft.AircraftResponseDto;
import com.projects.plane.model.Aircraft;
import com.projects.plane.model.Airline;
import com.projects.plane.model.Airplane;

public class AircraftMapper {
    public static AircraftResponseDto mapToAircraftResponseDto(Aircraft aircraft) {
        return new AircraftResponseDto(
                aircraft.getId(),
                aircraft.getRegistrationNumber(),
                aircraft.getOperatingStatus(),
                aircraft.getAirline() != null ? aircraft.getAirline().getName() : null,
                aircraft.getAirplane().getManufacturer(),
                aircraft.getAirplane().getModel()
        );
    }
    public static Aircraft mapToAircraft(AircraftRequestDto aircraftRequestDto, Airline airline, Airplane airplane) {
        return new Aircraft(
                null,
                airline,
                airplane,
                aircraftRequestDto.getRegistrationNumber(),
                aircraftRequestDto.getOperatingStatus()
        );
    }
}
