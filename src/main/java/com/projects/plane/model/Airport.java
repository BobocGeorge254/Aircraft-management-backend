package com.projects.plane.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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

    @NotBlank(message = "Airport name cannot be blank.")
    @Size(min = 3, max = 100, message = "Airport name must be between 3 and 100 characters.")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "IATA code cannot be blank.")
    @Size(min = 3, max = 3, message = "IATA code must be 3 characters long.")
    @Pattern(regexp = "^[A-Za-z]{3}$", message = "IATA code must consist of 3 alphabetic characters.")
    @Column(name = "iata_code", unique = true)
    private String iataCode;

    @NotBlank(message = "ICAO code cannot be blank.")
    @Size(min = 4, max = 4, message = "ICAO code must be 4 characters long.")
    @Pattern(regexp = "^[A-Za-z]{4}$", message = "ICAO code must consist of 4 alphabetic characters.")
    @Column(name = "icao_code", unique = true)
    private String icaoCode;

    @NotBlank(message = "Country cannot be blank.")
    @Size(min = 2, max = 100, message = "Country name must be between 2 and 100 characters.")
    @Column(name = "country")
    private String country;

    @NotBlank(message = "City cannot be blank.")
    @Size(min = 2, max = 100, message = "City name must be between 2 and 100 characters.")
    @Column(name = "city")
    private String city;

    @NotNull(message = "Latitude cannot be null.")
    @Min(value = -90, message = "Latitude must be between -90 and 90 degrees.")
    @Max(value = 90, message = "Latitude must be between -90 and 90 degrees.")
    @Column(name = "latitude")
    private Double latitude;

    @NotNull(message = "Longitude cannot be null.")
    @Min(value = -180, message = "Longitude must be between -180 and 180 degrees.")
    @Max(value = 180, message = "Longitude must be between -180 and 180 degrees.")
    @Column(name = "longitude")
    private Double longitude;

    @Min(value = 1, message = "An airport must have at least 1 runway.")
    @Max(value = 10, message = "An airport can have a maximum of 10 runways.")
    @Column(name = "number_of_runways")
    private Integer numberOfRunways;

    @ManyToMany(mappedBy = "hubs")
    private List<Airline> airlines = new ArrayList<>();
}
