# Hotel SmartTrack System

A component-based hotel management system built with Spring Boot 4.0.1 and OSGi, designed to demonstrate Component-Based Software Engineering (CBSE) principles.

## Table of Contents

- [Overview](#overview)
- [Architecture](#architecture)
- [Technology Stack](#technology-stack)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [Modules](#modules)
- [CBSE Design Rules](#cbse-design-rules)
- [Use Cases](#use-cases)
- [Development Team](#development-team)

## Overview

The Hotel SmartTrack System is a hotel management system designed to support and streamline the core operational activities of a hotel. This project demonstrates how a monolithic system can be redesigned using Component-Based Software Engineering (CBSE) principles to improve modularity, maintainability, and adaptability.

### Key Features

- Guest Management (registration, profiles, history tracking)
- Room Management (inventory, pricing, availability)
- Reservation Management (booking, modification, cancellation)
- Check-In/Check-Out Management (guest arrival/departure, incidental charges)
- Billing & Payment (invoice generation, payment processing)

### Target Users

- **Hotel Staff**: Managers, Receptionists, Housekeeping Staff
- **Guests**: For room search, reservations, and booking management

## Architecture

This system follows a **component-based architecture** with clear separation of concerns:

```
┌─────────────────────────────────────────────────────────┐
│              SmartTrack Application (SystemUI)          │
│                  (Entry Point Layer)                    │
└─────────────────────────────────────────────────────────┘
                          │
        ┌─────────────────┼─────────────────┐
        │                 │                 │
        ▼                 ▼                 ▼
┌──────────────┐  ┌──────────────┐  ┌──────────────┐
│    Guest     │  │     Room     │  │ Reservation  │
│  Component   │  │  Component   │  │  Component   │
└──────────────┘  └──────────────┘  └──────────────┘
        │                 │                 │
        ▼                 ▼                 ▼
┌──────────────┐  ┌──────────────────────────────────┐
│     Stay     │  │      Base Library                │
│  Component   │  │  (Entities + Service Interfaces) │
└──────────────┘  └──────────────────────────────────┘
        │
        ▼
┌──────────────┐
│   Billing    │
│  Component   │
└──────────────┘
```

### Dependency Depth

The componentized architecture achieves a maximum dependency depth of **1**, meaning:

- **Depth 0**: Base entities and service interfaces (no dependencies)
- **Depth 1**: Manager classes and SystemUI (depend only on base library)

This shallow dependency structure ensures loose coupling and high maintainability.

## Technology Stack

- **Java**: 17
- **Spring Boot**: 4.0.1
- **OSGi**: Core 8.0.0, Compendium 7.0.0
- **Build Tool**: Maven 3.x
- **Architecture**: Component-Based Software Engineering (CBSE)

## Project Structure

```
hotel-smarttrack/
├── smarttrack-base-library/      # Entities + Service Interfaces (Rule 1 & 5)
├── smarttrack-guest/             # Guest Management Component
├── smarttrack-room/              # Room Management Component
├── smarttrack-reservation/       # Reservation Management Component
├── smarttrack-stay/              # Check-In/Check-Out Component
├── smarttrack-billing/           # Billing & Payment Component
├── smarttrack-application/       # Application Entry Point (SystemUI)
├── docs/                         # Documentation
├── pom.xml                       # Parent POM
└── README.md                     # This file
```

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 17 or higher
- Apache Maven 3.6 or higher
- Git

### Installation

1. **Clone the repository**

   ```bash
   git clone <repository-url>
   cd hotel-smarttrack
   ```

2. **Build the project**

   ```bash
   mvn clean install -DskipTests
   ```

3. **Run the application**
   ```bash
   cd smarttrack-application
   mvn spring-boot:run
   ```

### Verification

After running the application, you should see Spring Boot startup logs indicating successful component initialization.

## Modules

### Base Library (`smarttrack-base-library`)

**Purpose**: Centralized repository for all entity classes and service interface definitions.

**Contains**:

- **Entities**: `Guest`, `Room`, `RoomType`, `Reservation`, `Stay`, `Invoice`, `Payment`, `IncidentalCharge`
- **Service Interfaces**: `GuestService`, `RoomService`, `ReservationService`, `StayService`, `BillingService`

**Dependency Depth**: 0

---

### Guest Management Component (`smarttrack-guest`)

**Responsibility**: Manage guest information and guest-related operations.

**Key Features**:

- Create, update, view, and deactivate guest profiles
- Search guest profiles by name, ID, phone, or email
- Retrieve guest stay and reservation history
- Manage guest status (active, blacklisted, inactive)

**Service Interface**: `GuestService`  
**Implementation**: `GuestManager`  
**Dependency Depth**: 1

---

### Room Management Component (`smarttrack-room`)

**Responsibility**: Manage room inventory, pricing, and availability.

**Key Features**:

- Create, update, retrieve, and delete room records
- Manage room operational status (Available, Occupied, Under Cleaning, Out of Service)
- Define and modify room pricing structures
- Display calendar-based room availability

**Service Interface**: `RoomService`  
**Implementation**: `RoomManager`  
**Dependency Depth**: 1

---

### Reservation Management Component (`smarttrack-reservation`)

**Responsibility**: Handle reservation lifecycle operations.

**Key Features**:

- Create, modify, and cancel reservations
- Search for available rooms based on criteria
- Assign and reassign rooms to reservations
- Track reservation status (Reserved, Confirmed, Cancelled, No-Show)

**Service Interface**: `ReservationService`  
**Implementation**: `ReservationManager`  
**Dependency Depth**: 1

---

### Stay Management Component (`smarttrack-stay`)

**Responsibility**: Manage check-in/check-out processes and stay records.

**Key Features**:

- Check-in guest with reservation verification
- Assign rooms and issue access credentials (key cards)
- Record incidental charges during stay
- Check-out guest with final billing

**Service Interface**: `StayService`  
**Implementation**: `StayManager`  
**Dependency Depth**: 1

---

### Billing & Payment Component (`smarttrack-billing`)

**Responsibility**: Handle billing activities and payment processing.

**Key Features**:

- Generate billing documents (invoices/folios)
- Compute total charges (room rates, taxes, discounts, services)
- Process and record payments (cash, card, digital wallet)
- Manage outstanding balances

**Service Interface**: `BillingService`  
**Implementation**: `BillingManager`  
**Dependency Depth**: 1

---

### Application Module (`smarttrack-application`)

**Responsibility**: Application entry point and user interface layer.

**Contains**: `SystemUI` class that orchestrates all business components through their service interfaces.

**Dependency Depth**: 1

## CBSE Design Rules

This project implements five key Component-Based Software Engineering rules:

### Rule 1: Separation of Entity Classes in Common Library

All data carrier entity classes are packaged into the base library for system-wide reuse. These classes contain no business logic, only data storage and transport functionality.

### Rule 2: Group Classes by Business Functionality

Function-related implementation classes are grouped into the same business component. Each component encapsulates a specific business area (Guest, Room, Reservation, Stay, Billing).

### Rule 3: Expose Business Functionality via Interfaces

Business functionality is exposed exclusively through service interfaces rather than concrete implementation classes. This ensures components depend on stable contracts, allowing internal changes without affecting other components.

### Rule 4: Group Mutually Dependent Classes Together

This rule does not apply to this system. Early design and planning ensured closely related classes are pre-classified into the same component, avoiding cross-component dependencies.

### Rule 5: Move Interface Definitions to Base Library

All service interface definitions are moved to the base library alongside entity classes. Components depend only on base library interfaces, not on each other's implementations.

## Use Cases

The system supports 20 use cases across 5 modules:

| Module                        | Use Cases               |
| ----------------------------- | ----------------------- |
| Guest Management              | UC1-UC4 (4 use cases)   |
| Room Management               | UC5-UC8 (4 use cases)   |
| Reservation Management        | UC9-UC12 (4 use cases)  |
| Check-In/Check-Out Management | UC13-UC16 (4 use cases) |
| Billing & Payment             | UC17-UC20 (4 use cases) |

For detailed use case descriptions, see [project-details.md](/docs/project-details.md).

## Documentation

- [Project Details](/docs/project-details.md) - Comprehensive project documentation with use cases and architecture details
- [Spring Boot OSGi Setup Guide](/docs/spring-boot-osgi-setup-guide.md) - Technical setup guide for Spring Boot and OSGi integration

## License

This project is developed for educational purposes as part of a Component-Based Software Engineering course.
