package com.projects.plane.repository;

import com.projects.plane.model.Airplane;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AirplaneRepository extends JpaRepository<Airplane, UUID> {
}
