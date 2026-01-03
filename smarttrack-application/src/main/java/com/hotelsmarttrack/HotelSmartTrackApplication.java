package com.hotelsmarttrack;

import com.hotelsmarttrack.base.entity.Guest;
import com.hotelsmarttrack.base.entity.Reservation;
import com.hotelsmarttrack.base.entity.RoomType;
import com.hotelsmarttrack.base.service.BillingService;
import com.hotelsmarttrack.base.service.GuestService;
import com.hotelsmarttrack.base.service.ReservationService;
import com.hotelsmarttrack.base.service.RoomService;
import com.hotelsmarttrack.base.service.StayService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Hotel SmartTrack Application - Main Entry Point (SystemUI).
 * 
 * Uses @ComponentScan to discover all @Service beans across all modules.
 * Demonstrates component-based architecture with loose coupling via interfaces.
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.hotelsmarttrack")
public class HotelSmartTrackApplication implements CommandLineRunner {
    
    private final GuestService guestService;
    private final RoomService roomService;
    private final ReservationService reservationService;
    private final StayService stayService;
    private final BillingService billingService;
    
    // Spring injects implementations via constructor (Rule 3 & 5)
    public HotelSmartTrackApplication(GuestService guestService,
                                       RoomService roomService,
                                       ReservationService reservationService,
                                       StayService stayService,
                                       BillingService billingService) {
        this.guestService = guestService;
        this.roomService = roomService;
        this.reservationService = reservationService;
        this.stayService = stayService;
        this.billingService = billingService;
    }
    
    public static void main(String[] args) {
        SpringApplication.run(HotelSmartTrackApplication.class, args);
    }
    
    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n========================================");
        System.out.println("   HOTEL SMARTTRACK SYSTEM - DEMO");
        System.out.println("========================================\n");
        
        // 1. Create Room Types (Room Management Component)
        System.out.println("--- Setting up Room Types ---");
        RoomType standard = roomService.createRoomType("Standard", "Basic room", 2, BigDecimal.valueOf(100));
        RoomType deluxe = roomService.createRoomType("Deluxe", "Premium room", 3, BigDecimal.valueOf(200));
        
        // 2. Create Rooms (Room Management Component)
        System.out.println("\n--- Creating Rooms ---");
        roomService.createRoom("101", 1, standard.getRoomTypeId());
        roomService.createRoom("102", 1, standard.getRoomTypeId());
        roomService.createRoom("201", 2, deluxe.getRoomTypeId());
        
        // 3. Register a Guest (Guest Management Component)
        System.out.println("\n--- Registering Guest ---");
        Guest guest = guestService.createGuest("John Doe", "john@email.com", "0123456789", "A12345678");
        
        // 4. Create a Reservation (Reservation Management Component)
        System.out.println("\n--- Creating Reservation ---");
        Reservation reservation = reservationService.createReservation(
                guest.getGuestId(),
                standard.getRoomTypeId(),
                LocalDate.now(),
                LocalDate.now().plusDays(3),
                2,
                "Late check-in requested"
        );
        
        // 5. Confirm and Assign Room
        System.out.println("\n--- Confirming Reservation ---");
        reservationService.confirmReservation(reservation.getReservationId());
        reservationService.assignRoom(reservation.getReservationId(), 1L); // Room 101
        
        // 6. Check In Guest (Stay Management Component)
        System.out.println("\n--- Checking In Guest ---");
        var stay = stayService.checkInGuest(reservation.getReservationId());
        
        // 7. Record Incidental Charges
        System.out.println("\n--- Recording Incidental Charges ---");
        stayService.recordCharge(stay.getStayId(), "Minibar", "Drinks", BigDecimal.valueOf(25));
        stayService.recordCharge(stay.getStayId(), "Room Service", "Dinner", BigDecimal.valueOf(45));
        
        // 8. Check Out Guest (triggers Billing Component)
        System.out.println("\n--- Checking Out Guest ---");
        stayService.checkOutGuest(stay.getStayId());
        
        // 9. Process Payment (Billing Component)
        System.out.println("\n--- Processing Payment ---");
        var invoice = billingService.getInvoiceByStay(stay.getStayId());
        invoice.ifPresent(inv -> {
            billingService.processPayment(inv.getInvoiceId(), inv.getTotalAmount(), "Credit Card");
        });
        
        System.out.println("\n========================================");
        System.out.println("   DEMO COMPLETED SUCCESSFULLY");
        System.out.println("========================================\n");
        
        // Print summary
        System.out.println("Component Interactions Demonstrated:");
        System.out.println("  ✓ GuestService -> Guest Management");
        System.out.println("  ✓ RoomService -> Room Management");
        System.out.println("  ✓ ReservationService -> Reservation Management");
        System.out.println("  ✓ StayService -> Stay Management (Check-In/Out)");
        System.out.println("  ✓ BillingService -> Billing & Payment");
        System.out.println("\nAll components communicate via interfaces (Rule 3 & 5)");
    }
}
