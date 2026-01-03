package com.hotelsmarttrack.guest;

import com.hotelsmarttrack.base.entity.Guest;
import com.hotelsmarttrack.base.service.GuestService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * GuestManager - Implementation of GuestService.
 * Business logic for Guest Management (Rule 2 & 3).
 * This class is private to the smarttrack-guest component.
 */
@Service
public class GuestManager implements GuestService {
    
    // In-memory mock database (replace with JPA Repository in production)
    private final List<Guest> guestDatabase = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);
    
    @Override
    public Guest createGuest(String name, String email, String phone, String identificationNumber) {
        Guest guest = Guest.builder()
                .guestId(idGenerator.getAndIncrement())
                .name(name)
                .email(email)
                .phone(phone)
                .identificationNumber(identificationNumber)
                .status("Active")
                .build();
        guestDatabase.add(guest);
        System.out.println("[GuestManager] Created guest: " + name);
        return guest;
    }
    
    @Override
    public Guest updateGuest(Guest guest) {
        Optional<Guest> existing = getGuestById(guest.getGuestId());
        if (existing.isPresent()) {
            guestDatabase.remove(existing.get());
            guestDatabase.add(guest);
            System.out.println("[GuestManager] Updated guest: " + guest.getName());
        }
        return guest;
    }
    
    @Override
    public Optional<Guest> getGuestById(Long guestId) {
        return guestDatabase.stream()
                .filter(g -> g.getGuestId().equals(guestId))
                .findFirst();
    }
    
    @Override
    public List<Guest> searchGuests(String searchTerm) {
        String term = searchTerm.toLowerCase();
        return guestDatabase.stream()
                .filter(g -> 
                    g.getName().toLowerCase().contains(term) ||
                    g.getEmail().toLowerCase().contains(term) ||
                    g.getPhone().contains(term) ||
                    (g.getIdentificationNumber() != null && g.getIdentificationNumber().contains(term))
                )
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Guest> getAllGuests() {
        return new ArrayList<>(guestDatabase);
    }
    
    @Override
    public void deactivateGuest(Long guestId, String justification) {
        getGuestById(guestId).ifPresent(guest -> {
            guest.setStatus("Inactive");
            guest.setStatusJustification(justification);
            System.out.println("[GuestManager] Deactivated guest: " + guest.getName());
        });
    }
    
    @Override
    public void blacklistGuest(Long guestId, String justification) {
        getGuestById(guestId).ifPresent(guest -> {
            guest.setStatus("Blacklisted");
            guest.setStatusJustification(justification);
            System.out.println("[GuestManager] Blacklisted guest: " + guest.getName());
        });
    }
    
    @Override
    public void reactivateGuest(Long guestId) {
        getGuestById(guestId).ifPresent(guest -> {
            guest.setStatus("Active");
            guest.setStatusJustification(null);
            System.out.println("[GuestManager] Reactivated guest: " + guest.getName());
        });
    }
}
