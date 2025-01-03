package com.projects.plane.repository;

import com.projects.plane.model.Aircraft;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Hidden
@Repository
public interface AircraftRepository extends JpaRepository<Aircraft, UUID> {

    List<Aircraft> findByAirplaneId(UUID airplaneId);
    List<Aircraft> findByAirlineId(UUID airlineId);
    List<Aircraft> findByAirplaneIdAndAirlineId(UUID airplaneId, UUID airlineId);

}

