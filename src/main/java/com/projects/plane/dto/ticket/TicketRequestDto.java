package com.projects.plane.dto.ticket;

import com.projects.plane.model.enums.TicketClass;
import com.projects.plane.model.enums.TicketStatus;
import com.projects.plane.model.enums.PaymentStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class TicketRequestDto {

    @NotNull(message = "Flight ID cannot be null.")
    private UUID flightId;

    @NotNull(message = "Passenger name cannot be null.")
    @Size(min = 1, max = 100, message = "Passenger name should be between 1 and 100 characters.")
    private String passengerName;

    @NotNull(message = "Seat number cannot be null.")
    @Size(min = 1, max = 5, message = "Seat number should be between 1 and 5 characters.")
    private String seatNumber;

    @NotNull(message = "Ticket class cannot be null.")
    private TicketClass ticketClass;

    @NotNull(message = "Price cannot be null.")
    private BigDecimal price;

    @NotNull(message = "Purchase date cannot be null.")
    private LocalDateTime purchaseDate;

    @NotNull(message = "Ticket status cannot be null.")
    private TicketStatus status;

    @NotNull(message = "Payment status cannot be null.")
    private PaymentStatus paymentStatus;
}
