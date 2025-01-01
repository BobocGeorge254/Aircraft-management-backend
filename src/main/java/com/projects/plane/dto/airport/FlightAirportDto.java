package com.projects.plane.dto.airport;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlightAirportDto {

    private String name;
    private String iataCode;
    private String icaoCode;
    private String country;

}
