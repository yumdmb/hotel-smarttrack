package com.hotelsmarttrack.base.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Invoice entity for billing documents.
 * Part of Base Library (Rule 1) - shared across all components.
 */
@Data
@Entity
@Table(name = "invoices")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Invoice {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long invoiceId;
    
    @ManyToOne
    @JoinColumn(name = "stay_id")
    private Stay stay;
    
    @ManyToOne
    @JoinColumn(name = "guest_id")
    private Guest guest;
    
    private BigDecimal roomCharges;
    private BigDecimal incidentalCharges;
    private BigDecimal taxes;
    private BigDecimal discounts;
    private BigDecimal totalAmount;
    private BigDecimal amountPaid;
    private BigDecimal outstandingBalance;
    
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "invoice_id")
    private List<Payment> payments;
    
    /**
     * Invoice status: Draft, Issued, Paid, Partially Paid, Overdue
     */
    private String status;
    
    private LocalDateTime issuedTime;
}
