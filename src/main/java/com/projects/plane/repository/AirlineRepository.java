package com.projects.plane.repository;

import com.projects.plane.model.Airline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AirlineRepository extends JpaRepository<Airline, UUID> {
}
