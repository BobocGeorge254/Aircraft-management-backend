package com.projects.plane.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
@Entity
@Table(name = "airport")
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "iata_code", unique = true)
    @Size(min = 3, max = 3, message = "IATA code must be 3 characters long!")
    @Pattern(regexp = "^[A-Za-z]{3}$", message = "IATA code must consist of 3 alphabetic characters!")
    private String iataCode;

    @Column(name = "icao_code", unique = true)
    @Size(min = 4, max = 4, message = "ICAO code must be 4 characters long!")
    @Pattern(regexp = "^[A-Za-z]{4}$", message = "ICAO code must consist of 4 alphabetic characters!")
    private String icaoCode;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "number_of_runways")
    @Min(value = 1, message = "An airport must have at least 1 runway.")
    @Max(value = 10, message = "An airport can have a maximum of 10 runways.")
    private Integer numberOfRunways;

    @ManyToMany(mappedBy = "hubs")
    private List<Airline> airlines = new ArrayList<>();
}
