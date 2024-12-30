package com.projects.plane.dto.airline;

import com.projects.plane.model.enums.AirlineOperatingStatus;
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
public class AirlineRequestDto {

    private String name;

    @Size(min = 2, max = 3, message = "IATA code for airlines must be 2 or 3 characters long!")
    @Pattern(regexp = "^[A-Za-z]{2,3}$", message = "IATA code must consist of 2 to 3 alphabetic characters")
    private String iataCode;

    @Size(min = 3, max = 3, message = "ICAO code for airlines must be exactly 3 characters long!")
    @Pattern(regexp = "^[A-Za-z]{3}$", message = "ICAO code must consist of exactly 3 alphabetic characters")
    private String icaoCode;

    private String country;
    private String headquarters;
    private AirlineOperatingStatus operatingStatus;
    private Integer foundedYear;
}
