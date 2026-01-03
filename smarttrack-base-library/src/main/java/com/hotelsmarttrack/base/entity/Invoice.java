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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Invoice entity for billing documents.
 * Part of Base Library (Rule 1) - shared across all components.
 */
@Entity
@Table(name = "invoices")
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

    // Default constructor
    public Invoice() {
    }

    // All-args constructor
    public Invoice(Long invoiceId, Stay stay, Guest guest, BigDecimal roomCharges,
                   BigDecimal incidentalCharges, BigDecimal taxes, BigDecimal discounts,
                   BigDecimal totalAmount, BigDecimal amountPaid, BigDecimal outstandingBalance,
                   List<Payment> payments, String status, LocalDateTime issuedTime) {
        this.invoiceId = invoiceId;
        this.stay = stay;
        this.guest = guest;
        this.roomCharges = roomCharges;
        this.incidentalCharges = incidentalCharges;
        this.taxes = taxes;
        this.discounts = discounts;
        this.totalAmount = totalAmount;
        this.amountPaid = amountPaid;
        this.outstandingBalance = outstandingBalance;
        this.payments = payments;
        this.status = status;
        this.issuedTime = issuedTime;
    }

    // Getters and Setters
    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Stay getStay() {
        return stay;
    }

    public void setStay(Stay stay) {
        this.stay = stay;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public BigDecimal getRoomCharges() {
        return roomCharges;
    }

    public void setRoomCharges(BigDecimal roomCharges) {
        this.roomCharges = roomCharges;
    }

    public BigDecimal getIncidentalCharges() {
        return incidentalCharges;
    }

    public void setIncidentalCharges(BigDecimal incidentalCharges) {
        this.incidentalCharges = incidentalCharges;
    }

    public BigDecimal getTaxes() {
        return taxes;
    }

    public void setTaxes(BigDecimal taxes) {
        this.taxes = taxes;
    }

    public BigDecimal getDiscounts() {
        return discounts;
    }

    public void setDiscounts(BigDecimal discounts) {
        this.discounts = discounts;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    public BigDecimal getOutstandingBalance() {
        return outstandingBalance;
    }

    public void setOutstandingBalance(BigDecimal outstandingBalance) {
        this.outstandingBalance = outstandingBalance;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getIssuedTime() {
        return issuedTime;
    }

    public void setIssuedTime(LocalDateTime issuedTime) {
        this.issuedTime = issuedTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return Objects.equals(invoiceId, invoice.invoiceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceId);
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "invoiceId=" + invoiceId +
                ", stay=" + stay +
                ", guest=" + guest +
                ", roomCharges=" + roomCharges +
                ", incidentalCharges=" + incidentalCharges +
                ", taxes=" + taxes +
                ", discounts=" + discounts +
                ", totalAmount=" + totalAmount +
                ", amountPaid=" + amountPaid +
                ", outstandingBalance=" + outstandingBalance +
                ", payments=" + payments +
                ", status='" + status + '\'' +
                ", issuedTime=" + issuedTime +
                '}';
    }
}
