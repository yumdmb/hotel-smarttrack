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
import java.time.LocalDateTime;

/**
 * Payment entity for recording payment transactions.
 * Part of Base Library (Rule 1) - shared across all components.
 */
@Data
@Entity
@Table(name = "payments")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;
    
    private BigDecimal amount;
    
    /**
     * Payment method: Cash, Credit Card, Debit Card, Digital Wallet
     */
    private String paymentMethod;
    
    /**
     * Payment status: Pending, Completed, Failed, Refunded
     */
    private String status;
    
    private String transactionReference;
    private LocalDateTime paymentTime;
}
