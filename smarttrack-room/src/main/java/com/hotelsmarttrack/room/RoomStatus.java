package com.hotelsmarttrack.room;

/**
 * Enum representing room operational statuses.
 * This provides type-safe status validation for the Room Management Component.
 */
public enum RoomStatus {
    /**
     * Room is available for booking
     */
    AVAILABLE("Available"),
    
    /**
     * Room is currently occupied by a guest
     */
    OCCUPIED("Occupied"),
    
    /**
     * Room is being cleaned
     */
    UNDER_CLEANING("Under Cleaning"),
    
    /**
     * Room is out of service (maintenance, repairs, etc.)
     */
    OUT_OF_SERVICE("Out of Service");
    
    private final String displayName;
    
    RoomStatus(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    /**
     * Get RoomStatus from display name string.
     * Validates that the status is one of the allowed values.
     * 
     * @param displayName The status display name
     * @return The corresponding RoomStatus enum
     * @throws IllegalArgumentException if the status is invalid
     */
    public static RoomStatus fromDisplayName(String displayName) {
        if (displayName == null || displayName.trim().isEmpty()) {
            throw new IllegalArgumentException("Room status cannot be null or empty");
        }
        
        for (RoomStatus status : values()) {
            if (status.displayName.equalsIgnoreCase(displayName.trim())) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid room status: '" + displayName + 
            "'. Valid statuses are: Available, Occupied, Under Cleaning, Out of Service");
    }
    
    /**
     * Check if the status allows booking
     */
    public boolean isAvailableForBooking() {
        return this == AVAILABLE;
    }
}
