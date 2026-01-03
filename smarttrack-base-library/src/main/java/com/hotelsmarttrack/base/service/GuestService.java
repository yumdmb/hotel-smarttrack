package com.hotelsmarttrack.base.service;

import com.hotelsmarttrack.base.entity.Guest;
import java.util.List;
import java.util.Optional;

/**
 * GuestService interface - exposes Guest Management functionality.
 * Part of Base Library (Rule 5) - interface in common library.
 * Implemented by GuestManager in smarttrack-guest component.
 */
public interface GuestService {
    
    /**
     * Create a new guest profile.
     * @param name Guest name
     * @param email Guest email
     * @param phone Guest phone number
     * @param identificationNumber Guest ID number
     * @return Created guest
     */
    Guest createGuest(String name, String email, String phone, String identificationNumber);
    
    /**
     * Update an existing guest profile.
     * @param guest Guest with updated information
     * @return Updated guest
     */
    Guest updateGuest(Guest guest);
    
    /**
     * Retrieve a guest by ID.
     * @param guestId Guest ID
     * @return Guest if found
     */
    Optional<Guest> getGuestById(Long guestId);
    
    /**
     * Search guests by criteria.
     * @param searchTerm Name, email, phone, or ID number
     * @return List of matching guests
     */
    List<Guest> searchGuests(String searchTerm);
    
    /**
     * Get all guests.
     * @return List of all guests
     */
    List<Guest> getAllGuests();
    
    /**
     * Deactivate a guest profile.
     * @param guestId Guest ID
     * @param justification Reason for deactivation
     */
    void deactivateGuest(Long guestId, String justification);
    
    /**
     * Mark a guest as blacklisted.
     * @param guestId Guest ID
     * @param justification Reason for blacklisting
     */
    void blacklistGuest(Long guestId, String justification);
    
    /**
     * Reactivate a guest profile.
     * @param guestId Guest ID
     */
    void reactivateGuest(Long guestId);
}
