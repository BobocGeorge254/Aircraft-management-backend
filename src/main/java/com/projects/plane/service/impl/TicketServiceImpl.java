package com.projects.plane.service.impl;

import com.projects.plane.dto.ticket.TicketRequestDto;
import com.projects.plane.dto.ticket.TicketResponseDto;
import com.projects.plane.exception.ResourceNotFoundException;
import com.projects.plane.mapper.TicketMapper;
import com.projects.plane.model.Ticket;
import com.projects.plane.model.Flight;
import com.projects.plane.repository.TicketRepository;
import com.projects.plane.repository.FlightRepository;
import com.projects.plane.service.TicketService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TicketServiceImpl implements TicketService {

    private TicketRepository ticketRepository;
    private FlightRepository flightRepository;

    @Override
    public TicketResponseDto createTicket(TicketRequestDto ticketRequestDto) {
        Flight flight = flightRepository.findById(ticketRequestDto.getFlightId())
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with ID: " + ticketRequestDto.getFlightId()));

        Ticket ticket = TicketMapper.mapToTicket(ticketRequestDto, flight);
        Ticket savedTicket = ticketRepository.save(ticket);

        return TicketMapper.mapToTicketResponseDto(savedTicket);
    }

    @Override
    public TicketResponseDto updateTicket(UUID id, TicketRequestDto dto) {
        Ticket existingTicket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with ID: " + id));

        Flight flight = flightRepository.findById(dto.getFlightId())
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with ID: " + dto.getFlightId()));

        existingTicket.setPassengerName(dto.getPassengerName());
        existingTicket.setSeatNumber(dto.getSeatNumber());
        existingTicket.setTicketClass(dto.getTicketClass());
        existingTicket.setPaymentStatus(dto.getPaymentStatus());

        existingTicket.setFlight(flight);

        Ticket updatedTicket = ticketRepository.save(existingTicket);

        return TicketMapper.mapToTicketResponseDto(updatedTicket);
    }

    @Override
    public TicketResponseDto getTicketById(UUID id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with ID: " + id));

        return TicketMapper.mapToTicketResponseDto(ticket);
    }

    @Override
    public List<TicketResponseDto> getAllTickets(String passengerName) {
        List<Ticket> tickets;

        if (passengerName != null && !passengerName.isEmpty()) {
            tickets = ticketRepository.findByPassengerName(passengerName);
        }
        else {
            tickets = ticketRepository.findAll();
        }

        return tickets.stream()
                .map(TicketMapper::mapToTicketResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteTicket(UUID id) {
        if (!ticketRepository.existsById(id)) {
            throw new ResourceNotFoundException("Ticket not found with ID: " + id);
        }
        ticketRepository.deleteById(id);
    }
}
