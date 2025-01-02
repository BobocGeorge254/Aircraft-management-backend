package com.projects.plane.dto.aircraft;

import com.projects.plane.model.enums.AircraftOperatingStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotNull(message = "Airplane ID cannot be null.")
    private UUID airplaneId;

    @NotBlank(message = "Registration number cannot be blank.")
    @Size(min = 1, max = 20, message = "Registration number must be between 1 and 20 characters.")
    private String registrationNumber;

    @NotNull(message = "Operating status cannot be null.")
    private AircraftOperatingStatus operatingStatus;
}
