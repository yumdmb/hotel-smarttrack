package com.hotelsmarttrack.base.service;

import com.hotelsmarttrack.base.entity.IncidentalCharge;
import com.hotelsmarttrack.base.entity.Stay;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * StayService interface - exposes Check-In/Check-Out Management functionality.
 * Part of Base Library (Rule 5) - interface in common library.
 * Implemented by StayManager in smarttrack-stay component.
 */
public interface StayService {
    
    // ============ Check-In Operations ============
    
    /**
     * Check in a guest with a reservation.
     * @param reservationId Reservation ID
     * @return Created stay record
     */
    Stay checkInGuest(Long reservationId);
    
    /**
     * Check in a walk-in guest (no prior reservation).
     * @param guestId Guest ID
     * @param roomId Room ID
     * @return Created stay record
     */
    Stay checkInWalkIn(Long guestId, Long roomId);
    
    /**
     * Assign room and issue key card.
     * @param stayId Stay ID
     * @param roomId Room ID
     * @param keyCardNumber Key card number
     */
    void assignRoomAndCredentials(Long stayId, Long roomId, String keyCardNumber);
    
    // ============ Incidental Charges ============
    
    /**
     * Record an incidental charge during stay.
     * @param stayId Stay ID
     * @param serviceType Type of service (F&B, Laundry, etc.)
     * @param description Charge description
     * @param amount Charge amount
     * @return Created charge record
     */
    IncidentalCharge recordCharge(Long stayId, String serviceType, String description, BigDecimal amount);
    
    /**
     * Get all charges for a stay.
     */
    List<IncidentalCharge> getChargesForStay(Long stayId);
    
    // ============ Check-Out Operations ============
    
    /**
     * Check out a guest.
     * @param stayId Stay ID
     */
    void checkOutGuest(Long stayId);
    
    /**
     * Get outstanding balance for a stay.
     */
    BigDecimal getOutstandingBalance(Long stayId);
    
    // ============ Stay Queries ============
    
    /**
     * Get stay by ID.
     */
    Optional<Stay> getStayById(Long stayId);
    
    /**
     * Get active stay by room number.
     */
    Optional<Stay> getActiveStayByRoom(String roomNumber);
    
    /**
     * Get all active stays.
     */
    List<Stay> getActiveStays();
    
    /**
     * Get stay history for a guest.
     */
    List<Stay> getGuestStayHistory(Long guestId);
}
