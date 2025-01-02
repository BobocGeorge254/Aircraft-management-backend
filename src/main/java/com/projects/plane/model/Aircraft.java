package com.projects.plane.model;

import com.projects.plane.model.enums.AircraftOperatingStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "aircraft")
public class Aircraft {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "airline_id", nullable = true)
    private Airline airline;

    @ManyToOne(optional = false)
    @JoinColumn(name = "airplane_id", nullable = false)
    private Airplane airplane;

    @NotBlank(message = "Registration number cannot be blank.")
    @Size(min = 1, max = 20, message = "Registration number must be between 1 and 20 characters.")
    @Column(name = "registration_number", unique = true, nullable = false)
    private String registrationNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "operating_status", nullable = false)
    private AircraftOperatingStatus operatingStatus;
}
