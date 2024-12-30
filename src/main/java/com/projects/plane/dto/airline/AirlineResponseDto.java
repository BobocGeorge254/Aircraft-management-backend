package com.projects.plane.dto.airline;

import com.projects.plane.dto.airport.AirportResponseDto;
import com.projects.plane.model.enums.AirlineOperatingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AirlineResponseDto {

    private UUID id;
    private String name;
    private String iataCode;
    private String icaoCode;
    private String country;
    private String headquarters;
    private AirlineOperatingStatus operatingStatus;
    private Integer foundedYear;
    private List<AirportResponseDto> hubs = new ArrayList<>();

}
