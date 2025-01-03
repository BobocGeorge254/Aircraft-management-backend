package com.projects.plane.model;

import com.projects.plane.model.enums.PaymentStatus;
import com.projects.plane.model.enums.TicketClass;
import com.projects.plane.model.enums.TicketStatus;
import jakarta.persistence.*;
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
@Entity
@Table(name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull(message = "Flight cannot be null.")
    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;

    @NotNull(message = "Passenger name cannot be null.")
    @Size(min = 1, max = 100, message = "Passenger name should be between 1 and 100 characters.")
    @Column(name = "passenger_name")
    private String passengerName;

    @NotNull(message = "Seat number cannot be null.")
    @Size(min = 1, max = 5, message = "Seat number should be between 1 and 5 characters.")
    @Column(name = "seat_number")
    private String seatNumber;

    @NotNull(message = "Ticket class cannot be null.")
    @Enumerated(EnumType.STRING)
    @Column(name = "ticket_class")
    private TicketClass ticketClass;

    @NotNull(message = "Price cannot be null.")
    @Column(name = "price")
    private BigDecimal price;

    @NotNull(message = "Purchase date cannot be null.")
    @Column(name = "purchase_date")
    private LocalDateTime purchaseDate;

    @NotNull(message = "Ticket status cannot be null.")
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TicketStatus status;

    @NotNull(message = "Payment status cannot be null.")
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

}
