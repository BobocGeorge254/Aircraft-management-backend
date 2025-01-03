package com.projects.plane.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.plane.dto.aircraft.AircraftResponseDto;
import com.projects.plane.dto.airline.AirlineResponseDto;
import com.projects.plane.dto.airplane.AirplaneResponseDto;
import com.projects.plane.dto.airport.AirportRequestDto;
import com.projects.plane.dto.airport.AirportResponseDto;
import com.projects.plane.dto.airport.FlightAirportDto;
import com.projects.plane.dto.flight.FlightRequestDto;
import com.projects.plane.dto.flight.FlightResponseDto;
import com.projects.plane.model.Aircraft;
import com.projects.plane.model.Airline;
import com.projects.plane.model.Airplane;
import com.projects.plane.model.Airport;
import com.projects.plane.model.enums.AircraftOperatingStatus;
import com.projects.plane.model.enums.AirlineOperatingStatus;
import com.projects.plane.model.enums.FlightStatus;
import com.projects.plane.service.FlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FlightController.class)
class FlightControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FlightService flightService;

    @Autowired
    private ObjectMapper objectMapper;

    private FlightRequestDto requestDto;
    private FlightResponseDto responseDto;
    private UUID flightId, aircraftId, departureAirportId, arrivalAirportId;
    private LocalDateTime departureTime, arrivalTime;

    @BeforeEach
    void setUp() {
        flightId = UUID.randomUUID();
        aircraftId = UUID.randomUUID();
        departureAirportId = UUID.randomUUID();
        arrivalAirportId = UUID.randomUUID();
        departureTime = LocalDateTime.now().plusHours(2);
        arrivalTime = LocalDateTime.now().plusHours(5);

        AircraftResponseDto aircraft = new AircraftResponseDto(aircraftId, "KF-424", AircraftOperatingStatus.OPERATIONAL, "Old Airline", "OLD", "OLDC", "Boeing", "737 MAX 8");
        FlightAirportDto departureAirport = new FlightAirportDto("London Heathrow", "LHR", "EGLL", "United Kingdom");
        FlightAirportDto arrivalAirport = new FlightAirportDto("New York JFK", "JFK", "KJFK", "United States");

        requestDto = new FlightRequestDto(aircraftId, departureAirportId, arrivalAirportId, "BA123", departureTime, arrivalTime, FlightStatus.SCHEDULED);
        responseDto = new FlightResponseDto(flightId, aircraft, departureAirport, arrivalAirport, "BA123", departureTime, arrivalTime, FlightStatus.SCHEDULED);
    }

    @Test
    void createFlight_ShouldReturnCreatedStatus() throws Exception {
        when(flightService.createFlight(any(FlightRequestDto.class))).thenReturn(responseDto);
        mockMvc.perform(post("/api/flight")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.flightNumber").value("BA123"))
                .andExpect(jsonPath("$.flightStatus").value("SCHEDULED"));
    }

    @Test
    void createFlight_WithPastDepartureTime_ShouldReturnBadRequest() throws Exception {
        FlightRequestDto invalidDto = new FlightRequestDto(aircraftId, departureAirportId, arrivalAirportId, "BA123", LocalDateTime.now().minusHours(1), arrivalTime, FlightStatus.SCHEDULED);
        mockMvc.perform(post("/api/flight")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createFlight_WithInvalidFlightNumber_ShouldReturnBadRequest() throws Exception {
        FlightRequestDto invalidDto = new FlightRequestDto(aircraftId, departureAirportId, arrivalAirportId, "A", departureTime, arrivalTime, FlightStatus.SCHEDULED);
        mockMvc.perform(post("/api/flight")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateFlight_ShouldReturnOkStatus() throws Exception {
        when(flightService.updateFlight(any(UUID.class), any(FlightRequestDto.class))).thenReturn(responseDto);
        mockMvc.perform(put("/api/flight/" + flightId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flightNumber").value("BA123"));
        verify(flightService).updateFlight(eq(flightId), any(FlightRequestDto.class));
    }

    @Test
    void getFlightById_ShouldReturnOkStatus() throws Exception {
        when(flightService.getFlightById(flightId)).thenReturn(responseDto);
        mockMvc.perform(get("/api/flight/" + flightId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flightNumber").value("BA123"));
        verify(flightService).getFlightById(flightId);
    }

    @Test
    void getAllFlights_ShouldReturnOkStatus() throws Exception {
        when(flightService.getAllFlights(null, null, null)).thenReturn(Arrays.asList(responseDto));
        mockMvc.perform(get("/api/flight"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].flightNumber").value("BA123"));
        verify(flightService).getAllFlights(null, null, null);
    }

    @Test
    void deleteFlight_ShouldReturnNoContentStatus() throws Exception {
        doNothing().when(flightService).deleteFlight(flightId);
        mockMvc.perform(delete("/api/flight/" + flightId))
                .andExpect(status().isNoContent());
        verify(flightService).deleteFlight(flightId);
    }

    @Test
    void createFlight_WithNullRequiredFields_ShouldReturnBadRequest() throws Exception {
        FlightRequestDto invalidDto = new FlightRequestDto(null, null, null, null, null, null, null);
        mockMvc.perform(post("/api/flight")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }
}
