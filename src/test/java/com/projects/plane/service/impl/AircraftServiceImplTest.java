package com.projects.plane.service.impl;

import com.projects.plane.dto.aircraft.AircraftRequestDto;
import com.projects.plane.dto.aircraft.AircraftResponseDto;
import com.projects.plane.exception.ResourceNotFoundException;
import com.projects.plane.model.Aircraft;
import com.projects.plane.model.Airline;
import com.projects.plane.model.Airplane;
import com.projects.plane.model.enums.AircraftOperatingStatus;
import com.projects.plane.model.enums.AirlineOperatingStatus;
import com.projects.plane.repository.AircraftRepository;
import com.projects.plane.repository.AirlineRepository;
import com.projects.plane.repository.AirplaneRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AircraftServiceImplTest {

    @InjectMocks
    private AircraftServiceImpl aircraftService;

    @Mock
    private AircraftRepository aircraftRepository;

    @Mock
    private AirlineRepository airlineRepository;

    @Mock
    private AirplaneRepository airplaneRepository;

    @Test
    void whenCreateAircraft_thenAircraftIsSavedAndReturned() {
        // Arrange
        UUID airlineId = UUID.randomUUID();
        UUID airplaneId = UUID.randomUUID();
        AircraftRequestDto requestDto = new AircraftRequestDto(airlineId, airplaneId, "ABC123", AircraftOperatingStatus.OPERATIONAL);
        Airline airline = new Airline(airlineId, "Test Airline", "TST", "TSTC", "Test Country", "Test HQ", AirlineOperatingStatus.ACTIVE, 2000, null);
        Airplane airplane = new Airplane(airplaneId, "Boeing", "737", 200, 70000, 5000);
        Aircraft aircraft = new Aircraft(UUID.randomUUID(), airline, airplane, "ABC123", AircraftOperatingStatus.OPERATIONAL);

        when(airlineRepository.findById(airlineId)).thenReturn(Optional.of(airline));
        when(airplaneRepository.findById(airplaneId)).thenReturn(Optional.of(airplane));
        when(aircraftRepository.save(any(Aircraft.class))).thenReturn(aircraft);

        // Act
        AircraftResponseDto responseDto = aircraftService.createAircraft(requestDto);

        // Assert
        assertNotNull(responseDto);
        assertEquals("ABC123", responseDto.getRegistrationNumber());
        verify(aircraftRepository).save(any(Aircraft.class));
    }

    @Test
    void whenCreateAircraft_withInvalidAirlineId_thenThrowsResourceNotFoundException() {
        // Arrange
        UUID airlineId = UUID.randomUUID();
        UUID airplaneId = UUID.randomUUID();
        AircraftRequestDto requestDto = new AircraftRequestDto(airlineId, airplaneId, "ABC123", AircraftOperatingStatus.OPERATIONAL);

        when(airlineRepository.findById(airlineId)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> aircraftService.createAircraft(requestDto));
        assertEquals("Airline not found with ID: " + airlineId, exception.getMessage());
        verify(airlineRepository).findById(airlineId);
        verify(airplaneRepository, never()).findById(any(UUID.class));
        verify(aircraftRepository, never()).save(any(Aircraft.class));
    }

    @Test
    void whenCreateAircraft_withInvalidAirplaneId_thenThrowsResourceNotFoundException() {
        // Arrange
        UUID airlineId = UUID.randomUUID();
        UUID airplaneId = UUID.randomUUID();
        AircraftRequestDto requestDto = new AircraftRequestDto(airlineId, airplaneId, "ABC123", AircraftOperatingStatus.OPERATIONAL);
        Airline airline = new Airline(airlineId, "Test Airline", "TST", "TSTC", "Test Country", "Test HQ", AirlineOperatingStatus.ACTIVE, 2000, null);

        when(airlineRepository.findById(airlineId)).thenReturn(Optional.of(airline));
        when(airplaneRepository.findById(airplaneId)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> aircraftService.createAircraft(requestDto));
        assertEquals("Airplane not found with ID: " + airplaneId, exception.getMessage());
        verify(airlineRepository).findById(airlineId);
        verify(airplaneRepository).findById(airplaneId);
        verify(aircraftRepository, never()).save(any(Aircraft.class));
    }

    @Test
    void whenUpdateAircraft_withValidId_thenAircraftIsUpdatedAndReturned() {
        // Arrange
        UUID aircraftId = UUID.randomUUID();
        UUID airlineId = UUID.randomUUID();
        UUID airplaneId = UUID.randomUUID();
        AircraftRequestDto requestDto = new AircraftRequestDto(airlineId, airplaneId, "DEF456", AircraftOperatingStatus.OPERATIONAL);
        Airline airline = new Airline(airlineId, "Updated Airline", "TST", "TSTC", "Test Country", "Test HQ", null, 2000, null);
        Airplane airplane = new Airplane(airplaneId, "Airbus", "A320", 200, 65000, 4500);
        Aircraft existingAircraft = new Aircraft(aircraftId, airline, airplane, "ABC123", AircraftOperatingStatus.UNDER_MAINTENANCE);
        Aircraft updatedAircraft = new Aircraft(aircraftId, airline, airplane, "DEF456", AircraftOperatingStatus.OPERATIONAL);

        when(aircraftRepository.findById(aircraftId)).thenReturn(Optional.of(existingAircraft));
        when(aircraftRepository.save(existingAircraft)).thenReturn(updatedAircraft);
        when(airlineRepository.findById(airlineId)).thenReturn(Optional.of(airline));
        when(airplaneRepository.findById(airplaneId)).thenReturn(Optional.of(airplane));

        // Act
        AircraftResponseDto responseDto = aircraftService.updateAircraft(aircraftId, requestDto);

        // Assert
        assertNotNull(responseDto);
        assertEquals("DEF456", responseDto.getRegistrationNumber());
        verify(aircraftRepository).save(existingAircraft);
    }

    @Test
    void whenUpdateAircraft_withInvalidId_thenThrowsResourceNotFoundException() {
        // Arrange
        UUID aircraftId = UUID.randomUUID();
        AircraftRequestDto requestDto = new AircraftRequestDto(UUID.randomUUID(), UUID.randomUUID(), "DEF456", AircraftOperatingStatus.UNDER_MAINTENANCE);

        when(aircraftRepository.findById(aircraftId)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> aircraftService.updateAircraft(aircraftId, requestDto));
        assertEquals("Aircraft not found with ID: " + aircraftId, exception.getMessage());
        verify(aircraftRepository).findById(aircraftId);
        verify(aircraftRepository, never()).save(any(Aircraft.class));
    }

    @Test
    void whenGetAircraftById_withValidId_thenReturnsAircraft() {
        // Arrange
        UUID aircraftId = UUID.randomUUID();
        UUID airplaneId = UUID.randomUUID();
        Airplane airplane = new Airplane(airplaneId, "Airbus", "A320", 200, 65000, 4500);
        Aircraft aircraft = new Aircraft(aircraftId, null, airplane, "ABC123", AircraftOperatingStatus.UNDER_MAINTENANCE);

        when(aircraftRepository.findById(aircraftId)).thenReturn(Optional.of(aircraft));

        // Act
        AircraftResponseDto responseDto = aircraftService.getAircraftById(aircraftId);

        // Assert
        assertNotNull(responseDto);
        assertEquals(aircraft.getRegistrationNumber(), responseDto.getRegistrationNumber());
        verify(aircraftRepository).findById(aircraftId);
    }

    @Test
    void whenGetAircraftById_withInvalidId_thenThrowsResourceNotFoundException() {
        // Arrange
        UUID aircraftId = UUID.randomUUID();

        when(aircraftRepository.findById(aircraftId)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> aircraftService.getAircraftById(aircraftId));
        assertEquals("Aircraft not found with ID: " + aircraftId, exception.getMessage());
        verify(aircraftRepository).findById(aircraftId);
    }

    @Test
    void whenDeleteAircraft_withValidId_thenDeletesAircraft() {
        // Arrange
        UUID aircraftId = UUID.randomUUID();
        when(aircraftRepository.existsById(aircraftId)).thenReturn(true);

        // Act
        aircraftService.deleteAircraft(aircraftId);

        // Assert
        verify(aircraftRepository).existsById(aircraftId);
        verify(aircraftRepository).deleteById(aircraftId);
    }

    @Test
    void whenDeleteAircraft_withInvalidId_thenThrowsResourceNotFoundException() {
        // Arrange
        UUID aircraftId = UUID.randomUUID();
        when(aircraftRepository.existsById(aircraftId)).thenReturn(false);

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> aircraftService.deleteAircraft(aircraftId));
        assertEquals("Aircraft not found with ID: " + aircraftId, exception.getMessage());
        verify(aircraftRepository).existsById(aircraftId);
        verify(aircraftRepository, never()).deleteById(aircraftId);
    }

    @Test
    void whenGetAllAircraft_thenReturnsListOfAircraft() {
        // Arrange
        UUID airplaneId = UUID.randomUUID();
        UUID airlineId = UUID.randomUUID();
        Airplane airplane = new Airplane(airplaneId, "Airbus", "A320", 200, 65000, 4500);
        Aircraft aircraft1 = new Aircraft(UUID.randomUUID(), null, airplane, "ABC123", AircraftOperatingStatus.UNDER_MAINTENANCE);
        Aircraft aircraft2 = new Aircraft(UUID.randomUUID(), null, airplane, "DEF456", AircraftOperatingStatus.OPERATIONAL);
        List<Aircraft> aircraftList = List.of(aircraft1, aircraft2);

        when(aircraftRepository.findAll()).thenReturn(aircraftList);

        // Act
        List<AircraftResponseDto> responseDtos = aircraftService.getAllAircraft(null, null);

        // Assert
        assertNotNull(responseDtos);
        assertEquals(2, responseDtos.size());
        verify(aircraftRepository).findAll();
    }

}
