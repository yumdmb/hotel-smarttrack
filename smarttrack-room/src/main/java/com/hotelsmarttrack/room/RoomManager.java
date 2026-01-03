package com.hotelsmarttrack.room;

import com.hotelsmarttrack.base.entity.Room;
import com.hotelsmarttrack.base.entity.RoomType;
import com.hotelsmarttrack.base.service.RoomService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * RoomManager - Implementation of RoomService.
 * Business logic for Room Management (Rule 2 & 3).
 * This class is private to the smarttrack-room component.
 */
@Service
public class RoomManager implements RoomService {
    
    private final List<Room> roomDatabase = new ArrayList<>();
    private final List<RoomType> roomTypeDatabase = new ArrayList<>();
    private final AtomicLong roomIdGenerator = new AtomicLong(1);
    private final AtomicLong roomTypeIdGenerator = new AtomicLong(1);
    
    @Override
    public RoomType createRoomType(String typeName, String description, int maxOccupancy, BigDecimal basePrice) {
        RoomType roomType = new RoomType();
        roomType.setRoomTypeId(roomTypeIdGenerator.getAndIncrement());
        roomType.setTypeName(typeName);
        roomType.setDescription(description);
        roomType.setMaxOccupancy(maxOccupancy);
        roomType.setBasePrice(basePrice);
        roomType.setTaxRate(BigDecimal.valueOf(0.10)); // Default 10% tax
        
        roomTypeDatabase.add(roomType);
        System.out.println("[RoomManager] Created room type: " + typeName);
        return roomType;
    }
    
    @Override
    public RoomType updateRoomPricing(Long roomTypeId, BigDecimal newPrice, BigDecimal newTaxRate) {
        Optional<RoomType> roomType = roomTypeDatabase.stream()
                .filter(rt -> rt.getRoomTypeId().equals(roomTypeId))
                .findFirst();
        roomType.ifPresent(rt -> {
            rt.setBasePrice(newPrice);
            rt.setTaxRate(newTaxRate);
            System.out.println("[RoomManager] Updated pricing for: " + rt.getTypeName());
        });
        return roomType.orElse(null);
    }
    
    @Override
    public List<RoomType> getAllRoomTypes() {
        return new ArrayList<>(roomTypeDatabase);
    }
    
    @Override
    public Room createRoom(String roomNumber, int floorNumber, Long roomTypeId) {
        RoomType roomType = roomTypeDatabase.stream()
                .filter(rt -> rt.getRoomTypeId().equals(roomTypeId))
                .findFirst()
                .orElse(null);
        
        Room room = new Room();
        room.setRoomId(roomIdGenerator.getAndIncrement());
        room.setRoomNumber(roomNumber);
        room.setFloorNumber(floorNumber);
        room.setRoomType(roomType);
        room.setStatus("Available");
        
        roomDatabase.add(room);
        System.out.println("[RoomManager] Created room: " + roomNumber);
        return room;
    }
    
    @Override
    public Room updateRoom(Room room) {
        Optional<Room> existing = getRoomById(room.getRoomId());
        if (existing.isPresent()) {
            roomDatabase.remove(existing.get());
            roomDatabase.add(room);
        }
        return room;
    }
    
    @Override
    public Optional<Room> getRoomById(Long roomId) {
        return roomDatabase.stream()
                .filter(r -> r.getRoomId().equals(roomId))
                .findFirst();
    }
    
    @Override
    public Optional<Room> getRoomByNumber(String roomNumber) {
        return roomDatabase.stream()
                .filter(r -> r.getRoomNumber().equals(roomNumber))
                .findFirst();
    }
    
    @Override
    public List<Room> getAllRooms() {
        return new ArrayList<>(roomDatabase);
    }
    
    @Override
    public void deleteRoom(Long roomId) {
        roomDatabase.removeIf(r -> r.getRoomId().equals(roomId));
        System.out.println("[RoomManager] Deleted room ID: " + roomId);
    }
    
    @Override
    public void updateRoomStatus(Long roomId, String status) {
        getRoomById(roomId).ifPresent(room -> {
            room.setStatus(status);
            System.out.println("[RoomManager] Updated room " + room.getRoomNumber() + " status to: " + status);
        });
    }
    
    @Override
    public List<Room> getAvailableRooms(LocalDate checkIn, LocalDate checkOut) {
        // Simplified - in production, would check reservations
        return roomDatabase.stream()
                .filter(r -> "Available".equals(r.getStatus()))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Room> getAvailableRoomsByType(Long roomTypeId, LocalDate checkIn, LocalDate checkOut) {
        return getAvailableRooms(checkIn, checkOut).stream()
                .filter(r -> r.getRoomType() != null && r.getRoomType().getRoomTypeId().equals(roomTypeId))
                .collect(Collectors.toList());
    }
}
