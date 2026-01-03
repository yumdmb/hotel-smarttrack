package com.hotelsmarttrack.base.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * RoomType entity defining room categories and pricing.
 * Part of Base Library (Rule 1) - shared across all components.
 */
@Entity
@Table(name = "room_types")
public class RoomType {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomTypeId;
    
    private String typeName;         // e.g., "Standard", "Deluxe", "Suite"
    private String description;
    private int maxOccupancy;
    private BigDecimal basePrice;
    private BigDecimal taxRate;

    // Default constructor
    public RoomType() {
    }

    // All-args constructor
    public RoomType(Long roomTypeId, String typeName, String description, 
                    int maxOccupancy, BigDecimal basePrice, BigDecimal taxRate) {
        this.roomTypeId = roomTypeId;
        this.typeName = typeName;
        this.description = description;
        this.maxOccupancy = maxOccupancy;
        this.basePrice = basePrice;
        this.taxRate = taxRate;
    }

    // Getters and Setters
    public Long getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Long roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMaxOccupancy() {
        return maxOccupancy;
    }

    public void setMaxOccupancy(int maxOccupancy) {
        this.maxOccupancy = maxOccupancy;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomType roomType = (RoomType) o;
        return Objects.equals(roomTypeId, roomType.roomTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomTypeId);
    }

    @Override
    public String toString() {
        return "RoomType{" +
                "roomTypeId=" + roomTypeId +
                ", typeName='" + typeName + '\'' +
                ", description='" + description + '\'' +
                ", maxOccupancy=" + maxOccupancy +
                ", basePrice=" + basePrice +
                ", taxRate=" + taxRate +
                '}';
    }
}
