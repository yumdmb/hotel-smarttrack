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

/**
 * Guest entity representing hotel guest information.
 * Part of Base Library (Rule 1) - shared across all components.
 */
@Data
@Entity
@Table(name = "guests")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Guest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long guestId;
    
    private String name;
    private String email;
    private String phone;
    private String identificationNumber;
    
    /**
     * Guest status: Active, Inactive, Blacklisted
     */
    private String status;
    
    private String statusJustification;
}
