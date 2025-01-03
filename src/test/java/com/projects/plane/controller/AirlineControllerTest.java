package com.projects.plane.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.plane.dto.airline.AirlineRequestDto;
import com.projects.plane.dto.airline.AirlineResponseDto;
import com.projects.plane.model.enums.AirlineOperatingStatus;
import com.projects.plane.service.AirlineService;
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

@WebMvcTest(AirlineController.class)
class AirlineControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AirlineService airlineService;

    @Autowired
    private ObjectMapper objectMapper;

    private AirlineRequestDto requestDto;
    private AirlineResponseDto responseDto;
    private UUID airlineId;

    @BeforeEach
    void setUp() {
        airlineId = UUID.randomUUID();

        requestDto = new AirlineRequestDto("British Airways", "BA", "BAW", "United Kingdom", "London Waterside", AirlineOperatingStatus.ACTIVE, 1974);
        responseDto = new AirlineResponseDto(airlineId, "British Airways", "BA", "BAW", "United Kingdom", "London Waterside", AirlineOperatingStatus.ACTIVE, 1974, new ArrayList<>());
    }

    @Test
    void createAirline_ShouldReturnCreatedStatus() throws Exception {
        when(airlineService.createAirline(any(AirlineRequestDto.class)))
                .thenReturn(responseDto);

        mockMvc.perform(post("/api/airline")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(airlineId.toString()))
                .andExpect(jsonPath("$.name").value("British Airways"))
                .andExpect(jsonPath("$.iataCode").value("BA"))
                .andExpect(jsonPath("$.icaoCode").value("BAW"))
                .andExpect(jsonPath("$.operatingStatus").value("ACTIVE"));

        verify(airlineService).createAirline(any(AirlineRequestDto.class));
    }

    @Test
    void createAirline_WithInvalidIataCodes_ShouldReturnBadRequest() throws Exception {
        requestDto.setIataCode("INVALID");

        mockMvc.perform(post("/api/airline")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createAirline_WithInvalidIcaoCodes_ShouldReturnBadRequest() throws Exception {
        requestDto.setIcaoCode("IN");

        mockMvc.perform(post("/api/airline")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateAirline_ShouldReturnOkStatus() throws Exception {
        when(airlineService.updateAirline(any(UUID.class), any(AirlineRequestDto.class)))
                .thenReturn(responseDto);

        mockMvc.perform(put("/api/airline/" + airlineId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("British Airways"))
                .andExpect(jsonPath("$.country").value("United Kingdom"));

        verify(airlineService).updateAirline(eq(airlineId), any(AirlineRequestDto.class));
    }

    @Test
    void getAirlineById_ShouldReturnOkStatus() throws Exception {
        when(airlineService.getAirlineById(airlineId))
                .thenReturn(responseDto);

        mockMvc.perform(get("/api/airline/" + airlineId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(airlineId.toString()))
                .andExpect(jsonPath("$.name").value("British Airways"));

        verify(airlineService).getAirlineById(airlineId);
    }

    @Test
    void getAllAirlines_ShouldReturnOkStatus() throws Exception {
        when(airlineService.getAllAirlines())
                .thenReturn(Arrays.asList(responseDto));

        mockMvc.perform(get("/api/airline"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(airlineId.toString()))
                .andExpect(jsonPath("$[0].name").value("British Airways"));

        verify(airlineService).getAllAirlines();
    }

    @Test
    void deleteAirline_ShouldReturnNoContentStatus() throws Exception {
        doNothing().when(airlineService).deleteAirline(airlineId);

        mockMvc.perform(delete("/api/airline/" + airlineId))
                .andExpect(status().isNoContent());

        verify(airlineService).deleteAirline(airlineId);
    }

    @Test
    void createAirline_WithNullRequiredFields_ShouldReturnBadRequest() throws Exception {
        AirlineRequestDto invalidDto = new AirlineRequestDto();
        invalidDto.setName(null);
        invalidDto.setCountry(null);

        mockMvc.perform(post("/api/airline")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }
}