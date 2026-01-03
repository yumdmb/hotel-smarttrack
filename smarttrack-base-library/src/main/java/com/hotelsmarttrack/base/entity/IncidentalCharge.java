package com.hotelsmarttrack.base.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * IncidentalCharge entity for additional services during stay.
 * Part of Base Library (Rule 1) - shared across all components.
 */
@Entity
@Table(name = "incidental_charges")
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

    // Default constructor
    public IncidentalCharge() {
    }

    // All-args constructor
    public IncidentalCharge(Long chargeId, Stay stay, String serviceType, 
                            String description, BigDecimal amount, LocalDateTime chargeTime) {
        this.chargeId = chargeId;
        this.stay = stay;
        this.serviceType = serviceType;
        this.description = description;
        this.amount = amount;
        this.chargeTime = chargeTime;
    }

    // Getters and Setters
    public Long getChargeId() {
        return chargeId;
    }

    public void setChargeId(Long chargeId) {
        this.chargeId = chargeId;
    }

    public Stay getStay() {
        return stay;
    }

    public void setStay(Stay stay) {
        this.stay = stay;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getChargeTime() {
        return chargeTime;
    }

    public void setChargeTime(LocalDateTime chargeTime) {
        this.chargeTime = chargeTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IncidentalCharge that = (IncidentalCharge) o;
        return Objects.equals(chargeId, that.chargeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chargeId);
    }

    @Override
    public String toString() {
        return "IncidentalCharge{" +
                "chargeId=" + chargeId +
                ", stay=" + stay +
                ", serviceType='" + serviceType + '\'' +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", chargeTime=" + chargeTime +
                '}';
    }
}
