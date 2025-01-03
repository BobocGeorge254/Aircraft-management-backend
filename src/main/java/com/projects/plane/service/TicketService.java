package com.projects.plane.service;

import com.projects.plane.dto.ticket.TicketRequestDto;
import com.projects.plane.dto.ticket.TicketResponseDto;
import com.projects.plane.model.Ticket;

import java.util.List;
import java.util.UUID;

public interface TicketService {
    TicketResponseDto createTicket(TicketRequestDto dto);
    TicketResponseDto updateTicket(UUID id, TicketRequestDto dto);
    TicketResponseDto getTicketById(UUID id);
    List<TicketResponseDto> getAllTickets(String passengerName);
    void deleteTicket(UUID id);
}
