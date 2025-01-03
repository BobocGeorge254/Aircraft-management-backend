package com.projects.plane.service.impl;

import com.projects.plane.dto.flight.FlightRequestDto;
import com.projects.plane.dto.flight.FlightResponseDto;
import com.projects.plane.exception.ResourceNotFoundException;
import com.projects.plane.model.*;
import com.projects.plane.model.enums.AircraftOperatingStatus;
import com.projects.plane.model.enums.AirlineOperatingStatus;
import com.projects.plane.model.enums.FlightStatus;
import com.projects.plane.repository.AircraftRepository;
import com.projects.plane.repository.AirportRepository;
import com.projects.plane.repository.FlightRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FlightServiceImplTest {

    @Mock
    private FlightRepository flightRepository;

    @Mock
    private AircraftRepository aircraftRepository;

    @Mock
    private AirportRepository airportRepository;

    @InjectMocks
    private FlightServiceImpl flightService;

    private Airplane airplane;
    private Airline airline;
    private Aircraft aircraft;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private Flight flight;
    private FlightRequestDto requestDto;
    private UUID airlineId;
    private UUID airplaneId;
    private UUID flightId;
    private UUID aircraftId;
    private UUID departureAirportId;
    private UUID arrivalAirportId;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    @BeforeEach
    void setUp() {
        airlineId = UUID.randomUUID();
        airplaneId = UUID.randomUUID();
        flightId = UUID.randomUUID();
        aircraftId = UUID.randomUUID();
        departureAirportId = UUID.randomUUID();
        arrivalAirportId = UUID.randomUUID();
        departureTime = LocalDateTime.now().plusHours(2);
        arrivalTime = departureTime.plusHours(3);

        airline = new Airline(airlineId, "British Airways", "BA", "BAW", "United Kingdom", "London", AirlineOperatingStatus.ACTIVE, 1974, new ArrayList<>());
        airplane = new Airplane(airplaneId, "Boeing", "787-9", 296, 80000, 14800);
        aircraft = new Aircraft(aircraftId, airline, airplane, "G-ZBKF", AircraftOperatingStatus.OPERATIONAL);
        departureAirport = new Airport(departureAirportId, "Departure Airport", "DEP", "DEPA", "United Kingdom", "London", 80.0, 80.0, 3, null);
        arrivalAirport = new Airport(arrivalAirportId, "Arrival Airport", "ARR", "ARRA", "United Kingdom", "London", 80.0, 80.0, 3, null);

        requestDto = new FlightRequestDto(aircraftId, departureAirportId, arrivalAirportId, "FL123", departureTime, arrivalTime, FlightStatus.SCHEDULED);
        flight = new Flight(flightId, aircraft, departureAirport, arrivalAirport, "FL123", departureTime, arrivalTime, FlightStatus.SCHEDULED);
    }

    @Test
    void whenCreateFlight_thenFlightIsSavedAndReturned() {
        // Arrange
        when(aircraftRepository.findById(aircraftId)).thenReturn(Optional.of(aircraft));
        when(airportRepository.findById(departureAirportId)).thenReturn(Optional.of(departureAirport));
        when(airportRepository.findById(arrivalAirportId)).thenReturn(Optional.of(arrivalAirport));
        when(flightRepository.save(any(Flight.class))).thenReturn(flight);

        // Act
        FlightResponseDto responseDto = flightService.createFlight(requestDto);

        // Assert
        assertNotNull(responseDto);
        assertEquals("FL123", responseDto.getFlightNumber());
        verify(flightRepository).save(any(Flight.class));
    }

    @Test
    void whenCreateFlight_withInvalidAircraftId_thenThrowsResourceNotFoundException() {
        // Arrange
        when(aircraftRepository.findById(aircraftId)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> flightService.createFlight(requestDto));
        assertEquals("Aircraft not found with ID: " + aircraftId, exception.getMessage());
        verify(aircraftRepository).findById(aircraftId);
        verify(flightRepository, never()).save(any(Flight.class));
    }

    @Test
    void whenCreateFlight_withInvalidDepartureAirportId_thenThrowsResourceNotFoundException() {
        // Arrange
        when(aircraftRepository.findById(aircraftId)).thenReturn(Optional.of(aircraft));
        when(airportRepository.findById(departureAirportId)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> flightService.createFlight(requestDto));
        assertEquals("Departure airport not found with ID: " + departureAirportId, exception.getMessage());
        verify(airportRepository).findById(departureAirportId);
        verify(flightRepository, never()).save(any(Flight.class));
    }

    @Test
    void whenCreateFlight_withInvalidArrivalAirportId_thenThrowsResourceNotFoundException() {
        // Arrange
        assertNotNull(aircraft, "Aircraft should not be null"); // Ensure aircraft is initialized
        assertNotNull(departureAirport, "Departure airport should not be null"); // Ensure departure airport is initialized
        when(aircraftRepository.findById(aircraftId)).thenReturn(Optional.ofNullable(aircraft));
        when(airportRepository.findById(departureAirportId)).thenReturn(Optional.ofNullable(departureAirport));
        when(airportRepository.findById(arrivalAirportId)).thenReturn(Optional.empty()); // Mock invalid arrival airport

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> flightService.createFlight(requestDto));
        assertEquals("Arrival airport not found with ID: " + arrivalAirportId, exception.getMessage());

        // Verify interactions with mocks
        verify(aircraftRepository).findById(aircraftId);
        verify(airportRepository).findById(departureAirportId);
        verify(airportRepository).findById(arrivalAirportId);

        verify(flightRepository, never()).save(any(Flight.class));
    }


    @Test
    void whenUpdateFlight_withValidId_thenFlightIsUpdatedAndReturned() {
        // Arrange
        Flight existingFlight = new Flight();
        existingFlight.setId(flightId);

        when(flightRepository.findById(flightId)).thenReturn(Optional.of(existingFlight));
        when(aircraftRepository.findById(aircraftId)).thenReturn(Optional.of(aircraft));
        when(airportRepository.findById(departureAirportId)).thenReturn(Optional.of(departureAirport));
        when(airportRepository.findById(arrivalAirportId)).thenReturn(Optional.of(arrivalAirport));
        when(flightRepository.save(any(Flight.class))).thenReturn(flight);

        // Act
        FlightResponseDto responseDto = flightService.updateFlight(flightId, requestDto);

        // Assert
        assertNotNull(responseDto);
        assertEquals("FL123", responseDto.getFlightNumber());
        verify(flightRepository).save(any(Flight.class));
    }

    @Test
    void whenUpdateFlight_withInvalidId_thenThrowsResourceNotFoundException() {
        // Arrange
        when(flightRepository.findById(flightId)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> flightService.updateFlight(flightId, requestDto));
        assertEquals("Flight not found with ID: " + flightId, exception.getMessage());
        verify(flightRepository).findById(flightId);
        verify(flightRepository, never()).save(any(Flight.class));
    }

    @Test
    void whenGetFlightById_withValidId_thenReturnsFlight() {
        // Arrange
        when(flightRepository.findById(flightId)).thenReturn(Optional.of(flight));

        // Act
        FlightResponseDto responseDto = flightService.getFlightById(flightId);

        // Assert
        assertNotNull(responseDto);
        assertEquals(flight.getFlightNumber(), responseDto.getFlightNumber());
        verify(flightRepository).findById(flightId);
    }

    @Test
    void whenGetFlightById_withInvalidId_thenThrowsResourceNotFoundException() {
        // Arrange
        when(flightRepository.findById(flightId)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> flightService.getFlightById(flightId));
        assertEquals("Flight not found with ID: " + flightId, exception.getMessage());
        verify(flightRepository).findById(flightId);
    }

    @Test
    void whenGetAllFlights_withNoFilters_thenReturnsAllFlights() {
        // Arrange
        List<Flight> flights = List.of(flight);
        when(flightRepository.findAll()).thenReturn(flights);

        // Act
        List<FlightResponseDto> responseDtos = flightService.getAllFlights(null, null, null);

        // Assert
        assertNotNull(responseDtos);
        assertEquals(1, responseDtos.size());
        assertEquals("FL123", responseDtos.get(0).getFlightNumber());
        verify(flightRepository).findAll();
    }

    @Test
    void whenGetAllFlights_withAllFilters_thenReturnsFilteredFlights() {
        // Arrange
        List<Flight> flights = List.of(flight);
        when(flightRepository.findByAircraftIdAndDepartureAirportIdAndArrivalAirportId(
                aircraftId, departureAirportId, arrivalAirportId))
                .thenReturn(flights);

        // Act
        List<FlightResponseDto> responseDtos = flightService.getAllFlights(
                aircraftId, departureAirportId, arrivalAirportId);

        // Assert
        assertNotNull(responseDtos);
        assertEquals(1, responseDtos.size());
        verify(flightRepository).findByAircraftIdAndDepartureAirportIdAndArrivalAirportId(
                aircraftId, departureAirportId, arrivalAirportId);
    }

    @Test
    void whenDeleteFlight_withValidId_thenDeletesFlight() {
        // Arrange
        when(flightRepository.existsById(flightId)).thenReturn(true);
        doNothing().when(flightRepository).deleteById(flightId);

        // Act
        flightService.deleteFlight(flightId);

        // Assert
        verify(flightRepository).existsById(flightId);
        verify(flightRepository).deleteById(flightId);
    }

    @Test
    void whenDeleteFlight_withInvalidId_thenThrowsResourceNotFoundException() {
        // Arrange
        when(flightRepository.existsById(flightId)).thenReturn(false);

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> flightService.deleteFlight(flightId));
        assertEquals("Flight not found with ID: " + flightId, exception.getMessage());
        verify(flightRepository).existsById(flightId);
        verify(flightRepository, never()).deleteById(flightId);
    }
}