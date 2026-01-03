package com.hotelsmarttrack.billing;

import com.hotelsmarttrack.base.entity.Invoice;
import com.hotelsmarttrack.base.entity.Payment;
import com.hotelsmarttrack.base.service.BillingService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * BillingManager - Implementation of BillingService.
 * Business logic for Billing & Payment Management (Rule 2 & 3).
 */
@Service
public class BillingManager implements BillingService {
    
    private final List<Invoice> invoiceDatabase = new ArrayList<>();
    private final List<Payment> paymentDatabase = new ArrayList<>();
    private final AtomicLong invoiceIdGenerator = new AtomicLong(1);
    private final AtomicLong paymentIdGenerator = new AtomicLong(1);
    
    // Store stay ID to invoice mapping for lookup
    private final java.util.Map<Long, Long> stayToInvoiceMap = new java.util.concurrent.ConcurrentHashMap<>();
    
    @Override
    public Invoice generateInvoice(Long stayId) {
        // Calculate charges (simplified)
        BigDecimal roomCharges = BigDecimal.valueOf(150.00); // Mock room charge
        BigDecimal incidentalCharges = BigDecimal.valueOf(50.00); // Mock incidental
        BigDecimal taxes = roomCharges.add(incidentalCharges).multiply(BigDecimal.valueOf(0.10));
        BigDecimal totalAmount = roomCharges.add(incidentalCharges).add(taxes);
        
        Invoice invoice = Invoice.builder()
                .invoiceId(invoiceIdGenerator.getAndIncrement())
                .roomCharges(roomCharges)
                .incidentalCharges(incidentalCharges)
                .taxes(taxes)
                .discounts(BigDecimal.ZERO)
                .totalAmount(totalAmount)
                .amountPaid(BigDecimal.ZERO)
                .outstandingBalance(totalAmount)
                .status("Issued")
                .issuedTime(LocalDateTime.now())
                .payments(new ArrayList<>())
                .build();
        
        invoiceDatabase.add(invoice);
        stayToInvoiceMap.put(stayId, invoice.getInvoiceId());
        
        System.out.println("[BillingManager] Generated invoice for stay " + stayId + 
                " - Total: $" + totalAmount);
        return invoice;
    }
    
    @Override
    public BigDecimal computeTotalCharges(Long stayId) {
        return getInvoiceByStay(stayId)
                .map(Invoice::getTotalAmount)
                .orElse(BigDecimal.ZERO);
    }
    
    @Override
    public Optional<Invoice> getInvoiceById(Long invoiceId) {
        return invoiceDatabase.stream()
                .filter(i -> i.getInvoiceId().equals(invoiceId))
                .findFirst();
    }
    
    @Override
    public Optional<Invoice> getInvoiceByStay(Long stayId) {
        Long invoiceId = stayToInvoiceMap.get(stayId);
        if (invoiceId != null) {
            return getInvoiceById(invoiceId);
        }
        return Optional.empty();
    }
    
    @Override
    public Payment processPayment(Long invoiceId, BigDecimal amount, String paymentMethod) {
        Payment payment = Payment.builder()
                .paymentId(paymentIdGenerator.getAndIncrement())
                .amount(amount)
                .paymentMethod(paymentMethod)
                .status("Completed")
                .transactionReference(UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .paymentTime(LocalDateTime.now())
                .build();
        
        paymentDatabase.add(payment);
        
        // Update invoice
        getInvoiceById(invoiceId).ifPresent(invoice -> {
            invoice.getPayments().add(payment);
            BigDecimal newPaidAmount = invoice.getAmountPaid().add(amount);
            invoice.setAmountPaid(newPaidAmount);
            invoice.setOutstandingBalance(invoice.getTotalAmount().subtract(newPaidAmount));
            
            // Update status
            if (invoice.getOutstandingBalance().compareTo(BigDecimal.ZERO) <= 0) {
                invoice.setStatus("Paid");
            } else {
                invoice.setStatus("Partially Paid");
            }
            
            System.out.println("[BillingManager] Processed payment: $" + amount + 
                    " via " + paymentMethod + " - Ref: " + payment.getTransactionReference());
        });
        
        return payment;
    }
    
    @Override
    public List<Payment> getPaymentsForInvoice(Long invoiceId) {
        return getInvoiceById(invoiceId)
                .map(Invoice::getPayments)
                .orElse(new ArrayList<>());
    }
    
    @Override
    public BigDecimal getOutstandingBalance(Long invoiceId) {
        return getInvoiceById(invoiceId)
                .map(Invoice::getOutstandingBalance)
                .orElse(BigDecimal.ZERO);
    }
    
    @Override
    public List<Invoice> getUnpaidInvoices() {
        return invoiceDatabase.stream()
                .filter(i -> i.getOutstandingBalance().compareTo(BigDecimal.ZERO) > 0)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Invoice> getInvoicesByGuest(Long guestId) {
        return invoiceDatabase.stream()
                .filter(i -> i.getGuest() != null && i.getGuest().getGuestId().equals(guestId))
                .collect(Collectors.toList());
    }
    
    @Override
    public void updateInvoiceStatus(Long invoiceId, String status) {
        getInvoiceById(invoiceId).ifPresent(invoice -> {
            invoice.setStatus(status);
            System.out.println("[BillingManager] Updated invoice " + invoiceId + " status to: " + status);
        });
    }
    
    @Override
    public void applyDiscount(Long invoiceId, BigDecimal discountAmount, String reason) {
        getInvoiceById(invoiceId).ifPresent(invoice -> {
            invoice.setDiscounts(invoice.getDiscounts().add(discountAmount));
            BigDecimal newTotal = invoice.getRoomCharges()
                    .add(invoice.getIncidentalCharges())
                    .add(invoice.getTaxes())
                    .subtract(invoice.getDiscounts());
            invoice.setTotalAmount(newTotal);
            invoice.setOutstandingBalance(newTotal.subtract(invoice.getAmountPaid()));
            
            System.out.println("[BillingManager] Applied discount: $" + discountAmount + " - " + reason);
        });
    }
}
