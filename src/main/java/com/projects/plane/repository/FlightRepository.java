package com.projects.plane.repository;

import com.projects.plane.model.Flight;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Hidden
@Repository
public interface FlightRepository extends JpaRepository<Flight, UUID> {

    List<Flight> findByAircraftId(UUID aircraftId);
    List<Flight> findByDepartureAirportId(UUID departureAirportId);
    List<Flight> findByArrivalAirportId(UUID arrivalAirportId);
    List<Flight> findByAircraftIdAndDepartureAirportId(UUID aircraftId, UUID departureAirportId);
    List<Flight> findByAircraftIdAndArrivalAirportId(UUID aircraftId, UUID arrivalAirportId);
    List<Flight> findByDepartureAirportIdAndArrivalAirportId(UUID departureAirportId, UUID arrivalAirportId);
    List<Flight> findByAircraftIdAndDepartureAirportIdAndArrivalAirportId(UUID aircraftId, UUID departureAirportId, UUID arrivalAirportId);

}
