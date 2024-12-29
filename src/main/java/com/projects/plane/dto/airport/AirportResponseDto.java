package com.projects.plane.dto.airport;

import com.projects.plane.dto.airline.AirlineResponseDto;
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
public class AirportResponseDto {

    private UUID id;
    private String name;
    private String iataCode;
    private String icaoCode;
    private String country;
    private String city;
    private Double latitude;
    private Double longitude;
    private Integer numberOfRunways;
    private List<String> airlines = new ArrayList<>();

}
