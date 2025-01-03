package com.projects.plane.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.plane.dto.aircraft.AircraftRequestDto;
import com.projects.plane.dto.aircraft.AircraftResponseDto;
import com.projects.plane.model.enums.AircraftOperatingStatus;
import com.projects.plane.service.AircraftService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AircraftController.class)
class AircraftControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AircraftService aircraftService;

    @Autowired
    private ObjectMapper objectMapper;

    private AircraftRequestDto requestDto;
    private AircraftResponseDto responseDto;
    private UUID aircraftId;
    private UUID airlineId;
    private UUID airplaneId;

    @BeforeEach
    void setUp() {
        aircraftId = UUID.randomUUID();
        airlineId = UUID.randomUUID();
        airplaneId = UUID.randomUUID();

        requestDto = new AircraftRequestDto(airlineId, airplaneId, "G-ZBKF", AircraftOperatingStatus.OPERATIONAL);
        responseDto = new AircraftResponseDto(aircraftId, "G-ZBKF", AircraftOperatingStatus.OPERATIONAL, "British Airways", "BA", "BAW", "Boeing", "787-9");
    }

    @Test
    void createAircraft_ShouldReturnCreatedStatus() throws Exception {
        when(aircraftService.createAircraft(any(AircraftRequestDto.class)))
                .thenReturn(responseDto);

        mockMvc.perform(post("/api/aircraft")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.registrationNumber").value("G-ZBKF"))
                .andExpect(jsonPath("$.aircraftOperatingStatus").value("OPERATIONAL"));
    }

    @Test
    void createAircraft_WithInvalidRegistration_ShouldReturnBadRequest() throws Exception {
        AircraftRequestDto invalidDto = new AircraftRequestDto(
                airlineId,
                airplaneId,
                "",
                AircraftOperatingStatus.OPERATIONAL
        );

        mockMvc.perform(post("/api/aircraft")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateAircraft_ShouldReturnOkStatus() throws Exception {
        when(aircraftService.updateAircraft(any(UUID.class), any(AircraftRequestDto.class)))
                .thenReturn(responseDto);

        mockMvc.perform(put("/api/aircraft/" + aircraftId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.registrationNumber").value("G-ZBKF"));

        verify(aircraftService).updateAircraft(eq(aircraftId), any(AircraftRequestDto.class));
    }

    @Test
    void getAircraftById_ShouldReturnOkStatus() throws Exception {
        when(aircraftService.getAircraftById(aircraftId))
                .thenReturn(responseDto);

        mockMvc.perform(get("/api/aircraft/" + aircraftId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.registrationNumber").value("G-ZBKF"))
                .andExpect(jsonPath("$.airlineName").value("British Airways"));

        verify(aircraftService).getAircraftById(aircraftId);
    }

    @Test
    void getAllAircraft_ShouldReturnOkStatus() throws Exception {
        when(aircraftService.getAllAircraft(null, null))
                .thenReturn(Arrays.asList(responseDto));

        mockMvc.perform(get("/api/aircraft"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].registrationNumber").value("G-ZBKF"))
                .andExpect(jsonPath("$[0].airplaneModel").value("787-9"));

        verify(aircraftService).getAllAircraft(null, null);
    }

    @Test
    void deleteAircraft_ShouldReturnNoContentStatus() throws Exception {
        doNothing().when(aircraftService).deleteAircraft(aircraftId);

        mockMvc.perform(delete("/api/aircraft/" + aircraftId))
                .andExpect(status().isNoContent());

        verify(aircraftService).deleteAircraft(aircraftId);
    }

    @Test
    void createAircraft_WithNullRequiredFields_ShouldReturnBadRequest() throws Exception {
        AircraftRequestDto invalidDto = new AircraftRequestDto(
                null,
                null,
                null,
                null
        );

        mockMvc.perform(post("/api/aircraft")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }
}