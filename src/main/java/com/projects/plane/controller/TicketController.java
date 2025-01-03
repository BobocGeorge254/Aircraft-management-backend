package com.projects.plane.controller;

import com.projects.plane.dto.ticket.TicketRequestDto;
import com.projects.plane.dto.ticket.TicketResponseDto;
import com.projects.plane.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/ticket")
@Tag(name = "Ticket", description = "Endpoints for managing ticket data.")
public class TicketController {

    private TicketService ticketService;

    @PostMapping
    @Operation(summary = "Create a new ticket.")
    public ResponseEntity<TicketResponseDto> createTicket(@Valid @RequestBody TicketRequestDto ticketRequestDto) {
        TicketResponseDto savedTicket = ticketService.createTicket(ticketRequestDto);
        return new ResponseEntity<>(savedTicket, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing ticket.")
    public ResponseEntity<TicketResponseDto> updateTicket(@PathVariable("id") UUID id, @Valid @RequestBody TicketRequestDto ticketRequestDto) {
        TicketResponseDto updatedTicket = ticketService.updateTicket(id, ticketRequestDto);
        return new ResponseEntity<>(updatedTicket, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a ticket by ID.")
    public ResponseEntity<TicketResponseDto> getTicketById(@PathVariable("id") UUID id) {
        TicketResponseDto ticket = ticketService.getTicketById(id);
        return new ResponseEntity<>(ticket, HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Get all tickets with filters.")
    public ResponseEntity<List<TicketResponseDto>> getAllTickets(
            @RequestParam(value = "passengerName", required = false) String passengerName
    ) {
        List<TicketResponseDto> tickets = ticketService.getAllTickets(passengerName);
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a ticket.")
    public ResponseEntity<Void> deleteTicket(@PathVariable("id") UUID id) {
        ticketService.deleteTicket(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
