package com.hotelsmarttrack.reservation;

import com.hotelsmarttrack.base.entity.Reservation;
import com.hotelsmarttrack.base.service.GuestService;
import com.hotelsmarttrack.base.service.ReservationService;
import com.hotelsmarttrack.base.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * ReservationManager - Implementation of ReservationService.
 * Business logic for Reservation Management (Rule 2 & 3).
 * Demonstrates inter-component communication via service interfaces (Rule 3 & 5).
 */
@Service
public class ReservationManager implements ReservationService {
    
    private final List<Reservation> reservationDatabase = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);
    
    // Injected via interface - loose coupling (Rule 3 & 5)
    private final GuestService guestService;
    private final RoomService roomService;
    
    @Autowired
    public ReservationManager(GuestService guestService, RoomService roomService) {
        this.guestService = guestService;
        this.roomService = roomService;
    }
    
    @Override
    public Reservation createReservation(Long guestId, Long roomTypeId, LocalDate checkIn,
                                          LocalDate checkOut, int numberOfGuests, String specialRequests) {
        Reservation reservation = Reservation.builder()
                .reservationId(idGenerator.getAndIncrement())
                .checkInDate(checkIn)
                .checkOutDate(checkOut)
                .numberOfGuests(numberOfGuests)
                .specialRequests(specialRequests)
                .status("Reserved")
                .build();
        
        // Link guest via service interface
        guestService.getGuestById(guestId).ifPresent(reservation::setGuest);
        
        // Link room type via service interface
        roomService.getAllRoomTypes().stream()
                .filter(rt -> rt.getRoomTypeId().equals(roomTypeId))
                .findFirst()
                .ifPresent(reservation::setRoomType);
        
        reservationDatabase.add(reservation);
        System.out.println("[ReservationManager] Created reservation for guest ID: " + guestId);
        return reservation;
    }
    
    @Override
    public Reservation modifyReservation(Long reservationId, LocalDate newCheckIn,
                                          LocalDate newCheckOut, int numberOfGuests) {
        Optional<Reservation> reservation = getReservationById(reservationId);
        reservation.ifPresent(r -> {
            r.setCheckInDate(newCheckIn);
            r.setCheckOutDate(newCheckOut);
            r.setNumberOfGuests(numberOfGuests);
            System.out.println("[ReservationManager] Modified reservation: " + reservationId);
        });
        return reservation.orElse(null);
    }
    
    @Override
    public void cancelReservation(Long reservationId) {
        getReservationById(reservationId).ifPresent(r -> {
            r.setStatus("Cancelled");
            System.out.println("[ReservationManager] Cancelled reservation: " + reservationId);
        });
    }
    
    @Override
    public void confirmReservation(Long reservationId) {
        getReservationById(reservationId).ifPresent(r -> {
            r.setStatus("Confirmed");
            System.out.println("[ReservationManager] Confirmed reservation: " + reservationId);
        });
    }
    
    @Override
    public Optional<Reservation> getReservationById(Long reservationId) {
        return reservationDatabase.stream()
                .filter(r -> r.getReservationId().equals(reservationId))
                .findFirst();
    }
    
    @Override
    public List<Reservation> getReservationsByGuest(Long guestId) {
        return reservationDatabase.stream()
                .filter(r -> r.getGuest() != null && r.getGuest().getGuestId().equals(guestId))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Reservation> getAllReservations() {
        return new ArrayList<>(reservationDatabase);
    }
    
    @Override
    public List<Reservation> getReservationsByStatus(String status) {
        return reservationDatabase.stream()
                .filter(r -> status.equals(r.getStatus()))
                .collect(Collectors.toList());
    }
    
    @Override
    public void assignRoom(Long reservationId, Long roomId) {
        getReservationById(reservationId).ifPresent(r -> {
            roomService.getRoomById(roomId).ifPresent(room -> {
                r.setAssignedRoom(room);
                roomService.updateRoomStatus(roomId, "Occupied");
                System.out.println("[ReservationManager] Assigned room " + room.getRoomNumber() + 
                        " to reservation " + reservationId);
            });
        });
    }
    
    @Override
    public void reassignRoom(Long reservationId, Long newRoomId) {
        getReservationById(reservationId).ifPresent(r -> {
            // Release old room
            if (r.getAssignedRoom() != null) {
                roomService.updateRoomStatus(r.getAssignedRoom().getRoomId(), "Available");
            }
            // Assign new room
            assignRoom(reservationId, newRoomId);
        });
    }
    
    @Override
    public List<Long> searchAvailableRooms(LocalDate checkIn, LocalDate checkOut,
                                            Long roomTypeId, int occupancy) {
        return roomService.getAvailableRoomsByType(roomTypeId, checkIn, checkOut).stream()
                .filter(r -> r.getRoomType().getMaxOccupancy() >= occupancy)
                .map(r -> r.getRoomId())
                .collect(Collectors.toList());
    }
    
    @Override
    public void markNoShow(Long reservationId) {
        getReservationById(reservationId).ifPresent(r -> {
            r.setStatus("No-Show");
            System.out.println("[ReservationManager] Marked no-show: " + reservationId);
        });
    }
    
    @Override
    public String getReservationStatus(Long reservationId) {
        return getReservationById(reservationId)
                .map(Reservation::getStatus)
                .orElse("Not Found");
    }
    
    @Override
    public List<Reservation> getGuestReservationHistory(Long guestId) {
        return getReservationsByGuest(guestId);
    }
}
