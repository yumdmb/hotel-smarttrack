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

/**
 * Room entity representing individual hotel rooms.
 * Part of Base Library (Rule 1) - shared across all components.
 */
@Data
@Entity
@Table(name = "rooms")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;
    
    private String roomNumber;
    private int floorNumber;
    
    @ManyToOne
    @JoinColumn(name = "room_type_id")
    private RoomType roomType;
    
    /**
     * Room status: Available, Occupied, Under Cleaning, Out of Service
     */
    private String status;
}
