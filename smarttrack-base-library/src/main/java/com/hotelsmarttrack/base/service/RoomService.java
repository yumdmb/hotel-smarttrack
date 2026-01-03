package com.hotelsmarttrack.base.service;

import com.hotelsmarttrack.base.entity.Room;
import com.hotelsmarttrack.base.entity.RoomType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * RoomService interface - exposes Room Management functionality.
 * Part of Base Library (Rule 5) - interface in common library.
 * Implemented by RoomManager in smarttrack-room component.
 */
public interface RoomService {
    
    // ============ Room Type Operations ============
    
    /**
     * Create a new room type.
     */
    RoomType createRoomType(String typeName, String description, int maxOccupancy, BigDecimal basePrice);
    
    /**
     * Update room type pricing.
     */
    RoomType updateRoomPricing(Long roomTypeId, BigDecimal newPrice, BigDecimal newTaxRate);
    
    /**
     * Get all room types.
     */
    List<RoomType> getAllRoomTypes();
    
    // ============ Room Operations ============
    
    /**
     * Create a new room.
     */
    Room createRoom(String roomNumber, int floorNumber, Long roomTypeId);
    
    /**
     * Update room information.
     */
    Room updateRoom(Room room);
    
    /**
     * Get room by ID.
     */
    Optional<Room> getRoomById(Long roomId);
    
    /**
     * Get room by room number.
     */
    Optional<Room> getRoomByNumber(String roomNumber);
    
    /**
     * Get all rooms.
     */
    List<Room> getAllRooms();
    
    /**
     * Delete a room.
     */
    void deleteRoom(Long roomId);
    
    // ============ Room Status Operations ============
    
    /**
     * Update room operational status.
     * @param roomId Room ID
     * @param status New status (Available, Occupied, Under Cleaning, Out of Service)
     */
    void updateRoomStatus(Long roomId, String status);
    
    /**
     * Get available rooms for date range.
     */
    List<Room> getAvailableRooms(LocalDate checkIn, LocalDate checkOut);
    
    /**
     * Get available rooms by type for date range.
     */
    List<Room> getAvailableRoomsByType(Long roomTypeId, LocalDate checkIn, LocalDate checkOut);
}
