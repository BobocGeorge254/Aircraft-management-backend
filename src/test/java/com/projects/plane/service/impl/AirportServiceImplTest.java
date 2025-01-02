package com.projects.plane.service.impl;

import com.projects.plane.dto.airport.AirportRequestDto;
import com.projects.plane.dto.airport.AirportResponseDto;
import com.projects.plane.exception.ResourceNotFoundException;
import com.projects.plane.mapper.AirportMapper;
import com.projects.plane.model.Airport;
import com.projects.plane.repository.AirportRepository;
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
class AirportServiceImplTest {

    @InjectMocks
    private AirportServiceImpl airportService;

    @Mock
    private AirportRepository airportRepository;

    @Test
    void whenCreateAirport_thenAirportIsSavedAndReturned() {
        // Arrange
        AirportRequestDto requestDto = new AirportRequestDto("Test Airport", "TST", "TEST", "Test Country", "Test City", 45.0, 90.0, 2);
        Airport airport = AirportMapper.mapToAirport(requestDto); // This converts requestDto to an Airport object
        Airport savedAirport = new Airport(UUID.randomUUID(), "Test Airport", "TST", "TEST", "Test Country", "Test City", 45.0, 90.0, 2, null);

        when(airportRepository.save(any(Airport.class))).thenReturn(savedAirport);

        // Act
        AirportResponseDto responseDto = airportService.createAirport(requestDto);

        // Assert
        assertNotNull(responseDto);
        assertEquals(savedAirport.getName(), responseDto.getName());
        verify(airportRepository).save(any(Airport.class));
    }


    @Test
    void whenUpdateAirport_withValidId_thenAirportIsUpdatedAndReturned() {
        // Arrange
        UUID airportId = UUID.randomUUID();
        AirportRequestDto requestDto = new AirportRequestDto("Updated Airport", "UPD", "UPDT", "Updated Country", "Updated City", 10.0, 20.0, 3);
        Airport existingAirport = new Airport(airportId, "Old Airport", "OLD", "OLDT", "Old Country", "Old City", 0.0, 0.0, 1, null);
        Airport updatedAirport = new Airport(airportId, "Updated Airport", "UPD", "UPDT", "Updated Country", "Updated City", 10.0, 20.0, 3, null);

        when(airportRepository.findById(airportId)).thenReturn(Optional.of(existingAirport));
        when(airportRepository.save(existingAirport)).thenReturn(updatedAirport);

        // Act
        AirportResponseDto responseDto = airportService.updateAirport(airportId, requestDto);

        // Assert
        assertNotNull(responseDto);
        assertEquals("Updated Airport", responseDto.getName());
        verify(airportRepository).findById(airportId);
        verify(airportRepository).save(existingAirport);
    }

    @Test
    void whenUpdateAirport_withInvalidId_thenThrowsResourceNotFoundException() {
        // Arrange
        UUID airportId = UUID.randomUUID();
        AirportRequestDto requestDto = new AirportRequestDto("Updated Airport", "UPD", "UPDT", "Updated Country", "Updated City", 10.0, 20.0, 3);

        when(airportRepository.findById(airportId)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> airportService.updateAirport(airportId, requestDto));
        assertEquals("Airport not found with ID: " + airportId, exception.getMessage());
        verify(airportRepository).findById(airportId);
        verify(airportRepository, never()).save(any(Airport.class));
    }

    @Test
    void whenGetAirportById_withValidId_thenReturnsAirport() {
        // Arrange
        UUID airportId = UUID.randomUUID();
        Airport airport = new Airport(airportId, "Test Airport", "TST", "TEST", "Test Country", "Test City", 45.0, 90.0, 2, null);

        when(airportRepository.findById(airportId)).thenReturn(Optional.of(airport));

        // Act
        AirportResponseDto responseDto = airportService.getAirportById(airportId);

        // Assert
        assertNotNull(responseDto);
        assertEquals(airport.getName(), responseDto.getName());
        verify(airportRepository).findById(airportId);
    }

    @Test
    void whenGetAirportById_withInvalidId_thenThrowsResourceNotFoundException() {
        // Arrange
        UUID airportId = UUID.randomUUID();

        when(airportRepository.findById(airportId)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> airportService.getAirportById(airportId));
        assertEquals("Airport not found with ID: " + airportId, exception.getMessage());
        verify(airportRepository).findById(airportId);
    }

    @Test
    void whenGetAllAirports_thenReturnsListOfAirports() {
        // Arrange
        List<Airport> airports = List.of(
                new Airport(UUID.randomUUID(), "Airport 1", "A1", "A111", "Country 1", "City 1", 10.0, 20.0, 2, null),
                new Airport(UUID.randomUUID(), "Airport 2", "A2", "A222", "Country 2", "City 2", 30.0, 40.0, 3, null)
        );

        when(airportRepository.findAll()).thenReturn(airports);

        // Act
        List<AirportResponseDto> response = airportService.getAllAirports();

        // Assert
        assertNotNull(response);
        assertEquals(airports.size(), response.size());
        verify(airportRepository).findAll();
    }

    @Test
    void whenDeleteAirport_withValidId_thenDeletesAirport() {
        // Arrange
        UUID airportId = UUID.randomUUID();
        when(airportRepository.existsById(airportId)).thenReturn(true);

        // Act
        airportService.deleteAirport(airportId);

        // Assert
        verify(airportRepository).existsById(airportId);
        verify(airportRepository).deleteById(airportId);
    }

    @Test
    void whenDeleteAirport_withInvalidId_thenThrowsResourceNotFoundException() {
        // Arrange
        UUID airportId = UUID.randomUUID();
        when(airportRepository.existsById(airportId)).thenReturn(false);

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> airportService.deleteAirport(airportId));
        assertEquals("Airport not found with ID: " + airportId, exception.getMessage());
        verify(airportRepository).existsById(airportId);
        verify(airportRepository, never()).deleteById(airportId);
    }
}
