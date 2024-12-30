package com.projects.plane.dto.aircraft;

import com.projects.plane.model.enums.AircraftOperatingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AircraftResponseDto {
    private UUID id;
    private String registrationNumber;
    private AircraftOperatingStatus aircraftOperatingStatus;
    private String airlineName;
    private String airplaneManufacturer;
    private String airplaneModel;
}
