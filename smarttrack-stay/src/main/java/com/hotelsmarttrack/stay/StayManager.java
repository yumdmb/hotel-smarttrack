package com.hotelsmarttrack.stay;

import com.hotelsmarttrack.base.entity.IncidentalCharge;
import com.hotelsmarttrack.base.entity.Stay;
import com.hotelsmarttrack.base.service.BillingService;
import com.hotelsmarttrack.base.service.ReservationService;
import com.hotelsmarttrack.base.service.RoomService;
import com.hotelsmarttrack.base.service.StayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * StayManager - Implementation of StayService.
 * Business logic for Check-In/Check-Out Management (Rule 2 & 3).
 * Demonstrates inter-component communication via service interfaces.
 */
@Service
public class StayManager implements StayService {
    
    private final List<Stay> stayDatabase = new ArrayList<>();
    private final List<IncidentalCharge> chargeDatabase = new ArrayList<>();
    private final AtomicLong stayIdGenerator = new AtomicLong(1);
    private final AtomicLong chargeIdGenerator = new AtomicLong(1);
    
    private final ReservationService reservationService;
    private final RoomService roomService;
    private final BillingService billingService;
    
    @Autowired
    public StayManager(ReservationService reservationService, 
                       RoomService roomService,
                       BillingService billingService) {
        this.reservationService = reservationService;
        this.roomService = roomService;
        this.billingService = billingService;
    }
    
    @Override
    public Stay checkInGuest(Long reservationId) {
        return reservationService.getReservationById(reservationId).map(reservation -> {
            Stay stay = new Stay();
            stay.setStayId(stayIdGenerator.getAndIncrement());
            stay.setReservation(reservation);
            stay.setGuest(reservation.getGuest());
            stay.setRoom(reservation.getAssignedRoom());
            stay.setCheckInTime(LocalDateTime.now());
            stay.setStatus("Active");
            
            stayDatabase.add(stay);
            
            // Update room status
            if (reservation.getAssignedRoom() != null) {
                roomService.updateRoomStatus(reservation.getAssignedRoom().getRoomId(), "Occupied");
            }
            
            System.out.println("[StayManager] Checked in guest: " + reservation.getGuest().getName());
            return stay;
        }).orElse(null);
    }
    
    @Override
    public Stay checkInWalkIn(Long guestId, Long roomId) {
        Stay stay = new Stay();
        stay.setStayId(stayIdGenerator.getAndIncrement());
        stay.setCheckInTime(LocalDateTime.now());
        stay.setStatus("Active");
        
        roomService.getRoomById(roomId).ifPresent(room -> {
            stay.setRoom(room);
            roomService.updateRoomStatus(roomId, "Occupied");
        });
        
        stayDatabase.add(stay);
        System.out.println("[StayManager] Walk-in check-in for room: " + roomId);
        return stay;
    }
    
    @Override
    public void assignRoomAndCredentials(Long stayId, Long roomId, String keyCardNumber) {
        getStayById(stayId).ifPresent(stay -> {
            roomService.getRoomById(roomId).ifPresent(room -> {
                stay.setRoom(room);
                stay.setKeyCardNumber(keyCardNumber);
                roomService.updateRoomStatus(roomId, "Occupied");
                System.out.println("[StayManager] Assigned room " + room.getRoomNumber() + 
                        " with key card: " + keyCardNumber);
            });
        });
    }
    
    @Override
    public IncidentalCharge recordCharge(Long stayId, String serviceType, 
                                          String description, BigDecimal amount) {
        IncidentalCharge charge = new IncidentalCharge();
        charge.setChargeId(chargeIdGenerator.getAndIncrement());
        charge.setServiceType(serviceType);
        charge.setDescription(description);
        charge.setAmount(amount);
        charge.setChargeTime(LocalDateTime.now());
        
        getStayById(stayId).ifPresent(charge::setStay);
        chargeDatabase.add(charge);
        
        System.out.println("[StayManager] Recorded charge: " + serviceType + " - $" + amount);
        return charge;
    }
    
    @Override
    public List<IncidentalCharge> getChargesForStay(Long stayId) {
        return chargeDatabase.stream()
                .filter(c -> c.getStay() != null && c.getStay().getStayId().equals(stayId))
                .collect(Collectors.toList());
    }
    
    @Override
    public void checkOutGuest(Long stayId) {
        getStayById(stayId).ifPresent(stay -> {
            stay.setCheckOutTime(LocalDateTime.now());
            stay.setStatus("Checked-Out");
            
            // Trigger billing
            billingService.generateInvoice(stayId);
            
            // Update room status to cleaning
            if (stay.getRoom() != null) {
                roomService.updateRoomStatus(stay.getRoom().getRoomId(), "Under Cleaning");
            }
            
            System.out.println("[StayManager] Checked out guest from room: " + 
                    (stay.getRoom() != null ? stay.getRoom().getRoomNumber() : "N/A"));
        });
    }
    
    @Override
    public BigDecimal getOutstandingBalance(Long stayId) {
        return billingService.getInvoiceByStay(stayId)
                .map(invoice -> billingService.getOutstandingBalance(invoice.getInvoiceId()))
                .orElse(BigDecimal.ZERO);
    }
    
    @Override
    public Optional<Stay> getStayById(Long stayId) {
        return stayDatabase.stream()
                .filter(s -> s.getStayId().equals(stayId))
                .findFirst();
    }
    
    @Override
    public Optional<Stay> getActiveStayByRoom(String roomNumber) {
        return stayDatabase.stream()
                .filter(s -> "Active".equals(s.getStatus()))
                .filter(s -> s.getRoom() != null && roomNumber.equals(s.getRoom().getRoomNumber()))
                .findFirst();
    }
    
    @Override
    public List<Stay> getActiveStays() {
        return stayDatabase.stream()
                .filter(s -> "Active".equals(s.getStatus()))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Stay> getGuestStayHistory(Long guestId) {
        return stayDatabase.stream()
                .filter(s -> s.getGuest() != null && s.getGuest().getGuestId().equals(guestId))
                .collect(Collectors.toList());
    }
}
