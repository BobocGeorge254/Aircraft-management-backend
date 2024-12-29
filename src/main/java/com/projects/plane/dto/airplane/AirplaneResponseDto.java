package com.projects.plane.dto.airplane;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AirplaneResponseDto {
    private UUID id;
    private String manufacturer;
    private String model;
    private Integer seatingCapacity;
    private Integer maximumTakeoffWeight;
    private Integer range;
}
