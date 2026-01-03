package com.hotelsmarttrack.base.service;

import com.hotelsmarttrack.base.entity.Invoice;
import com.hotelsmarttrack.base.entity.Payment;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * BillingService interface - exposes Billing & Payment functionality.
 * Part of Base Library (Rule 5) - interface in common library.
 * Implemented by BillingManager in smarttrack-billing component.
 */
public interface BillingService {
    
    // ============ Invoice Generation ============
    
    /**
     * Generate a billing invoice for a stay.
     * @param stayId Stay ID
     * @return Generated invoice
     */
    Invoice generateInvoice(Long stayId);
    
    /**
     * Compute total charges for a stay.
     * Includes room charges, incidental charges, taxes, and discounts.
     * @param stayId Stay ID
     * @return Total amount payable
     */
    BigDecimal computeTotalCharges(Long stayId);
    
    /**
     * Get invoice by ID.
     */
    Optional<Invoice> getInvoiceById(Long invoiceId);
    
    /**
     * Get invoice for a stay.
     */
    Optional<Invoice> getInvoiceByStay(Long stayId);
    
    // ============ Payment Processing ============
    
    /**
     * Process and record a payment.
     * @param invoiceId Invoice ID
     * @param amount Payment amount
     * @param paymentMethod Payment method (Cash, Card, etc.)
     * @return Recorded payment
     */
    Payment processPayment(Long invoiceId, BigDecimal amount, String paymentMethod);
    
    /**
     * Get payments for an invoice.
     */
    List<Payment> getPaymentsForInvoice(Long invoiceId);
    
    // ============ Outstanding Balance Management ============
    
    /**
     * Get outstanding balance for an invoice.
     */
    BigDecimal getOutstandingBalance(Long invoiceId);
    
    /**
     * Get all invoices with outstanding balances.
     */
    List<Invoice> getUnpaidInvoices();
    
    /**
     * Get invoices for a guest.
     */
    List<Invoice> getInvoicesByGuest(Long guestId);
    
    /**
     * Update invoice status.
     */
    void updateInvoiceStatus(Long invoiceId, String status);
    
    // ============ Discounts ============
    
    /**
     * Apply discount to an invoice.
     * @param invoiceId Invoice ID
     * @param discountAmount Discount amount
     * @param reason Discount reason
     */
    void applyDiscount(Long invoiceId, BigDecimal discountAmount, String reason);
}
