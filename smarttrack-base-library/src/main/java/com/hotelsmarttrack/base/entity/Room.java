package com.hotelsmarttrack.base.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.Objects;

/**
 * Room entity representing individual hotel rooms.
 * Part of Base Library (Rule 1) - shared across all components.
 */
@Entity
@Table(name = "rooms")
public class Room {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;
    
    private String roomNumber;
    private int floorNumber;
    
    @ManyToOne
    @JoinColumn(name = "room_type_id")
    private RoomType roomType;
    
    /**
     * Room status: Available, Occupied, Under Cleaning, Out of Service
     */
    private String status;

    // Default constructor
    public Room() {
    }

    // All-args constructor
    public Room(Long roomId, String roomNumber, int floorNumber, RoomType roomType, String status) {
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.floorNumber = floorNumber;
        this.roomType = roomType;
        this.status = status;
    }

    // Getters and Setters
    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(roomId, room.roomId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomId);
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomId=" + roomId +
                ", roomNumber='" + roomNumber + '\'' +
                ", floorNumber=" + floorNumber +
                ", roomType=" + roomType +
                ", status='" + status + '\'' +
                '}';
    }
}
