package com.projects.plane.service.impl;

import com.projects.plane.dto.airline.AirlineRequestDto;
import com.projects.plane.dto.airline.AirlineResponseDto;
import com.projects.plane.mapper.AirlineMapper;
import com.projects.plane.model.Airline;
import com.projects.plane.model.Airport;
import com.projects.plane.model.enums.AirlineOperatingStatus;
import com.projects.plane.repository.AirlineRepository;
import com.projects.plane.exception.ResourceNotFoundException;
import com.projects.plane.repository.AirportRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AirlineServiceImplTest {

    @InjectMocks
    private AirlineServiceImpl airlineService;

    @Mock
    private AirlineRepository airlineRepository;

    @Mock
    private AirportRepository airportRepository;

    @Test
    void whenCreateAirline_thenAirlineIsSavedAndReturned() {
        // Arrange
        AirlineRequestDto requestDto = new AirlineRequestDto("Test Airline", "TST", "TSTC", "Test Country", "Test HQ", null, 2000);
        Airline airline = AirlineMapper.mapToAirline(requestDto); // Convert request DTO to Airline entity
        Airline savedAirline = new Airline(UUID.randomUUID(), "Test Airline", "TST", "TSTC", "Test Country", "Test HQ", null, 2000, null);

        when(airlineRepository.save(any(Airline.class))).thenReturn(savedAirline);

        // Act
        AirlineResponseDto responseDto = airlineService.createAirline(requestDto);

        // Assert
        assertNotNull(responseDto);
        assertEquals(savedAirline.getName(), responseDto.getName());
        verify(airlineRepository).save(any(Airline.class));
    }

    @Test
    void whenUpdateAirline_withValidId_thenAirlineIsUpdatedAndReturned() {
        // Arrange
        UUID airlineId = UUID.randomUUID();
        AirlineRequestDto requestDto = new AirlineRequestDto("Updated Airline", "UPD", "UPDC", "Updated Country", "Updated HQ", null, 2020);
        Airline existingAirline = new Airline(airlineId, "Old Airline", "OLD", "OLDC", "Old Country", "Old HQ", null, 2000, null);
        Airline updatedAirline = new Airline(airlineId, "Updated Airline", "UPD", "UPDC", "Updated Country", "Updated HQ", null, 2020, null);

        when(airlineRepository.findById(airlineId)).thenReturn(Optional.of(existingAirline));
        when(airlineRepository.save(existingAirline)).thenReturn(updatedAirline);

        // Act
        AirlineResponseDto responseDto = airlineService.updateAirline(airlineId, requestDto);

        // Assert
        assertNotNull(responseDto);
        assertEquals("Updated Airline", responseDto.getName());
        verify(airlineRepository).findById(airlineId);
        verify(airlineRepository).save(existingAirline);
    }

    @Test
    void whenUpdateAirline_withInvalidId_thenThrowsResourceNotFoundException() {
        // Arrange
        UUID airlineId = UUID.randomUUID();
        AirlineRequestDto requestDto = new AirlineRequestDto("Updated Airline", "UPD", "UPDC", "Updated Country", "Updated HQ", null, 2020);

        when(airlineRepository.findById(airlineId)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> airlineService.updateAirline(airlineId, requestDto));
        assertEquals("Airline not found with ID: " + airlineId, exception.getMessage());
        verify(airlineRepository).findById(airlineId);
        verify(airlineRepository, never()).save(any(Airline.class));
    }

    @Test
    void whenGetAirlineById_withValidId_thenReturnsAirline() {
        // Arrange
        UUID airlineId = UUID.randomUUID();
        Airline airline = new Airline(airlineId, "Test Airline", "TST", "TSTC", "Test Country", "Test HQ", null, 2000, null);

        when(airlineRepository.findById(airlineId)).thenReturn(Optional.of(airline));

        // Act
        AirlineResponseDto responseDto = airlineService.getAirlineById(airlineId);

        // Assert
        assertNotNull(responseDto);
        assertEquals(airline.getName(), responseDto.getName());
        verify(airlineRepository).findById(airlineId);
    }

    @Test
    void whenGetAirlineById_withInvalidId_thenThrowsResourceNotFoundException() {
        // Arrange
        UUID airlineId = UUID.randomUUID();

        when(airlineRepository.findById(airlineId)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> airlineService.getAirlineById(airlineId));
        assertEquals("Airline not found with ID: " + airlineId, exception.getMessage());
        verify(airlineRepository).findById(airlineId);
    }

    @Test
    void whenDeleteAirline_withValidId_thenDeletesAirline() {
        // Arrange
        UUID airlineId = UUID.randomUUID();
        when(airlineRepository.existsById(airlineId)).thenReturn(true);

        // Act
        airlineService.deleteAirline(airlineId);

        // Assert
        verify(airlineRepository).existsById(airlineId);
        verify(airlineRepository).deleteById(airlineId);
    }

    @Test
    void whenDeleteAirline_withInvalidId_thenThrowsResourceNotFoundException() {
        // Arrange
        UUID airlineId = UUID.randomUUID();
        when(airlineRepository.existsById(airlineId)).thenReturn(false);

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> airlineService.deleteAirline(airlineId));
        assertEquals("Airline not found with ID: " + airlineId, exception.getMessage());
        verify(airlineRepository).existsById(airlineId);
        verify(airlineRepository, never()).deleteById(airlineId);
    }

    @Test
    void whenAddHub_thenHubIsAddedToAirline() {
        // Arrange
        UUID airlineId = UUID.randomUUID();
        UUID airportId = UUID.randomUUID();

        Airline airline = new Airline(airlineId, "American Airlines", "AA", "AAL", "United States", "Fort Worth, Texas", AirlineOperatingStatus.ACTIVE, 1930, new ArrayList<>());
        Airport airport = new Airport(airportId, "Airport A", "AIA", "AIA", "Country", "City", 50.0, 50.0, 2, null);

        when(airlineRepository.findById(airlineId)).thenReturn(Optional.of(airline));
        when(airportRepository.findById(airportId)).thenReturn(Optional.of(airport));

        // Act
        airlineService.addHubToAirline(airlineId, airportId);

        // Assert
        assertTrue(airline.getHubs().contains(airport));
        verify(airlineRepository).findById(airlineId);
        verify(airportRepository).findById(airportId);
        verify(airlineRepository).save(airline);
    }


    @Test
    void whenAddHub_withInvalidAirlineId_thenThrowsResourceNotFoundException() {
        // Arrange
        UUID airlineId = UUID.randomUUID();
        UUID airportId = UUID.randomUUID();

        when(airlineRepository.findById(airlineId)).thenReturn(java.util.Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> airlineService.addHubToAirline(airlineId, airportId));
        assertEquals("Airline not found with ID: " + airlineId, exception.getMessage());
        verify(airlineRepository).findById(airlineId);
        verify(airportRepository, never()).findById(any(UUID.class));
        verify(airlineRepository, never()).save(any(Airline.class));
    }

    @Test
    void whenAddHub_withInvalidAirportId_thenThrowsResourceNotFoundException() {
        // Arrange
        UUID airlineId = UUID.randomUUID();
        UUID airportId = UUID.randomUUID();

        Airline airline = new Airline(airlineId, "American Airlines", "AA", "AAL", "United States", "Fort Worth, Texas", AirlineOperatingStatus.ACTIVE, 1930, null);

        when(airlineRepository.findById(airlineId)).thenReturn(java.util.Optional.of(airline));
        when(airportRepository.findById(airportId)).thenReturn(java.util.Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> airlineService.addHubToAirline(airlineId, airportId));
        assertEquals("Airport not found with ID: " + airportId, exception.getMessage());
        verify(airlineRepository).findById(airlineId);
        verify(airportRepository).findById(airportId);
        verify(airlineRepository, never()).save(any(Airline.class));
    }

}
