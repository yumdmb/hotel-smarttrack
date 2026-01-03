package com.hotelsmarttrack.base.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Reservation entity for room bookings.
 * Part of Base Library (Rule 1) - shared across all components.
 */
@Entity
@Table(name = "reservations")
public class Reservation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;
    
    @ManyToOne
    @JoinColumn(name = "guest_id")
    private Guest guest;
    
    @ManyToOne
    @JoinColumn(name = "room_type_id")
    private RoomType roomType;
    
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room assignedRoom;
    
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int numberOfGuests;
    
    /**
     * Reservation status: Reserved, Confirmed, Cancelled, No-Show, Checked-In, Checked-Out
     */
    private String status;
    
    private String specialRequests;

    // Default constructor
    public Reservation() {
    }

    // All-args constructor
    public Reservation(Long reservationId, Guest guest, RoomType roomType, Room assignedRoom,
                       LocalDate checkInDate, LocalDate checkOutDate, int numberOfGuests,
                       String status, String specialRequests) {
        this.reservationId = reservationId;
        this.guest = guest;
        this.roomType = roomType;
        this.assignedRoom = assignedRoom;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numberOfGuests = numberOfGuests;
        this.status = status;
        this.specialRequests = specialRequests;
    }

    // Getters and Setters
    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public Room getAssignedRoom() {
        return assignedRoom;
    }

    public void setAssignedRoom(Room assignedRoom) {
        this.assignedRoom = assignedRoom;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSpecialRequests() {
        return specialRequests;
    }

    public void setSpecialRequests(String specialRequests) {
        this.specialRequests = specialRequests;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(reservationId, that.reservationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservationId);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId=" + reservationId +
                ", guest=" + guest +
                ", roomType=" + roomType +
                ", assignedRoom=" + assignedRoom +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", numberOfGuests=" + numberOfGuests +
                ", status='" + status + '\'' +
                ", specialRequests='" + specialRequests + '\'' +
                '}';
    }
}
