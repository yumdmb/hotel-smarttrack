package com.hotelsmarttrack.base.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * RoomType entity defining room categories and pricing.
 * Part of Base Library (Rule 1) - shared across all components.
 */
@Data
@Entity
@Table(name = "room_types")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomType {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomTypeId;
    
    private String typeName;         // e.g., "Standard", "Deluxe", "Suite"
    private String description;
    private int maxOccupancy;
    private BigDecimal basePrice;
    private BigDecimal taxRate;
}
