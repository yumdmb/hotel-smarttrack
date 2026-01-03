package com.hotelsmarttrack.base.service;

import com.hotelsmarttrack.base.entity.Reservation;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * ReservationService interface - exposes Reservation Management functionality.
 * Part of Base Library (Rule 5) - interface in common library.
 * Implemented by ReservationManager in smarttrack-reservation component.
 */
public interface ReservationService {
    
    /**
     * Create a new reservation.
     */
    Reservation createReservation(Long guestId, Long roomTypeId, LocalDate checkIn, 
                                   LocalDate checkOut, int numberOfGuests, String specialRequests);
    
    /**
     * Modify an existing reservation.
     */
    Reservation modifyReservation(Long reservationId, LocalDate newCheckIn, 
                                   LocalDate newCheckOut, int numberOfGuests);
    
    /**
     * Cancel a reservation.
     */
    void cancelReservation(Long reservationId);
    
    /**
     * Confirm a reservation.
     */
    void confirmReservation(Long reservationId);
    
    /**
     * Get reservation by ID.
     */
    Optional<Reservation> getReservationById(Long reservationId);
    
    /**
     * Get reservations by guest.
     */
    List<Reservation> getReservationsByGuest(Long guestId);
    
    /**
     * Get all reservations.
     */
    List<Reservation> getAllReservations();
    
    /**
     * Get reservations by status.
     */
    List<Reservation> getReservationsByStatus(String status);
    
    /**
     * Assign a specific room to a reservation.
     */
    void assignRoom(Long reservationId, Long roomId);
    
    /**
     * Reassign a room to a reservation.
     */
    void reassignRoom(Long reservationId, Long newRoomId);
    
    /**
     * Search available rooms for reservation criteria.
     */
    List<Long> searchAvailableRooms(LocalDate checkIn, LocalDate checkOut, 
                                     Long roomTypeId, int occupancy);
    
    /**
     * Mark reservation as no-show.
     */
    void markNoShow(Long reservationId);
    
    /**
     * Get reservation status.
     */
    String getReservationStatus(Long reservationId);
    
    /**
     * Get guest reservation history.
     */
    List<Reservation> getGuestReservationHistory(Long guestId);
}
