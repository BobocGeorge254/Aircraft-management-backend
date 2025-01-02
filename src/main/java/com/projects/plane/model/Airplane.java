package com.projects.plane.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "airplane")
@Schema(description = "Generic airplane model entity, representing basic airplane specifications.")
public class Airplane {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Manufacturer cannot be blank.")
    @Size(min = 1, max = 50, message = "Manufacturer name must be between 1 and 50 characters.")
    @Column(name = "manufacturer")
    private String manufacturer;

    @NotBlank(message = "Model cannot be blank.")
    @Size(min = 1, max = 50, message = "Model name must be between 1 and 50 characters.")
    @Column(name = "model")
    private String model;

    @NotNull(message = "Seating capacity cannot be null.")
    @Min(value = 1, message = "Seating capacity must be at least 1.")
    @Column(name = "seating_capacity")
    private Integer seatingCapacity;

    @NotNull(message = "Maximum takeoff weight cannot be null.")
    @Min(value = 1, message = "Maximum takeoff weight must be greater than 0.")
    @Column(name = "maximum_takeoff_weight")
    private Integer maximumTakeoffWeight;

    @NotNull(message = "Range cannot be null.")
    @Min(value = 1, message = "Range must be greater than 0.")
    @Column(name = "range")
    private Integer range;
}
