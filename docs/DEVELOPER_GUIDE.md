# Hotel SmartTrack - Developer Guide

A comprehensive guide for developing the Hotel SmartTrack system using **Spring Boot 4.0.1 + OSGi**.

> **Note on OSGi**: This project uses OSGi for **build-time architecture enforcement** and **IDE support**, not as a runtime container. The application runs in Spring Boot. OSGi validates module boundaries during compilation to maintain clean architecture. See [intellij-osgi-setup.md](intellij-osgi-setup.md) for IDE configuration.

---

## Table of Contents

- [Quick Reference](#quick-reference)
- [Module Overview](#module-overview)
- [Data Storage with H2 Database](#data-storage-with-h2-database)
- [Seed Data](#seed-data)
- [Team Development Strategy](#team-development-strategy)
- [Development Workflow](#development-workflow)
- [Adding New Features](#adding-new-features)
- [Dependency Rules (CBSE)](#dependency-rules-cbse)
- [OSGi Bundle Verification](#osgi-bundle-verification)
- [Common Issues & Fixes](#common-issues--fixes)

---

## Quick Reference

| Command                         | Purpose                                          |
| ------------------------------- | ------------------------------------------------ |
| `mvn clean install -DskipTests` | Build all modules (run from project root)        |
| `mvn spring-boot:run`           | Run application (from `smarttrack-application/`) |
| `mvn test`                      | Run all tests                                    |
| `mvn package`                   | Package modules as JARs/bundles                  |

---

## Module Overview

```
hotel-smarttrack/
├── pom.xml                      # Parent POM - manages all dependencies
├── smarttrack-base-library/     # Shared code (entities + interfaces)
├── smarttrack-guest/            # Guest Management business logic
├── smarttrack-room/             # Room Management business logic
├── smarttrack-reservation/      # Reservation Management business logic
├── smarttrack-stay/             # Check-In/Check-Out business logic
├── smarttrack-billing/          # Billing & Payment business logic
└── smarttrack-application/      # Main Spring Boot app (entry point)
```

### Module Descriptions

| Module                      | Type             | Purpose                                                                                                                                                                      |
| --------------------------- | ---------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **smarttrack-base-library** | OSGi Bundle      | Contains all shared entity classes (`Guest`, `Room`, etc.) and service interfaces (`GuestService`, `RoomService`, etc.). This is the foundation all other modules depend on. |
| **smarttrack-guest**        | OSGi Bundle      | Implements `GuestService` via `GuestManager`. Handles guest registration, profile management, status updates.                                                                |
| **smarttrack-room**         | OSGi Bundle      | Implements `RoomService` via `RoomManager`. Handles room creation, pricing, availability, status updates.                                                                    |
| **smarttrack-reservation**  | OSGi Bundle      | Implements `ReservationService` via `ReservationManager`. Handles booking lifecycle.                                                                                         |
| **smarttrack-stay**         | OSGi Bundle      | Implements `StayService` via `StayManager`. Handles check-in/check-out flow.                                                                                                 |
| **smarttrack-billing**      | OSGi Bundle      | Implements `BillingService` via `BillingManager`. Handles invoices and payments.                                                                                             |
| **smarttrack-application**  | JAR (Executable) | Spring Boot main class. Orchestrates all components.                                                                                                                         |

---

## H2 Database

This project uses **H2 embedded database** for data persistence. H2 is a lightweight, file-based database that requires no external server.

### Configuration

The database is configured in `smarttrack-application/src/main/resources/application.properties`:

```properties
# H2 Database (File-Based Persistence)
spring.datasource.url=jdbc:h2:file:./data/hotelsmarttrack;DB_CLOSE_ON_EXIT=FALSE;AUTO_RECONNECT=TRUE
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false

# Seed Data Initialization
spring.sql.init.mode=always
spring.jpa.defer-datasource-initialization=true
```

### Key Points

- **Data file location**: `./data/hotelsmarttrack.mv.db` (created automatically)
- **Persistence**: Data survives application restarts
- **H2 Console**: Access at `http://localhost:8080/h2-console` for debugging
- **DDL mode**: `update` preserves existing data; tables are created/updated as needed

### H2 Console Access

1. Start the application: `mvn spring-boot:run`
2. Open browser: `http://localhost:8080/h2-console`
3. Connection settings:
   - JDBC URL: `jdbc:h2:file:./data/hotelsmarttrack`
   - Username: `sa`
   - Password: _(leave empty)_

---

## Seed Data

The project includes seed data that is automatically loaded on startup.

### Location

`smarttrack-application/src/main/resources/data.sql`

### Contents

The seed data includes:

| Data Type      | Records | Description                     |
| -------------- | ------- | ------------------------------- |
| **Room Types** | 4       | Standard, Deluxe, Suite, Family |
| **Rooms**      | 8       | Rooms across 4 floors           |
| **Guests**     | 3       | Sample guest profiles           |

### How It Works

1. On application startup, Spring Boot executes `data.sql`
2. Uses `MERGE INTO` statements to avoid duplicate entries
3. All developers get the same initial data from Git

### Data Sharing Between Developers

| Data                           | Shared via Git? | Notes                                       |
| ------------------------------ | --------------- | ------------------------------------------- |
| `data.sql` (seed script)       | Yes             | Everyone starts with same initial data      |
| `data/*.mv.db` (database file) | No              | Each developer has their own local database |

---

## Team Development Strategy

This component-based architecture is designed for **parallel development** where each team member works on a separate module.

### Module Assignment

| Developer   | Module                   | Responsibility                                    |
| ----------- | ------------------------ | ------------------------------------------------- |
| Developer A | `smarttrack-room`        | Room types, room inventory, availability, pricing |
| Developer B | `smarttrack-guest`       | Guest registration, profiles, search, status      |
| Developer C | `smarttrack-reservation` | Booking lifecycle, room assignment                |
| Developer D | `smarttrack-stay`        | Check-in, check-out, incidental charges           |
| Developer E | `smarttrack-billing`     | Invoices, payments, balance tracking              |

### Parallel Development Flow

```
Phase 1: Parallel Development (Independent Work)
├── Developer A works on smarttrack-room/
├── Developer B works on smarttrack-guest/
├── Developer C works on smarttrack-reservation/
├── Developer D works on smarttrack-stay/
└── Developer E works on smarttrack-billing/

Phase 2: Integration (Combine Everything)
└── smarttrack-application/ brings all modules together
```

### Git Branch Strategy

```
main (protected - requires PR)
├── feature/room-module        ← Developer A
├── feature/guest-module       ← Developer B
├── feature/reservation-module ← Developer C
├── feature/stay-module        ← Developer D
└── feature/billing-module     ← Developer E
```

### Developer Workflow

1. **Clone and setup**:

```bash
git clone <repository-url>
cd hotel-smarttrack
mvn clean install -DskipTests
```

2. **Create feature branch**:

```bash
git checkout -b feature/room-module
```

3. **Work on your module**:

```bash
# Edit files in smarttrack-room/
# Test your changes
cd smarttrack-application
mvn spring-boot:run
```

4. **Commit and push**:

```bash
git add smarttrack-room/
git commit -m "Implement room availability check"
git push origin feature/room-module
```

5. **Create Pull Request** to merge into `main`

### Why This Works

Because of the CBSE architecture:

- **No merge conflicts**: Each developer works in a different folder
- **Interface contracts**: Components use interfaces, not implementations
- **Loose coupling**: Changes in one module don't break others
- **Independent testing**: Each module can be tested in isolation

### Inter-Module Communication

Components communicate through interfaces:

```java
// In ReservationManager (Developer C's code)
@Service
public class ReservationManager implements ReservationService {

    private final RoomService roomService;  // ← Uses Developer A's work
    private final GuestService guestService; // ← Uses Developer B's work

    @Autowired
    public ReservationManager(RoomService roomService, GuestService guestService) {
        this.roomService = roomService;
        this.guestService = guestService;
    }
}
```

**Key point**: Developer C uses `RoomService` interface, NOT `RoomManager` class directly.

### Integration Testing

After merging all branches to `main`:

```bash
git checkout main
git pull origin main
mvn clean install -DskipTests
cd smarttrack-application
mvn spring-boot:run
```

Spring Boot automatically discovers and wires all `@Service` beans.

---

## Development Workflow

### 1. After Making Code Changes

**Always rebuild from the project root:**

```powershell
cd d:\Dev\hotel-smarttrack
mvn clean install -DskipTests
```

> **Important**: You MUST run `install` from the parent project so modules are available to each other in the local Maven repository.

### 2. Running the Application

```powershell
cd smarttrack-application
mvn spring-boot:run
```

**Alternative (run the JAR directly):**

```powershell
java -jar smarttrack-application/target/smarttrack-application-1.0-SNAPSHOT.jar
```

### 3. Running Tests

```powershell
# All tests
mvn test

# Specific module
mvn test -pl smarttrack-guest
```

---

## Adding New Features

### Adding a New Entity

1. Create the entity class in `smarttrack-base-library/src/main/java/com/hotelsmarttrack/base/entity/`
2. Use JPA annotations with standard Java:

   ```java
   @Entity
   @Table(name = "your_table")
   public class YourEntity {
       @Id
       @GeneratedValue(strategy = GenerationType.IDENTITY)
       private Long id;
       // fields...

       // Default constructor
       public YourEntity() {}

       // All-args constructor
       public YourEntity(Long id, /* other fields */) {
           this.id = id;
           // ...
       }

       // Getters, Setters, equals(), hashCode(), toString()
   }
   ```

3. Rebuild: `mvn clean install -DskipTests`

### Adding a New Service Method

1. Add method signature to the interface in `smarttrack-base-library/.../service/`
2. Implement in the corresponding Manager class
3. Rebuild: `mvn clean install -DskipTests`

### Adding a New Component Module

1. Create folder `smarttrack-newmodule/`
2. Create `pom.xml` (copy from existing module, change artifactId)
3. Add to parent POM `<modules>` section
4. Add as dependency to `smarttrack-application`
5. Rebuild: `mvn clean install -DskipTests`

---

## Dependency Rules (CBSE)

### Allowed Dependencies

```
smarttrack-application  →  all modules
smarttrack-*            →  smarttrack-base-library
```

### NOT Allowed

```
smarttrack-guest  →  smarttrack-billing  (business to business)
smarttrack-base-library  →  any other module
```

### Inter-Component Communication

Components must communicate via **interfaces only**:

```java
@Service
public class StayManager implements StayService {

    // Correct: Inject interface, not implementation
    private final BillingService billingService;

    @Autowired
    public StayManager(BillingService billingService) {
        this.billingService = billingService;
    }
}
```

---

## OSGi Bundle Verification

Each business module is an OSGi bundle. The `maven-bundle-plugin` generates manifests during build to enforce module boundaries.

**What OSGi Does**:

- Validates that modules only import declared dependencies
- Prevents circular dependencies between components
- Enforces encapsulation (implementations are private)
- Provides IDE support for module boundaries
- NOT used as a runtime container (app runs in Spring Boot)

**Verify the manifest**:

```powershell
# Extract and view MANIFEST.MF
cd smarttrack-guest\target
jar xf smarttrack-guest-1.0-SNAPSHOT.jar META-INF/MANIFEST.MF
type META-INF\MANIFEST.MF
```

**Expected headers**:

```
Bundle-SymbolicName: smarttrack-guest
Export-Package: (empty for implementation modules)
Import-Package: com.hotelsmarttrack.base.entity, com.hotelsmarttrack.base.service
Private-Package: com.hotelsmarttrack.guest
```

These declarations are validated at **compile time**. If you try to import a package not listed in `Import-Package`, the build will fail.

---

## Common Issues & Fixes

### Issue: "Could not find artifact"

**Cause**: Modules not installed to local Maven repo
**Fix**: Run from project root: `mvn clean install -DskipTests`

### Issue: Circular dependency

**Cause**: Module A depends on Module B which depends on Module A
**Fix**: Use interfaces in base-library for communication

### Issue: Bean not found

**Cause**: @ComponentScan not finding your @Service
**Fix**: Ensure package is under `com.hotelsmarttrack`

### Issue: Data lost on restart

**Cause**: Using in-memory storage (ArrayList)
**Fix**: Configure H2 database with file persistence (see [Data Storage](#data-storage-with-h2-database))

---

## File Locations Quick Reference

| What                        | Location                                                                   |
| --------------------------- | -------------------------------------------------------------------------- |
| **Entity classes**          | `smarttrack-base-library/src/main/java/.../base/entity/`                   |
| **Service interfaces**      | `smarttrack-base-library/src/main/java/.../base/service/`                  |
| **Manager implementations** | `smarttrack-{module}/src/main/java/.../`                                   |
| **Main application**        | `smarttrack-application/src/main/java/.../HotelSmartTrackApplication.java` |
| **Application config**      | `smarttrack-application/src/main/resources/application.properties`         |
| **Seed data**               | `smarttrack-application/src/main/resources/data.sql`                       |

---

## Build Outputs

After `mvn package`, find JARs at:

```
smarttrack-base-library/target/smarttrack-base-library-1.0-SNAPSHOT.jar
smarttrack-guest/target/smarttrack-guest-1.0-SNAPSHOT.jar
smarttrack-application/target/smarttrack-application-1.0-SNAPSHOT.jar
```
