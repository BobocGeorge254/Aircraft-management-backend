package com.projects.plane.dto.airport;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AirportRequestDto {

    @Valid

    @NotBlank(message = "Airport name cannot be blank.")
    @Size(min = 3, max = 100, message = "Airport name must be between 3 and 100 characters.")
    private String name;

    @Size(min = 3, max = 3, message = "IATA code must be 3 characters long.")
    @Pattern(regexp = "^[A-Za-z]{3}$", message = "IATA code must consist of 3 alphabetic characters.")
    private String iataCode;

    @Size(min = 4, max = 4, message = "ICAO code must be 4 characters long.")
    @Pattern(regexp = "^[A-Za-z]{4}$", message = "ICAO code must consist of 4 alphabetic characters.")
    private String icaoCode;

    @NotBlank(message = "Country cannot be blank.")
    @Size(min = 2, max = 100, message = "Country name must be between 2 and 100 characters.")
    private String country;

    @NotBlank(message = "City cannot be blank.")
    @Size(min = 2, max = 100, message = "City name must be between 2 and 100 characters.")
    private String city;

    @NotNull(message = "Latitude cannot be null.")
    @Min(value = -90, message = "Latitude must be between -90 and 90 degrees.")
    @Max(value = 90, message = "Latitude must be between -90 and 90 degrees.")
    private Double latitude;

    @NotNull(message = "Longitude cannot be null.")
    @Min(value = -180, message = "Longitude must be between -180 and 180 degrees.")
    @Max(value = 180, message = "Longitude must be between -180 and 180 degrees.")
    private Double longitude;

    @Min(value = 1, message = "An airport must have at least 1 runway.")
    @Max(value = 10, message = "An airport can have a maximum of 10 runways.")
    private Integer numberOfRunways;
}
