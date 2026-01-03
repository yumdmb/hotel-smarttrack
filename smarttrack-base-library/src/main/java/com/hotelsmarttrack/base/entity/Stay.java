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

import java.time.LocalDateTime;

/**
 * Stay entity representing active/past guest stays.
 * Part of Base Library (Rule 1) - shared across all components.
 */
@Data
@Entity
@Table(name = "stays")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stay {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stayId;
    
    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;
    
    @ManyToOne
    @JoinColumn(name = "guest_id")
    private Guest guest;
    
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
    
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    
    /**
     * Stay status: Active, Checked-Out
     */
    private String status;
    
    private String keyCardNumber;
}
