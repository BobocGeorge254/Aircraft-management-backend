package com.projects.plane.service.impl;

import com.projects.plane.dto.flight.FlightRequestDto;
import com.projects.plane.dto.flight.FlightResponseDto;
import com.projects.plane.exception.ResourceNotFoundException;
import com.projects.plane.mapper.FlightMapper;
import com.projects.plane.model.Aircraft;
import com.projects.plane.model.Airport;
import com.projects.plane.model.Flight;
import com.projects.plane.repository.AircraftRepository;
import com.projects.plane.repository.AirportRepository;
import com.projects.plane.repository.FlightRepository;
import com.projects.plane.service.FlightService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FlightServiceImpl implements FlightService {

    private FlightRepository flightRepository;
    private AircraftRepository aircraftRepository;
    private AirportRepository airportRepository;


    @Override
    public FlightResponseDto createFlight(FlightRequestDto flightRequestDto) {
        Aircraft aircraft = aircraftRepository.findById(flightRequestDto.getAircraftId())
                .orElseThrow(() -> new ResourceNotFoundException("Aircraft not found with ID: " + flightRequestDto.getAircraftId()));

        Airport departureAirport = airportRepository.findById(flightRequestDto.getDepartureAirportId())
                .orElseThrow(() -> new ResourceNotFoundException("Departure airport not found with ID: " + flightRequestDto.getDepartureAirportId()));

        Airport arrivalAirport = airportRepository.findById(flightRequestDto.getArrivalAirportId())
                .orElseThrow(() -> new ResourceNotFoundException("Arrival airport not found with ID: " + flightRequestDto.getArrivalAirportId()));

        Flight flight = FlightMapper.mapToFlight(flightRequestDto, aircraft, departureAirport, arrivalAirport);
        Flight savedFlight = flightRepository.save(flight);

        return FlightMapper.mapToFlightResponseDto(savedFlight);
    }

    @Override
    public FlightResponseDto updateFlight(UUID id, FlightRequestDto flightRequestDto) {
        Flight existingFlight = flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with ID: " + id));

        Aircraft aircraft = aircraftRepository.findById(flightRequestDto.getAircraftId())
                .orElseThrow(() -> new ResourceNotFoundException("Aircraft not found with ID: " + flightRequestDto.getAircraftId()));

        Airport departureAirport = airportRepository.findById(flightRequestDto.getDepartureAirportId())
                .orElseThrow(() -> new ResourceNotFoundException("Departure Airport not found with ID: " + flightRequestDto.getDepartureAirportId()));

        Airport arrivalAirport = airportRepository.findById(flightRequestDto.getArrivalAirportId())
                .orElseThrow(() -> new ResourceNotFoundException("Arrival Airport not found with ID: " + flightRequestDto.getArrivalAirportId()));

        existingFlight.setAircraft(aircraft);
        existingFlight.setDepartureAirport(departureAirport);
        existingFlight.setArrivalAirport(arrivalAirport);
        existingFlight.setDepartureDateTime(flightRequestDto.getDepartureDateTime());
        existingFlight.setArrivalDateTime(flightRequestDto.getArrivalDateTime());
        existingFlight.setFlightStatus(flightRequestDto.getFlightStatus());

        Flight updatedFlight = flightRepository.save(existingFlight);

        return FlightMapper.mapToFlightResponseDto(updatedFlight);
    }


    @Override
    public FlightResponseDto getFlightById(UUID flightId) {
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with ID: " + flightId));

        return FlightMapper.mapToFlightResponseDto(flight);
    }

    @Override
    public List<FlightResponseDto> getAllFlights(UUID aircraftId, UUID departureAirportId, UUID arrivalAirportId) {
        List<Flight> flights;

        if (aircraftId != null && departureAirportId != null && arrivalAirportId != null) {
            flights = flightRepository.findByAircraftIdAndDepartureAirportIdAndArrivalAirportId(aircraftId, departureAirportId, arrivalAirportId);
        }
        else if (aircraftId != null && departureAirportId != null) {
            flights = flightRepository.findByAircraftIdAndDepartureAirportId(aircraftId, departureAirportId);
        }
        else if (aircraftId != null && arrivalAirportId != null) {
            flights = flightRepository.findByAircraftIdAndArrivalAirportId(aircraftId, arrivalAirportId);
        }
        else if (departureAirportId != null && arrivalAirportId != null) {
            flights = flightRepository.findByDepartureAirportIdAndArrivalAirportId(departureAirportId, arrivalAirportId);
        }
        else if (aircraftId != null) {
            flights = flightRepository.findByAircraftId(aircraftId);
        }
        else if (departureAirportId != null) {
            flights = flightRepository.findByDepartureAirportId(departureAirportId);
        }
        else if (arrivalAirportId != null) {
            flights = flightRepository.findByArrivalAirportId(arrivalAirportId);
        }
        else {
            flights = flightRepository.findAll();
        }

        return flights
                .stream()
                .map(FlightMapper::mapToFlightResponseDto)
                .collect(Collectors.toList());
    }


    @Override
    public void deleteFlight(UUID id) {
        if (!flightRepository.existsById(id)) {
            throw new ResourceNotFoundException("Flight not found with ID: " + id);
        }
        flightRepository.deleteById(id);
    }

}
