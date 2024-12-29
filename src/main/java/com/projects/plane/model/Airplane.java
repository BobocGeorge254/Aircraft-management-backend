package com.projects.plane.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
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
@Table(name = "airplane")
@Schema(description = "Generic airplane model entity, representing basic airplane specifications.")
public class Airplane {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "model")
    private String model;

    @Column(name = "seating_capacity")
    private Integer seatingCapacity;

    @Column(name = "maximum_takeoff_weight")
    private Integer maximumTakeoffWeight;

    @Column(name = "range")
    private Integer range;
}
