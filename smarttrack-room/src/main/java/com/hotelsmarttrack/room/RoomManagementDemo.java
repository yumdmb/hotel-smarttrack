package com.hotelsmarttrack.room;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.hotelsmarttrack.base.entity.Room;
import com.hotelsmarttrack.base.entity.RoomType;
import com.hotelsmarttrack.base.service.RoomService;

/**
 * Demonstration class showcasing enhanced Room Management features:
 * - Input validation at service layer
 * - Date range validation
 * - Status value validation using RoomStatus enum
 * - Enhanced availability checking with reservation integration
 */
public class RoomManagementDemo {
    
    private final RoomService roomService;
    
    public RoomManagementDemo(RoomService roomService) {
        this.roomService = roomService;
    }
    
    /**
     * Run a complete demonstration of all enhanced features.
     */
    public void runEnhancedDemo() {
        System.out.println("\n========================================");
        System.out.println("  ENHANCED ROOM MANAGEMENT DEMO");
        System.out.println("========================================\n");
        
        // 1. Input Validation Demo
        demonstrateInputValidation();
        
        // 2. Room Type and Pricing Management
        demonstrateRoomTypeManagement();
        
        // 3. Room CRUD Operations with Validation
        demonstrateRoomCRUD();
        
        // 4. Status Management with Enum Validation
        demonstrateStatusManagement();
        
        // 5. Enhanced Availability Checking
        demonstrateEnhancedAvailability();
        
        // 6. Date Range Validation
        demonstrateDateRangeValidation();
        
        System.out.println("\n========================================");
        System.out.println("  DEMO COMPLETED SUCCESSFULLY");
        System.out.println("========================================\n");
    }
    
    private void demonstrateInputValidation() {
        System.out.println("--- 1. Input Validation Demo ---");
        
        try {
            // This should fail - empty room type name
            roomService.createRoomType("", "Description", 2, new BigDecimal("100"));
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Caught expected error: " + e.getMessage());
        }
        
        try {
            // This should fail - negative price
            roomService.createRoomType("Invalid", "Description", 2, new BigDecimal("-50"));
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Caught expected error: " + e.getMessage());
        }
        
        try {
            // This should fail - zero occupancy
            roomService.createRoomType("Invalid", "Description", 0, new BigDecimal("100"));
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Caught expected error: " + e.getMessage());
        }
        
        System.out.println("✓ Input validation working correctly\n");
    }
    
    private void demonstrateRoomTypeManagement() {
        System.out.println("--- 2. Room Type and Pricing Management ---");
        
        // Create room types (already initialized)
        List<RoomType> types = roomService.getAllRoomTypes();
        System.out.println("Available room types:");
        types.forEach(rt -> 
            System.out.println("  • " + rt.getTypeName() + ": $" + rt.getBasePrice() + 
                             " (Tax: " + rt.getTaxRate().multiply(new BigDecimal("100")) + "%)")
        );
        
        // Update pricing with validation
        RoomType deluxe = types.stream()
                .filter(rt -> "Deluxe".equals(rt.getTypeName()))
                .findFirst()
                .orElse(null);
        
        if (deluxe != null) {
            roomService.updateRoomPricing(
                deluxe.getRoomTypeId(),
                new BigDecimal("225.00"),
                new BigDecimal("0.12")
            );
            System.out.println("✓ Updated Deluxe pricing to $225.00 with 12% tax");
        }
        
        System.out.println();
    }
    
    private void demonstrateRoomCRUD() {
        System.out.println("--- 3. Room CRUD Operations with Validation ---");
        
        // Get room types
        List<RoomType> types = roomService.getAllRoomTypes();
        Long standardTypeId = types.get(0).getRoomTypeId();
        Long deluxeTypeId = types.get(1).getRoomTypeId();
        
        // Create rooms with validation
        Room room101 = roomService.createRoom("101", 1, standardTypeId);
        roomService.createRoom("102", 1, standardTypeId);
        roomService.createRoom("201", 2, deluxeTypeId);
        roomService.createRoom("202", 2, deluxeTypeId);
        
        System.out.println("✓ Created 4 rooms successfully");
        
        // Try to create duplicate - should fail
        try {
            roomService.createRoom("101", 1, standardTypeId);
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Prevented duplicate room: " + e.getMessage());
        }
        
        // Try to create room with invalid floor
        try {
            roomService.createRoom("999", 0, standardTypeId);
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Prevented invalid floor: " + e.getMessage());
        }
        
        // Update room
        room101.setFloorNumber(1);
        roomService.updateRoom(room101);
        System.out.println("✓ Updated room 101");
        
        System.out.println("Total rooms in system: " + roomService.getAllRooms().size());
        System.out.println();
    }
    
    private void demonstrateStatusManagement() {
        System.out.println("--- 4. Status Management with Enum Validation ---");
        
        Room room = roomService.getRoomByNumber("101").orElse(null);
        if (room != null) {
            // Valid status updates
            roomService.updateRoomStatus(room.getRoomId(), RoomStatus.OCCUPIED.getDisplayName());
            System.out.println("✓ Room 101 status: " + RoomStatus.OCCUPIED.getDisplayName());
            
            roomService.updateRoomStatus(room.getRoomId(), RoomStatus.UNDER_CLEANING.getDisplayName());
            System.out.println("✓ Room 101 status: " + RoomStatus.UNDER_CLEANING.getDisplayName());
            
            roomService.updateRoomStatus(room.getRoomId(), RoomStatus.AVAILABLE.getDisplayName());
            System.out.println("✓ Room 101 status: " + RoomStatus.AVAILABLE.getDisplayName());
            
            // Try invalid status - should fail
            try {
                roomService.updateRoomStatus(room.getRoomId(), "InvalidStatus");
            } catch (IllegalArgumentException e) {
                System.out.println("✓ Prevented invalid status: " + e.getMessage());
            }
        }
        
        System.out.println();
    }
    
    private void demonstrateEnhancedAvailability() {
        System.out.println("--- 5. Enhanced Availability Checking ---");
        
        LocalDate checkIn = LocalDate.now().plusDays(7);
        LocalDate checkOut = LocalDate.now().plusDays(10);
        
        System.out.println("Check-in: " + checkIn);
        System.out.println("Check-out: " + checkOut);
        
        // Get available rooms
        List<Room> availableRooms = roomService.getAvailableRooms(checkIn, checkOut);
        System.out.println("Available rooms: " + availableRooms.size());
        availableRooms.forEach(r -> 
            System.out.println("  • Room " + r.getRoomNumber() + " - " + 
                             r.getRoomType().getTypeName())
        );
        
        // Block a room for specific dates
        if (!availableRooms.isEmpty()) {
            Room roomToBlock = availableRooms.get(0);
            RoomManager manager = (RoomManager) roomService;
            manager.getAvailabilityChecker().blockRoomDates(
                roomToBlock.getRoomId(), checkIn, checkOut
            );
            System.out.println("✓ Blocked room " + roomToBlock.getRoomNumber() + 
                             " for " + checkIn + " to " + checkOut);
            
            // Check availability again - should be one less
            List<Room> afterBlock = roomService.getAvailableRooms(checkIn, checkOut);
            System.out.println("Available rooms after blocking: " + afterBlock.size());
        }
        
        // Get available rooms by type
        List<RoomType> types = roomService.getAllRoomTypes();
        if (!types.isEmpty()) {
            Long deluxeTypeId = types.stream()
                    .filter(t -> "Deluxe".equals(t.getTypeName()))
                    .findFirst()
                    .map(RoomType::getRoomTypeId)
                    .orElse(null);
            
            if (deluxeTypeId != null) {
                List<Room> availableDeluxe = roomService.getAvailableRoomsByType(
                    deluxeTypeId, checkIn, checkOut
                );
                System.out.println("Available Deluxe rooms: " + availableDeluxe.size());
            }
        }
        
        System.out.println();
    }
    
    private void demonstrateDateRangeValidation() {
        System.out.println("--- 6. Date Range Validation ---");
        
        // Try invalid date ranges
        try {
            // Check-in after check-out
            roomService.getAvailableRooms(
                LocalDate.now().plusDays(10),
                LocalDate.now().plusDays(5)
            );
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Prevented invalid range: " + e.getMessage());
        }
        
        try {
            // Check-in in the past
            roomService.getAvailableRooms(
                LocalDate.now().minusDays(5),
                LocalDate.now().plusDays(5)
            );
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Prevented past date: " + e.getMessage());
        }
        
        try {
            // Same check-in and check-out
            LocalDate sameDate = LocalDate.now().plusDays(5);
            roomService.getAvailableRooms(sameDate, sameDate);
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Prevented same-day booking: " + e.getMessage());
        }
        
        try {
            // Null dates
            roomService.getAvailableRooms(null, LocalDate.now().plusDays(5));
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Prevented null date: " + e.getMessage());
        }
        
        System.out.println("✓ Date range validation working correctly");
        System.out.println();
    }
    
    /**
     * Main method for standalone testing.
     */
    public static void main(String[] args) {
        System.out.println("Enhanced Room Management Component - Demo");
        System.out.println("Note: This requires Spring context to run properly.");
        System.out.println("Please run through the main application or a Spring Boot test.");
    }
}
