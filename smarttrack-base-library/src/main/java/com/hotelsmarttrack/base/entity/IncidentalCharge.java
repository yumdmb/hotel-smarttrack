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

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * IncidentalCharge entity for additional services during stay.
 * Part of Base Library (Rule 1) - shared across all components.
 */
@Data
@Entity
@Table(name = "incidental_charges")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IncidentalCharge {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chargeId;
    
    @ManyToOne
    @JoinColumn(name = "stay_id")
    private Stay stay;
    
    /**
     * Service type: F&B, Laundry, Minibar, Room Service, Spa, etc.
     */
    private String serviceType;
    
    private String description;
    private BigDecimal amount;
    private LocalDateTime chargeTime;
}
