package com.projects.plane.dto.airport;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AirportRequestDto {

    private String name;

    @Size(min = 3, max = 3, message = "IATA code must be 3 characters long!")
    @Pattern(regexp = "^[A-Za-z]{3}$", message = "IATA code must consist of 3 alphabetic characters!")
    private String iataCode;

    @Size(min = 4, max = 4, message = "ICAO code must be 4 characters long!")
    @Pattern(regexp = "^[A-Za-z]{4}$", message = "ICAO code must consist of 4 alphabetic characters!")
    private String icaoCode;

    private String country;

    private String city;

    private Double latitude;

    private Double longitude;

    @Min(value = 1, message = "An airport must have at least 1 runway.")
    @Max(value = 10, message = "An airport can have a maximum of 10 runways.")
    private Integer numberOfRunways;
}
