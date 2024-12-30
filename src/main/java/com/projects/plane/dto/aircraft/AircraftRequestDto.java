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
public class AircraftRequestDto {
    private UUID airlineId;
    private UUID airplaneId;
    private String registrationNumber;
    private AircraftOperatingStatus operatingStatus;
}
