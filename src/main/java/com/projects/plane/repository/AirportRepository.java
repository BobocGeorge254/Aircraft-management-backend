package com.projects.plane.repository;

import com.projects.plane.model.Airport;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Hidden
@Repository
public interface AirportRepository extends JpaRepository<Airport, UUID> {
}
