package com.projects.plane.mapper;

import com.projects.plane.dto.airplane.AirplaneRequestDto;
import com.projects.plane.dto.airplane.AirplaneResponseDto;
import com.projects.plane.model.Airplane;

public class AirplaneMapper {

    public static AirplaneResponseDto mapToAirplaneResponseDto(Airplane airplane) {
        return new AirplaneResponseDto(
            airplane.getId(),
            airplane.getManufacturer(),
            airplane.getModel(),
            airplane.getSeatingCapacity(),
            airplane.getMaximumTakeoffWeight(),
            airplane.getRange()
        );
    }

    public static Airplane mapToAirplane(AirplaneRequestDto airplaneRequestDto) {
        return new Airplane(
                null,
            airplaneRequestDto.getManufacturer(),
            airplaneRequestDto.getModel(),
            airplaneRequestDto.getSeatingCapacity(),
            airplaneRequestDto.getMaximumTakeoffWeight(),
            airplaneRequestDto.getRange()
        );
    }
}

