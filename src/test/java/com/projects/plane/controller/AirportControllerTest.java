package com.projects.plane.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.plane.dto.airport.AirportRequestDto;
import com.projects.plane.dto.airport.AirportResponseDto;
import com.projects.plane.service.AirportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AirportController.class)
class AirportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AirportService airportService;

    @Autowired
    private ObjectMapper objectMapper;

    private AirportRequestDto requestDto;
    private AirportResponseDto responseDto;
    private UUID airportId;

    @BeforeEach
    void setUp() {
        airportId = UUID.randomUUID();
        requestDto = new AirportRequestDto("London Heathrow Airport", "LHR", "EGLL", "United Kingdom", "London", 51.4700, -0.4543, 2);
        responseDto = new AirportResponseDto(airportId, "London Heathrow Airport", "LHR", "EGLL", "United Kingdom", "London", 51.4700, -0.4543, 2, new ArrayList<>());
    }

    @Test
    void createAirport_ShouldReturnCreatedStatus() throws Exception {
        when(airportService.createAirport(any(AirportRequestDto.class))).thenReturn(responseDto);
        mockMvc.perform(post("/api/airport")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("London Heathrow Airport"))
                .andExpect(jsonPath("$.iataCode").value("LHR"))
                .andExpect(jsonPath("$.icaoCode").value("EGLL"));
    }

    @Test
    void createAirport_WithInvalidData_ShouldReturnBadRequest() throws Exception {
        AirportRequestDto invalidDto = new AirportRequestDto("A", "INVALID", "INV", "", "", 91.0, 181.0, 0);
        mockMvc.perform(post("/api/airport")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateAirport_ShouldReturnOkStatus() throws Exception {
        when(airportService.updateAirport(any(UUID.class), any(AirportRequestDto.class))).thenReturn(responseDto);
        mockMvc.perform(put("/api/airport/" + airportId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("London Heathrow Airport"));
        verify(airportService).updateAirport(eq(airportId), any(AirportRequestDto.class));
    }

    @Test
    void getAirportById_ShouldReturnOkStatus() throws Exception {
        when(airportService.getAirportById(airportId)).thenReturn(responseDto);
        mockMvc.perform(get("/api/airport/" + airportId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.iataCode").value("LHR"));
        verify(airportService).getAirportById(airportId);
    }

    @Test
    void getAllAirports_ShouldReturnOkStatus() throws Exception {
        when(airportService.getAllAirports()).thenReturn(Arrays.asList(responseDto));
        mockMvc.perform(get("/api/airport"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].iataCode").value("LHR"));
        verify(airportService).getAllAirports();
    }

    @Test
    void deleteAirport_ShouldReturnNoContentStatus() throws Exception {
        doNothing().when(airportService).deleteAirport(airportId);
        mockMvc.perform(delete("/api/airport/" + airportId))
                .andExpect(status().isNoContent());
        verify(airportService).deleteAirport(airportId);
    }

    @Test
    void createAirport_WithInvalidCoordinates_ShouldReturnBadRequest() throws Exception {
        AirportRequestDto invalidDto = new AirportRequestDto("Test Airport", "TST", "TEST", "Test Country", "Test City", -91.0, -181.0, 1);
        mockMvc.perform(post("/api/airport")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }
}
