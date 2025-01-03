package com.projects.plane.mapper;

import com.projects.plane.dto.ticket.*;
import com.projects.plane.model.Ticket;
import com.projects.plane.model.Flight;

public class TicketMapper {

    public static TicketResponseDto mapToTicketResponseDto(Ticket ticket) {
        return new TicketResponseDto(
                ticket.getId(),
                FlightMapper.mapToFlightResponseDto(ticket.getFlight()),
                ticket.getPassengerName(),
                ticket.getSeatNumber(),
                ticket.getTicketClass(),
                ticket.getPrice(),
                ticket.getPurchaseDate(),
                ticket.getStatus(),
                ticket.getPaymentStatus()
        );
    }

    public static Ticket mapToTicket(TicketRequestDto ticketRequestDto, Flight flight) {
        return new Ticket(
                null,
                flight,
                ticketRequestDto.getPassengerName(),
                ticketRequestDto.getSeatNumber(),
                ticketRequestDto.getTicketClass(),
                ticketRequestDto.getPrice(),
                ticketRequestDto.getPurchaseDate(),
                ticketRequestDto.getStatus(),
                ticketRequestDto.getPaymentStatus()
        );
    }
}
