package com.projects.plane.model;

import com.projects.plane.model.enums.AirlineOperatingStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "airline")
public class Airline {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    @NotNull(message = "Airline name can not be null.")
    @Column(name = "name")
    public String name;

    @Column(name = "iata_code", unique = true)
    @Size(min = 2, max = 3, message = "IATA code for airlines must be 2 or 3 characters long.")
    @Pattern(regexp = "^[A-Za-z]{2,3}$", message = "IATA code must consist of 2 to 3 alphabetic characters.")
    public String iataCode;

    @Column(name = "icao_code", unique = true)
    @Size(min = 3, max = 3, message = "ICAO code for airlines must be exactly 3 characters long.")
    @Pattern(regexp = "^[A-Za-z]{3}$", message = "ICAO code must consist of exactly 3 alphabetic characters.")
    public String icaoCode;

    @NotNull(message = "Country cannot be blank.")
    @Column(name = "country")
    private String country;

    @Column(name = "headquarters")
    private String headquarters;

    @Enumerated(EnumType.STRING)
    @Column(name = "operating_status")
    private AirlineOperatingStatus operatingStatus;

    @Column(name = "founded_year")
    private Integer foundedYear;

    @ManyToMany
    @JoinTable(
            name = "airline_hubs",
            joinColumns = @JoinColumn(name = "airline_id"),
            inverseJoinColumns = @JoinColumn(name = "airport_id")
    )
    private List<Airport> hubs = new ArrayList<>();
}
