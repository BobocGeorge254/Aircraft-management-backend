package com.projects.plane.dto.airline;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AirlineHubDto {

    private String name;
    private String iataCode;
    private String icaoCode;
    private String country;

}
