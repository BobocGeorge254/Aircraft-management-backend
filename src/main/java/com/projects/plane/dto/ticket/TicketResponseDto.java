package com.projects.plane.dto.ticket;

import com.projects.plane.dto.flight.FlightResponseDto;
import com.projects.plane.model.enums.TicketClass;
import com.projects.plane.model.enums.TicketStatus;
import com.projects.plane.model.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponseDto {

    private UUID id;
    private FlightResponseDto flight;
    private String passengerName;
    private String seatNumber;
    private TicketClass ticketClass;
    private BigDecimal price;
    private LocalDateTime purchaseDate;
    private TicketStatus status;
    private PaymentStatus paymentStatus;
}
