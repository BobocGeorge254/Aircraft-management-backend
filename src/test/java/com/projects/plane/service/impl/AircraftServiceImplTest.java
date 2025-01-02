package com.projects.plane.service.impl;

import com.projects.plane.dto.aircraft.AircraftRequestDto;
import com.projects.plane.dto.aircraft.AircraftResponseDto;
import com.projects.plane.exception.ResourceNotFoundException;
import com.projects.plane.model.Aircraft;
import com.projects.plane.model.Airline;
import com.projects.plane.model.Airplane;
import com.projects.plane.model.enums.AircraftOperatingStatus;
import com.projects.plane.repository.AircraftRepository;
import com.projects.plane.repository.AirlineRepository;
import com.projects.plane.repository.AirplaneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AircraftServiceImplTest {

    @Mock
    private AircraftRepository aircraftRepository;

    @Mock
    private AirlineRepository airlineRepository;

    @Mock
    private AirplaneRepository airplaneRepository;

    @InjectMocks
    private AircraftServiceImpl aircraftService;

    private UUID aircraftId;
    private UUID airlineId;
    private UUID airplaneId;
    private Airline airline;
    private Airplane airplane;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        aircraftId = UUID.randomUUID();
        airlineId = UUID.randomUUID();
        airplaneId = UUID.randomUUID();

        airline = new Airline(airlineId, "American Airlines", "AA", "AAL", "United States", "Fort Worth, Texas", null, 1930, null);
        airplane = new Airplane(airplaneId, "Boeing", "737", 180, 8000, null);

        when(airlineRepository.findById(airlineId)).thenReturn(Optional.of(airline)); // Ensure the mock returns the airline with the same ID
        when(airplaneRepository.findById(airplaneId)).thenReturn(Optional.of(airplane));
    }

    @Test
    void whenCreateAircraft_thenAircraftIsCreated() {
        AircraftRequestDto requestDto = new AircraftRequestDto(airplaneId, airlineId, "ABC123", AircraftOperatingStatus.OPERATIONAL);

        Aircraft savedAircraft = new Aircraft(aircraftId, airline, airplane, "ABC123", AircraftOperatingStatus.OPERATIONAL);
        when(aircraftRepository.save(any(Aircraft.class))).thenReturn(savedAircraft);

        AircraftResponseDto responseDto = aircraftService.createAircraft(requestDto);

        assertNotNull(responseDto);
        assertEquals("ABC123", responseDto.getRegistrationNumber());
        verify(aircraftRepository).save(any(Aircraft.class));
    }

    @Test
    void whenUpdateAircraft_thenAircraftIsUpdated() {
        Aircraft existingAircraft = new Aircraft(aircraftId, airline, airplane, "ABC123", AircraftOperatingStatus.OPERATIONAL);
        when(aircraftRepository.findById(aircraftId)).thenReturn(Optional.of(existingAircraft));

        AircraftRequestDto requestDto = new AircraftRequestDto(airplaneId, airlineId, "DEF456", AircraftOperatingStatus.STORED);
        Aircraft updatedAircraft = new Aircraft(aircraftId, airline, airplane, "DEF456", AircraftOperatingStatus.STORED);
        when(aircraftRepository.save(any(Aircraft.class))).thenReturn(updatedAircraft);

        AircraftResponseDto responseDto = aircraftService.updateAircraft(aircraftId, requestDto);

        assertNotNull(responseDto);
        assertEquals("DEF456", responseDto.getRegistrationNumber());
        assertEquals(AircraftOperatingStatus.STORED, responseDto.getAircraftOperatingStatus());
        verify(aircraftRepository).save(any(Aircraft.class));
    }

    @Test
    void whenGetAircraftById_thenReturnAircraft() {
        Aircraft existingAircraft = new Aircraft(aircraftId, airline, airplane, "ABC123", AircraftOperatingStatus.DAMAGED);
        when(aircraftRepository.findById(aircraftId)).thenReturn(Optional.of(existingAircraft));

        AircraftResponseDto responseDto = aircraftService.getAircraftById(aircraftId);

        assertNotNull(responseDto);
        assertEquals("ABC123", responseDto.getRegistrationNumber());
        verify(aircraftRepository).findById(aircraftId);
    }

    @Test
    void whenGetAircraftById_aircraftNotFound_thenThrowException() {
        when(aircraftRepository.findById(aircraftId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> aircraftService.getAircraftById(aircraftId));
    }

    @Test
    void whenDeleteAircraft_thenAircraftIsDeleted() {
        when(aircraftRepository.existsById(aircraftId)).thenReturn(true);
        aircraftService.deleteAircraft(aircraftId);

        verify(aircraftRepository).deleteById(aircraftId);
    }

    @Test
    void whenDeleteAircraft_aircraftNotFound_thenThrowException() {
        when(aircraftRepository.existsById(aircraftId)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> aircraftService.deleteAircraft(aircraftId));
    }
}
