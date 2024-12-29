package com.projects.plane.dto.airplane;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AirplaneRequestDto {
    private String manufacturer;
    private String model;
    private Integer seatingCapacity;
    private Integer maximumTakeoffWeight;
    private Integer range;
}
