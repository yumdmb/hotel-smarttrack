package com.hotelsmarttrack.room;

import com.hotelsmarttrack.base.entity.Room;
import com.hotelsmarttrack.base.entity.RoomType;
import com.hotelsmarttrack.base.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
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
 * 
 * Enhanced Features:
 * - Input validation at service layer
 * - Date range validation
 * - Status value validation using RoomStatus enum
 * - Enhanced availability checking with reservation integration
 */
@Service
public class RoomManager implements RoomService {
    
    private final List<Room> roomDatabase = new ArrayList<>();
    private final List<RoomType> roomTypeDatabase = new ArrayList<>();
    private final AtomicLong roomIdGenerator = new AtomicLong(1);
    private final AtomicLong roomTypeIdGenerator = new AtomicLong(1);
    
    // Helper component for availability checking with reservation integration
    private final RoomAvailabilityChecker availabilityChecker;
    
    @Autowired
    public RoomManager(RoomAvailabilityChecker availabilityChecker) {
        this.availabilityChecker = availabilityChecker;
        initializeSampleData();
    }
    
    /**
     * Initialize sample data for demonstration purposes.
     */
    private void initializeSampleData() {
        // Create sample room types
        createRoomType("Standard", "Standard room with basic amenities", 2, new BigDecimal("100.00"));
        createRoomType("Deluxe", "Deluxe room with premium amenities", 3, new BigDecimal("200.00"));
        createRoomType("Suite", "Luxury suite with separate living area", 4, new BigDecimal("350.00"));
        
        System.out.println("[RoomManager] Initialized with " + roomTypeDatabase.size() + " room types");
    }
    
    // ============ Room Type Operations ============
    
    @Override
    public RoomType createRoomType(String typeName, String description, int maxOccupancy, BigDecimal basePrice) {
        // Input validation
        if (typeName == null || typeName.trim().isEmpty()) {
            throw new IllegalArgumentException("Room type name cannot be null or empty");
        }
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Room type description cannot be null or empty");
        }
        if (maxOccupancy <= 0) {
            throw new IllegalArgumentException("Max occupancy must be greater than 0");
        }
        if (basePrice == null || basePrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Base price must be greater than 0");
        }
        
        // Check for duplicate type name
        boolean typeExists = roomTypeDatabase.stream()
                .anyMatch(rt -> rt.getTypeName().equalsIgnoreCase(typeName.trim()));
        if (typeExists) {
            throw new IllegalArgumentException("Room type with name '" + typeName + "' already exists");
        }
        
        RoomType roomType = new RoomType();
        roomType.setRoomTypeId(roomTypeIdGenerator.getAndIncrement());
        roomType.setTypeName(typeName.trim());
        roomType.setDescription(description.trim());
        roomType.setMaxOccupancy(maxOccupancy);
        roomType.setBasePrice(basePrice);
        roomType.setTaxRate(BigDecimal.valueOf(0.10)); // Default 10% tax
        
        roomTypeDatabase.add(roomType);
        System.out.println("[RoomManager] Created room type: " + typeName);
        return roomType;
    }
    
    @Override
    public RoomType updateRoomPricing(Long roomTypeId, BigDecimal newPrice, BigDecimal newTaxRate) {
        // Input validation
        if (roomTypeId == null) {
            throw new IllegalArgumentException("Room type ID cannot be null");
        }
        if (newPrice == null || newPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("New price must be greater than 0");
        }
        if (newTaxRate == null || newTaxRate.compareTo(BigDecimal.ZERO) < 0 || 
            newTaxRate.compareTo(BigDecimal.ONE) >= 1) {
            throw new IllegalArgumentException("Tax rate must be between 0 and 1 (exclusive)");
        }
        
        Optional<RoomType> roomType = roomTypeDatabase.stream()
                .filter(rt -> rt.getRoomTypeId().equals(roomTypeId))
                .findFirst();
        
        if (roomType.isEmpty()) {
            throw new IllegalArgumentException("Room type with ID " + roomTypeId + " not found");
        }
        
        roomType.ifPresent(rt -> {
            rt.setBasePrice(newPrice);
            rt.setTaxRate(newTaxRate);
            System.out.println("[RoomManager] Updated pricing for: " + rt.getTypeName() + 
                             " - New price: $" + newPrice + ", Tax: " + 
                             newTaxRate.multiply(new BigDecimal("100")) + "%");
        });
        return roomType.get();
    }
    
    @Override
    public List<RoomType> getAllRoomTypes() {
        return new ArrayList<>(roomTypeDatabase);
    }
    
    // ============ Room CRUD Operations ============
    
    @Override
    public Room createRoom(String roomNumber, int floorNumber, Long roomTypeId) {
        // Input validation
        if (roomNumber == null || roomNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Room number cannot be null or empty");
        }
        if (floorNumber < 1) {
            throw new IllegalArgumentException("Floor number must be at least 1");
        }
        if (roomTypeId == null) {
            throw new IllegalArgumentException("Room type ID cannot be null");
        }
        
        // Check for duplicate room number
        if (getRoomByNumber(roomNumber.trim()).isPresent()) {
            throw new IllegalArgumentException("Room with number '" + roomNumber + "' already exists");
        }
        
        // Validate room type exists
        RoomType roomType = roomTypeDatabase.stream()
                .filter(rt -> rt.getRoomTypeId().equals(roomTypeId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                    "Room type with ID " + roomTypeId + " not found"));
        
        Room room = new Room();
        room.setRoomId(roomIdGenerator.getAndIncrement());
        room.setRoomNumber(roomNumber.trim());
        room.setFloorNumber(floorNumber);
        room.setRoomType(roomType);
        room.setStatus(RoomStatus.AVAILABLE.getDisplayName());
        
        roomDatabase.add(room);
        System.out.println("[RoomManager] Created room: " + roomNumber + 
                         " (Type: " + roomType.getTypeName() + ", Floor: " + floorNumber + ")");
        return room;
    }
    
    @Override
    public Room updateRoom(Room room) {
        // Input validation
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null");
        }
        if (room.getRoomId() == null) {
            throw new IllegalArgumentException("Room ID cannot be null");
        }
        if (room.getRoomNumber() == null || room.getRoomNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Room number cannot be null or empty");
        }
        if (room.getFloorNumber() < 1) {
            throw new IllegalArgumentException("Floor number must be at least 1");
        }
        
        Optional<Room> existing = getRoomById(room.getRoomId());
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Room with ID " + room.getRoomId() + " not found");
        }
        
        // Check if room number is being changed and if new number already exists
        Room existingRoom = existing.get();
        if (!existingRoom.getRoomNumber().equals(room.getRoomNumber())) {
            if (getRoomByNumber(room.getRoomNumber()).isPresent()) {
                throw new IllegalArgumentException("Room number '" + room.getRoomNumber() + "' is already in use");
            }
        }
        
        roomDatabase.remove(existingRoom);
        roomDatabase.add(room);
        System.out.println("[RoomManager] Updated room: " + room.getRoomNumber());
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
        // Input validation
        if (roomId == null) {
            throw new IllegalArgumentException("Room ID cannot be null");
        }
        
        Optional<Room> room = getRoomById(roomId);
        if (room.isEmpty()) {
            throw new IllegalArgumentException("Room with ID " + roomId + " not found");
        }
        
        // Business rule: Can't delete occupied rooms
        if (RoomStatus.OCCUPIED.getDisplayName().equals(room.get().getStatus())) {
            throw new IllegalStateException("Cannot delete room " + room.get().getRoomNumber() + 
                                          " - room is currently occupied");
        }
        
        roomDatabase.remove(room.get());
        System.out.println("[RoomManager] Deleted room: " + room.get().getRoomNumber() + 
                         " (ID: " + roomId + ")");
    }
    
    // ============ Room Status Operations ============
    
    @Override
    public void updateRoomStatus(Long roomId, String status) {
        // Input validation
        if (roomId == null) {
            throw new IllegalArgumentException("Room ID cannot be null");
        }
        
        // Validate status using RoomStatus enum
        RoomStatus newStatus;
        try {
            newStatus = RoomStatus.fromDisplayName(status);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid room status. " + e.getMessage());
        }
        
        Optional<Room> roomOpt = getRoomById(roomId);
        if (roomOpt.isEmpty()) {
            throw new IllegalArgumentException("Room with ID " + roomId + " not found");
        }
        
        Room room = roomOpt.get();
        String oldStatus = room.getStatus();
        room.setStatus(newStatus.getDisplayName());
        
        System.out.println("[RoomManager] Updated room " + room.getRoomNumber() + 
                         " status from '" + oldStatus + "' to '" + newStatus.getDisplayName() + "'");
    }
    
    // ============ Room Availability Operations ============
    
    @Override
    public List<Room> getAvailableRooms(LocalDate checkIn, LocalDate checkOut) {
        // Date range validation
        validateDateRange(checkIn, checkOut);
        
        // Filter rooms using enhanced availability checking
        return roomDatabase.stream()
                .filter(room -> availabilityChecker.isRoomAvailable(room, checkIn, checkOut))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Room> getAvailableRoomsByType(Long roomTypeId, LocalDate checkIn, LocalDate checkOut) {
        // Input validation
        if (roomTypeId == null) {
            throw new IllegalArgumentException("Room type ID cannot be null");
        }
        
        // Date range validation
        validateDateRange(checkIn, checkOut);
        
        // Validate room type exists
        boolean typeExists = roomTypeDatabase.stream()
                .anyMatch(rt -> rt.getRoomTypeId().equals(roomTypeId));
        if (!typeExists) {
            throw new IllegalArgumentException("Room type with ID " + roomTypeId + " not found");
        }
        
        return getAvailableRooms(checkIn, checkOut).stream()
                .filter(r -> r.getRoomType() != null && r.getRoomType().getRoomTypeId().equals(roomTypeId))
                .collect(Collectors.toList());
    }
    
    // ============ Helper Methods ============
    
    /**
     * Validate date range for room availability checks.
     * 
     * @param checkIn Check-in date
     * @param checkOut Check-out date
     * @throws IllegalArgumentException if dates are invalid
     */
    private void validateDateRange(LocalDate checkIn, LocalDate checkOut) {
        if (checkIn == null) {
            throw new IllegalArgumentException("Check-in date cannot be null");
        }
        if (checkOut == null) {
            throw new IllegalArgumentException("Check-out date cannot be null");
        }
        if (checkIn.isAfter(checkOut)) {
            throw new IllegalArgumentException("Check-in date (" + checkIn + 
                ") must be before check-out date (" + checkOut + ")");
        }
        if (checkIn.equals(checkOut)) {
            throw new IllegalArgumentException("Check-in and check-out dates cannot be the same");
        }
        if (checkIn.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Check-in date cannot be in the past");
        }
    }
    
    /**
     * Get the availability checker for external access if needed.
     * This allows other components (like Reservation) to block/unblock dates.
     * 
     * @return The room availability checker
     */
    public RoomAvailabilityChecker getAvailabilityChecker() {
        return availabilityChecker;
    }
    
    /**
     * Get rooms by status (additional helper method).
     * 
     * @param status The room status to filter by
     * @return List of rooms with the specified status
     */
    public List<Room> getRoomsByStatus(String status) {
        // Validate status
        RoomStatus.fromDisplayName(status); // This will throw if invalid
        
        return roomDatabase.stream()
                .filter(room -> status.equals(room.getStatus()))
                .collect(Collectors.toList());
    }
    
    /**
     * Get rooms by floor number (additional helper method).
     * 
     * @param floorNumber The floor number
     * @return List of rooms on the specified floor
     */
    public List<Room> getRoomsByFloor(int floorNumber) {
        if (floorNumber < 1) {
            throw new IllegalArgumentException("Floor number must be at least 1");
        }
        
        return roomDatabase.stream()
                .filter(room -> room.getFloorNumber() == floorNumber)
                .collect(Collectors.toList());
    }
    
    /**
     * Get rooms by room type (additional helper method).
     * 
     * @param roomTypeId The room type ID
     * @return List of rooms of the specified type
     */
    public List<Room> getRoomsByType(Long roomTypeId) {
        if (roomTypeId == null) {
            throw new IllegalArgumentException("Room type ID cannot be null");
        }
        
        return roomDatabase.stream()
                .filter(room -> room.getRoomType() != null && 
                              room.getRoomType().getRoomTypeId().equals(roomTypeId))
                .collect(Collectors.toList());
    }
}
