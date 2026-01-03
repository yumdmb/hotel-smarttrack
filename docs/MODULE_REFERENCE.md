# Hotel SmartTrack - Module Reference

Quick reference for each module in the Hotel SmartTrack system.

---

## Base Library (`smarttrack-base-library`)

**Packaging**: `bundle` (OSGi)  
**Purpose**: Shared foundation - all entities and service interfaces

### Entities (8 classes)

| Entity             | Table                | Description                |
| ------------------ | -------------------- | -------------------------- |
| `Guest`            | `guests`             | Hotel guest profiles       |
| `RoomType`         | `room_types`         | Room category definitions  |
| `Room`             | `rooms`              | Individual room records    |
| `Reservation`      | `reservations`       | Booking records            |
| `Stay`             | `stays`              | Active/completed stays     |
| `IncidentalCharge` | `incidental_charges` | Extra services during stay |
| `Invoice`          | `invoices`           | Billing documents          |
| `Payment`          | `payments`           | Payment transactions       |

### Service Interfaces (5 interfaces)

| Interface            | Implemented By     | Methods                                                |
| -------------------- | ------------------ | ------------------------------------------------------ |
| `GuestService`       | GuestManager       | createGuest, updateGuest, searchGuests, blacklistGuest |
| `RoomService`        | RoomManager        | createRoom, updateRoomStatus, getAvailableRooms        |
| `ReservationService` | ReservationManager | createReservation, confirmReservation, assignRoom      |
| `StayService`        | StayManager        | checkInGuest, recordCharge, checkOutGuest              |
| `BillingService`     | BillingManager     | generateInvoice, processPayment, computeTotalCharges   |

---

## Business Components

### Guest Module (`smarttrack-guest`)

**Manager**: `GuestManager`  
**Use Cases**: UC1-UC4

- Create/update guest profiles
- Search guests by name, email, phone, ID
- Blacklist or deactivate guests
- Retrieve guest history

### Room Module (`smarttrack-room`)

**Manager**: `RoomManager`  
**Use Cases**: UC5-UC8

- Manage room types and pricing
- Track room status (Available, Occupied, Cleaning, Out of Service)
- Display room availability

### Reservation Module (`smarttrack-reservation`)

**Manager**: `ReservationManager`  
**Use Cases**: UC9-UC12

- Create/modify/cancel reservations
- Search available rooms
- Assign rooms to reservations
- Track reservation status

### Stay Module (`smarttrack-stay`)

**Manager**: `StayManager`  
**Use Cases**: UC13-UC16

- Check-in guests (with or without reservation)
- Issue key cards
- Record incidental charges (minibar, room service)
- Check-out and trigger billing

### Billing Module (`smarttrack-billing`)

**Manager**: `BillingManager`  
**Use Cases**: UC17-UC20

- Generate invoices
- Compute total charges (room + incidentals + tax - discounts)
- Process payments
- Track outstanding balances

---

## Application Module (`smarttrack-application`)

**Main Class**: `HotelSmartTrackApplication`  
**Packaging**: `jar` (executable)

- Spring Boot entry point
- Uses `@ComponentScan` to discover all services
- Includes demo workflow on startup
- Serves web requests on port 8080
