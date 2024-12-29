package com.projects.plane.repository;

import com.projects.plane.model.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AirportRepository extends JpaRepository<Airport, UUID> {
}
