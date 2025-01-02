package com.projects.plane.dto.airplane;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AirplaneRequestDto {

    @NotBlank(message = "Manufacturer cannot be blank.")
    private String manufacturer;

    @NotBlank(message = "Model cannot be blank.")
    private String model;

    @NotNull(message = "Seating capacity cannot be null.")
    @Min(value = 1, message = "Seating capacity must be at least 1.")
    private Integer seatingCapacity;

    @NotNull(message = "Maximum takeoff weight cannot be null.")
    @Min(value = 1, message = "Maximum takeoff weight must be greater than 0.")
    private Integer maximumTakeoffWeight;

    @NotNull(message = "Range cannot be null.")
    @Min(value = 1, message = "Range must be greater than 0.")
    private Integer range;
}
