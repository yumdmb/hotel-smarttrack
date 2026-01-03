package com.hotelsmarttrack.room;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.hotelsmarttrack.base.entity.Room;

/**
 * Helper class to check room availability based on calendar dates.
 * In a production environment, this would integrate with the Reservation service
 * to check actual bookings. For now, it maintains a simple reservation tracker.
 */
@Component
public class RoomAvailabilityChecker {
    
    // Mock reservation data: roomId -> list of booked date ranges
    // In production, this would query the Reservation service
    private final Map<Long, List<DateRange>> roomReservations = new HashMap<>();
    
    /**
     * Check if a room is available for a specific date range.
     * 
     * @param room The room to check
     * @param checkIn Check-in date
     * @param checkOut Check-out date
     * @return true if the room is available for the entire period
     */
    public boolean isRoomAvailable(Room room, LocalDate checkIn, LocalDate checkOut) {
        // First check the room status - only Available rooms can be booked
        if (!RoomStatus.AVAILABLE.getDisplayName().equals(room.getStatus())) {
            return false;
        }
        
        // Check against existing reservations
        List<DateRange> reservations = roomReservations.getOrDefault(room.getRoomId(), new ArrayList<>());
        DateRange requestedRange = new DateRange(checkIn, checkOut);
        
        for (DateRange reserved : reservations) {
            if (requestedRange.overlaps(reserved)) {
                return false; // Conflict found
            }
        }
        
        return true;
    }
    
    /**
     * Block a room for a specific date range (simulating a reservation).
     * In production, this would be handled by the Reservation service.
     * 
     * @param roomId The room ID to block
     * @param checkIn Check-in date
     * @param checkOut Check-out date
     */
    public void blockRoomDates(Long roomId, LocalDate checkIn, LocalDate checkOut) {
        roomReservations.computeIfAbsent(roomId, k -> new ArrayList<>())
                .add(new DateRange(checkIn, checkOut));
    }
    
    /**
     * Release a blocked date range for a room.
     * 
     * @param roomId The room ID to release
     * @param checkIn Check-in date
     * @param checkOut Check-out date
     */
    public void releaseRoomDates(Long roomId, LocalDate checkIn, LocalDate checkOut) {
        List<DateRange> reservations = roomReservations.get(roomId);
        if (reservations != null) {
            reservations.removeIf(range -> 
                range.start.equals(checkIn) && range.end.equals(checkOut));
        }
    }
    
    /**
     * Get all reserved date ranges for a room (for calendar display).
     * 
     * @param roomId The room ID
     * @return List of reserved date ranges
     */
    public List<DateRange> getReservedDates(Long roomId) {
        return new ArrayList<>(roomReservations.getOrDefault(roomId, new ArrayList<>()));
    }
    
    /**
     * Clear all reservations (for testing purposes).
     */
    public void clearAllReservations() {
        roomReservations.clear();
    }
    
    /**
     * Inner class representing a date range.
     */
    public static class DateRange {
        private final LocalDate start;
        private final LocalDate end;
        
        public DateRange(LocalDate start, LocalDate end) {
            if (start == null || end == null) {
                throw new IllegalArgumentException("Start and end dates cannot be null");
            }
            if (start.isAfter(end)) {
                throw new IllegalArgumentException("Start date must be before or equal to end date");
            }
            this.start = start;
            this.end = end;
        }
        
        public LocalDate getStart() {
            return start;
        }
        
        public LocalDate getEnd() {
            return end;
        }
        
        /**
         * Check if this date range overlaps with another.
         * Two ranges overlap if they share any dates.
         */
        public boolean overlaps(DateRange other) {
            return !this.end.isBefore(other.start) && !this.start.isAfter(other.end);
        }
        
        @Override
        public String toString() {
            return "DateRange{" + start + " to " + end + '}';
        }
    }
}
