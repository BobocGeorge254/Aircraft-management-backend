package com.projects.plane.service.impl;

import com.projects.plane.dto.airplane.AirplaneRequestDto;
import com.projects.plane.dto.airplane.AirplaneResponseDto;
import com.projects.plane.exception.ResourceNotFoundException;
import com.projects.plane.mapper.AirplaneMapper;
import com.projects.plane.model.Airplane;
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
class AirplaneServiceImplTest {

    @InjectMocks
    private AirplaneServiceImpl airplaneService;

    @Mock
    private AirplaneRepository airplaneRepository;

    @Test
    void whenCreateAirplane_thenAirplaneIsSavedAndReturned() {
        // Arrange
        AirplaneRequestDto requestDto = new AirplaneRequestDto("Boeing", "737", 200, 80000, 3000);
        Airplane airplane = AirplaneMapper.mapToAirplane(requestDto);
        Airplane savedAirplane = new Airplane(UUID.randomUUID(), "Boeing", "737", 200, 80000, 3000);

        when(airplaneRepository.save(any(Airplane.class))).thenReturn(savedAirplane);

        // Act
        AirplaneResponseDto responseDto = airplaneService.createAirplane(requestDto);

        // Assert
        assertNotNull(responseDto);
        assertEquals(savedAirplane.getManufacturer(), responseDto.getManufacturer());
        verify(airplaneRepository).save(any(Airplane.class));
    }

    @Test
    void whenUpdateAirplane_withValidId_thenAirplaneIsUpdatedAndReturned() {
        // Arrange
        UUID airplaneId = UUID.randomUUID();
        AirplaneRequestDto requestDto = new AirplaneRequestDto("Airbus", "A320", 150, 75000, 2500);
        Airplane existingAirplane = new Airplane(airplaneId, "Boeing", "737", 200, 80000, 3000);
        Airplane updatedAirplane = new Airplane(airplaneId, "Airbus", "A320", 150, 75000, 2500);

        when(airplaneRepository.findById(airplaneId)).thenReturn(Optional.of(existingAirplane));
        when(airplaneRepository.save(existingAirplane)).thenReturn(updatedAirplane);

        // Act
        AirplaneResponseDto responseDto = airplaneService.updateAirplane(airplaneId, requestDto);

        // Assert
        assertNotNull(responseDto);
        assertEquals("Airbus", responseDto.getManufacturer());
        verify(airplaneRepository).findById(airplaneId);
        verify(airplaneRepository).save(existingAirplane);
    }

    @Test
    void whenUpdateAirplane_withInvalidId_thenThrowsResourceNotFoundException() {
        // Arrange
        UUID airplaneId = UUID.randomUUID();
        AirplaneRequestDto requestDto = new AirplaneRequestDto("Airbus", "A320", 150, 75000, 2500);

        when(airplaneRepository.findById(airplaneId)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> airplaneService.updateAirplane(airplaneId, requestDto));
        assertEquals("Airplane not found with ID: " + airplaneId, exception.getMessage());
        verify(airplaneRepository).findById(airplaneId);
        verify(airplaneRepository, never()).save(any(Airplane.class));
    }

    @Test
    void whenGetAirplaneById_withValidId_thenReturnsAirplane() {
        // Arrange
        UUID airplaneId = UUID.randomUUID();
        Airplane airplane = new Airplane(airplaneId, "Boeing", "737", 200, 80000, 3000);

        when(airplaneRepository.findById(airplaneId)).thenReturn(Optional.of(airplane));

        // Act
        AirplaneResponseDto responseDto = airplaneService.getAirplaneById(airplaneId);

        // Assert
        assertNotNull(responseDto);
        assertEquals(airplane.getManufacturer(), responseDto.getManufacturer());
        verify(airplaneRepository).findById(airplaneId);
    }

    @Test
    void whenGetAirplaneById_withInvalidId_thenThrowsResourceNotFoundException() {
        // Arrange
        UUID airplaneId = UUID.randomUUID();

        when(airplaneRepository.findById(airplaneId)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> airplaneService.getAirplaneById(airplaneId));
        assertEquals("Airplane not found with ID: " + airplaneId, exception.getMessage());
        verify(airplaneRepository).findById(airplaneId);
    }

    @Test
    void whenGetAllAirplanes_thenReturnsListOfAirplanes() {
        // Arrange
        List<Airplane> airplanes = List.of(
                new Airplane(UUID.randomUUID(), "Boeing", "737", 200, 80000, 3000),
                new Airplane(UUID.randomUUID(), "Airbus", "A320", 150, 75000, 2500)
        );

        when(airplaneRepository.findAll()).thenReturn(airplanes);

        // Act
        List<AirplaneResponseDto> response = airplaneService.getAllAirplanes();

        // Assert
        assertNotNull(response);
        assertEquals(airplanes.size(), response.size());
        verify(airplaneRepository).findAll();
    }

    @Test
    void whenDeleteAirplane_withValidId_thenDeletesAirplane() {
        // Arrange
        UUID airplaneId = UUID.randomUUID();
        when(airplaneRepository.existsById(airplaneId)).thenReturn(true);

        // Act
        airplaneService.deleteAirplane(airplaneId);

        // Assert
        verify(airplaneRepository).existsById(airplaneId);
        verify(airplaneRepository).deleteById(airplaneId);
    }

    @Test
    void whenDeleteAirplane_withInvalidId_thenThrowsResourceNotFoundException() {
        // Arrange
        UUID airplaneId = UUID.randomUUID();
        when(airplaneRepository.existsById(airplaneId)).thenReturn(false);

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> airplaneService.deleteAirplane(airplaneId));
        assertEquals("Airplane not found with ID: " + airplaneId, exception.getMessage());
        verify(airplaneRepository).existsById(airplaneId);
        verify(airplaneRepository, never()).deleteById(airplaneId);
    }
}
