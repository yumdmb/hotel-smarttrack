package com.hotelsmarttrack.base.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Reservation entity for room bookings.
 * Part of Base Library (Rule 1) - shared across all components.
 */
@Data
@Entity
@Table(name = "reservations")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;
    
    @ManyToOne
    @JoinColumn(name = "guest_id")
    private Guest guest;
    
    @ManyToOne
    @JoinColumn(name = "room_type_id")
    private RoomType roomType;
    
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room assignedRoom;
    
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int numberOfGuests;
    
    /**
     * Reservation status: Reserved, Confirmed, Cancelled, No-Show, Checked-In, Checked-Out
     */
    private String status;
    
    private String specialRequests;
}
