package com.hotelsmarttrack.base.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Stay entity representing active/past guest stays.
 * Part of Base Library (Rule 1) - shared across all components.
 */
@Entity
@Table(name = "stays")
public class Stay {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stayId;
    
    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;
    
    @ManyToOne
    @JoinColumn(name = "guest_id")
    private Guest guest;
    
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
    
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    
    /**
     * Stay status: Active, Checked-Out
     */
    private String status;
    
    private String keyCardNumber;

    // Default constructor
    public Stay() {
    }

    // All-args constructor
    public Stay(Long stayId, Reservation reservation, Guest guest, Room room,
                LocalDateTime checkInTime, LocalDateTime checkOutTime, String status, String keyCardNumber) {
        this.stayId = stayId;
        this.reservation = reservation;
        this.guest = guest;
        this.room = room;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.status = status;
        this.keyCardNumber = keyCardNumber;
    }

    // Getters and Setters
    public Long getStayId() {
        return stayId;
    }

    public void setStayId(Long stayId) {
        this.stayId = stayId;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public LocalDateTime getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(LocalDateTime checkInTime) {
        this.checkInTime = checkInTime;
    }

    public LocalDateTime getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(LocalDateTime checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKeyCardNumber() {
        return keyCardNumber;
    }

    public void setKeyCardNumber(String keyCardNumber) {
        this.keyCardNumber = keyCardNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stay stay = (Stay) o;
        return Objects.equals(stayId, stay.stayId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stayId);
    }

    @Override
    public String toString() {
        return "Stay{" +
                "stayId=" + stayId +
                ", reservation=" + reservation +
                ", guest=" + guest +
                ", room=" + room +
                ", checkInTime=" + checkInTime +
                ", checkOutTime=" + checkOutTime +
                ", status='" + status + '\'' +
                ", keyCardNumber='" + keyCardNumber + '\'' +
                '}';
    }
}
