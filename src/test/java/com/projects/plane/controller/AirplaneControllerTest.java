package com.projects.plane.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.plane.dto.airplane.AirplaneRequestDto;
import com.projects.plane.dto.airplane.AirplaneResponseDto;
import com.projects.plane.service.AirplaneService;
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

@WebMvcTest(AirplaneController.class)
class AirplaneControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AirplaneService airplaneService;

    @Autowired
    private ObjectMapper objectMapper;

    private AirplaneRequestDto requestDto;
    private AirplaneResponseDto responseDto;
    private UUID airplaneId;

    @BeforeEach
    void setUp() {
        airplaneId = UUID.randomUUID();

        requestDto = new AirplaneRequestDto("Boeing", "747-400", 416, 396890, 13450);
        responseDto = new AirplaneResponseDto(airplaneId, "Boeing", "747-400", 416, 396890, 13450);
    }

    @Test
    void createAirplane_ShouldReturnCreatedStatus() throws Exception {
        when(airplaneService.createAirplane(any(AirplaneRequestDto.class)))
                .thenReturn(responseDto);

        mockMvc.perform(post("/api/airplane")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(airplaneId.toString()))
                .andExpect(jsonPath("$.manufacturer").value("Boeing"))
                .andExpect(jsonPath("$.model").value("747-400"))
                .andExpect(jsonPath("$.seatingCapacity").value(416))
                .andExpect(jsonPath("$.maximumTakeoffWeight").value(396890))
                .andExpect(jsonPath("$.range").value(13450));

        verify(airplaneService).createAirplane(any(AirplaneRequestDto.class));
    }

    @Test
    void updateAirplane_ShouldReturnOkStatus() throws Exception {
        when(airplaneService.updateAirplane(any(UUID.class), any(AirplaneRequestDto.class)))
                .thenReturn(responseDto);

        mockMvc.perform(put("/api/airplane/" + airplaneId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(airplaneId.toString()))
                .andExpect(jsonPath("$.manufacturer").value("Boeing"))
                .andExpect(jsonPath("$.model").value("747-400"));

        verify(airplaneService).updateAirplane(eq(airplaneId), any(AirplaneRequestDto.class));
    }

    @Test
    void getAirplaneById_ShouldReturnOkStatus() throws Exception {
        when(airplaneService.getAirplaneById(airplaneId))
                .thenReturn(responseDto);

        mockMvc.perform(get("/api/airplane/" + airplaneId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(airplaneId.toString()))
                .andExpect(jsonPath("$.manufacturer").value("Boeing"))
                .andExpect(jsonPath("$.model").value("747-400"))
                .andExpect(jsonPath("$.seatingCapacity").value(416));

        verify(airplaneService).getAirplaneById(airplaneId);
    }

    @Test
    void getAllAirplanes_ShouldReturnOkStatus() throws Exception {
        when(airplaneService.getAllAirplanes())
                .thenReturn(Arrays.asList(responseDto));

        mockMvc.perform(get("/api/airplane"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(airplaneId.toString()))
                .andExpect(jsonPath("$[0].manufacturer").value("Boeing"))
                .andExpect(jsonPath("$[0].model").value("747-400"));

        verify(airplaneService).getAllAirplanes();
    }

    @Test
    void deleteAirplane_ShouldReturnNoContentStatus() throws Exception {
        doNothing().when(airplaneService).deleteAirplane(airplaneId);

        mockMvc.perform(delete("/api/airplane/" + airplaneId))
                .andExpect(status().isNoContent());

        verify(airplaneService).deleteAirplane(airplaneId);
    }

    @Test
    void createAirplane_WithInvalidData_ShouldReturnBadRequest() throws Exception {
        AirplaneRequestDto invalidDto = new AirplaneRequestDto(
                "",  // invalid manufacturer
                "",  // invalid model
                0,   // invalid seating capacity
                0,   // invalid takeoff weight
                0    // invalid range
        );

        mockMvc.perform(post("/api/airplane")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createAirplane_WithNullData_ShouldReturnBadRequest() throws Exception {
        AirplaneRequestDto invalidDto = new AirplaneRequestDto(
                null,  // null manufacturer
                null,  // null model
                null,  // null seating capacity
                null,  // null takeoff weight
                null   // null range
        );

        mockMvc.perform(post("/api/airplane")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }
}