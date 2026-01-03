# Hotel SmartTrack - Developer Guide

A comprehensive guide for developing the Hotel SmartTrack system using **Spring Boot 4.0.1 + OSGi**.

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

## Development Workflow

### 1. After Making Code Changes

**Always rebuild from the project root:**

```powershell
cd d:\Dev\hotel-smarttrack
mvn clean install -DskipTests
```

> ⚠️ **Important**: You MUST run `install` from the parent project so modules are available to each other in the local Maven repository.

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

### ✅ Allowed Dependencies

```
smarttrack-application  →  all modules
smarttrack-*            →  smarttrack-base-library
```

### ❌ NOT Allowed

```
smarttrack-guest  →  smarttrack-billing  (business to business)
smarttrack-base-library  →  any other module
```

### Inter-Component Communication

Components must communicate via **interfaces only**:

```java
@Service
public class StayManager implements StayService {

    // ✅ Correct: Inject interface, not implementation
    private final BillingService billingService;

    @Autowired
    public StayManager(BillingService billingService) {
        this.billingService = billingService;
    }
}
```

---

## OSGi Bundle Verification

Each business module is an OSGi bundle. Verify the manifest:

```powershell
# Extract and view MANIFEST.MF
cd smarttrack-guest\target
jar xf smarttrack-guest-1.0-SNAPSHOT.jar META-INF/MANIFEST.MF
type META-INF\MANIFEST.MF
```

**Expected headers:**

```
Bundle-SymbolicName: smarttrack-guest
Export-Package: (empty for implementation modules)
Import-Package: com.hotelsmarttrack.base.entity, com.hotelsmarttrack.base.service
Private-Package: com.hotelsmarttrack.guest
```

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

---

## File Locations Quick Reference

| What                        | Location                                                                   |
| --------------------------- | -------------------------------------------------------------------------- |
| **Entity classes**          | `smarttrack-base-library/src/main/java/.../base/entity/`                   |
| **Service interfaces**      | `smarttrack-base-library/src/main/java/.../base/service/`                  |
| **Manager implementations** | `smarttrack-{module}/src/main/java/.../`                                   |
| **Main application**        | `smarttrack-application/src/main/java/.../HotelSmartTrackApplication.java` |
| **Application config**      | `smarttrack-application/src/main/resources/application.properties`         |

---

## Build Outputs

After `mvn package`, find JARs at:

```
smarttrack-base-library/target/smarttrack-base-library-1.0-SNAPSHOT.jar
smarttrack-guest/target/smarttrack-guest-1.0-SNAPSHOT.jar
smarttrack-application/target/smarttrack-application-1.0-SNAPSHOT.jar
```
