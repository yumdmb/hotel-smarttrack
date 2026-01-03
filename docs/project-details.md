Project Description
Background of the System
The Hotel SmartTrack System is a hotel management system designed to support and streamline the core operational activities of a hotel. Hotels must manage a variety of interrelated processes such as guest registration, room allocation, reservations, billing, payment tracking, and service usage monitoring. In many organisations, these operations are handled using monolithic hotel management systems, where all functionalities and business logic are implemented within a single, tightly-integrated software structure.

Although monolithic systems are straightforward to develop initially, they gradually become complex and difficult to maintain as the system evolves. Any modification in one part of the system such as pricing rules, reservation policies, or billing processes could affect other modules due to tight coupling. This increases maintenance effort, slows down updates, and limits scalability. Therefore, the Hotel SmartTrack System provides a meaningful case study on how a monolithic system can be analysed and later redesigned using Component-Based Software Engineering (CBSE) principles to improve modularity and adaptability.

Objectives
The main objective of this assignment is to apply CBSE concepts in analysing and improving a monolithic application. In particular, the assignment aims to:
Model the Hotel SmartTrack System as a monolithic application using UML diagrams.
Identify and analyse dependency depth among system modules and classes to understand how tightly they are coupled.
Redesign the system into a component-based architecture by following established CBSE development rules.
Re-evaluate the dependency depth after componentization to highlight improvements in modularity, maintainability, and ease of accommodating changes.

Target User
Both hotel staff and guests are the target users of this hotel management system. Hotel employees like housekeepers, managers, and receptionists use this system. Hotel employees are capable of managing reservations for guests, processing guest arrivals and departures, room availability, billing, and other daily tasks. In order for visitors to look for rooms, make reservations, manage their bookings, and complete payment processes, they interact with this system in a concurrent manner. As a result, the system is made to facilitate easy communication and effective service between the hotel and its visitors while assisting personnel in centralizing and organizing activities.

System Scope
The scope of this assignment focuses on the core hotel operation modules within the Hotel SmartTrack System. Authentication and security modules are intentionally excluded, as required by the assignment. The system comprises five main functional modules, each containing at least four key functionalities.

Module
Functionalities

Guest Management Module
(Ma Wenting)

Manage Guest Records
The system shall allow authorized staff to create, update, retrieve, and deactivate guest profiles.
Retrieve Guest Stay and Reservation History
The system shall generate a complete historical record of a guest’s past stays and reservations.
Manage Guest Status
The system shall allow authorized staff to mark a guest as blacklisted or inactive, including recording a justification.
Search Guest Profiles
The system shall provide a search function for locating guests using name, email, phone number, or identification number.

Room Management Module
(Eisraq Rejab)

Manage Room Records
The system shall support creating, updating, retrieving, and deleting room records and room-type definitions.
Manage Room Operational Status
The system shall allow staff to update room status Available, Occupied, Under Cleaning, Out of Service.
Manage Room Pricing
The system shall allow authorized personnel to define and modify room pricing structures.
Display Room Availability
The system shall present a calendar-based view of room availability across specified date ranges.

Reservation Management Module
(Li Yuhang)

Perform Reservation Operations
The system shall allow staff or guests to create, modify, and cancel reservations.
Search Available Rooms
The system shall provide real-time availability results based on dates, room type, and occupancy requirements.
Manage Reservation Allocation
The system shall allow assignment and reassignment of rooms to reservations based on availability and business rules.
Track Reservation Status
The system shall maintain reservation states (e.g., Reserved, Confirmed, Cancelled, No-Show) and update them accordingly.

Check-In / Check-Out Management Module
(Elvis Sawing)

Check-In Guest
The system shall verify reservation details and confirm guest arrival to complete the check-in process.
Assign Room and Access Credentials
The system shall assign rooms and issue room-access credentials (e.g., key cards) during the check-in process.
Record Incidental Charges
The system shall record additional services or consumables (such as F&B, laundry, or minibar usage) during a guest’s stay.
Check-Out Guest
The system shall finalize the stay, compute outstanding charges, trigger room-cleaning status, and close the check-out process.

Billing & Payment Module
( Huang Di)

Generate Billing Documents
The system shall create invoices or folios containing all room charges, taxes, fees, and incidental items.
Compute Total Charges
The system shall calculate the total amount payable based on room rates, duration of stay, taxes, discounts, and recorded services.
Process and Record Payments
The system shall record payment transactions, including method, amount, and payment status (cash, card, or digital wallet).
Manage Outstanding Balances
The system shall track unpaid or partially paid invoices and allow authorized personnel to view and update payment status.

## Use Case Summary Table

| UC ID | Use Case Name                             | Module                        | Primary Actor(s)                 | Brief Description                                                           |
| ----- | ----------------------------------------- | ----------------------------- | -------------------------------- | --------------------------------------------------------------------------- |
| UC1   | Manage Guest Records                      | Guest Management              | Manager                          | Create, update, view, or deactivate guest profiles                          |
| UC2   | Search Guest Profiles                     | Guest Management              | Manager                          | Locate guest information using search criteria (name, ID, phone, email)     |
| UC3   | Retrieve Guest Stay & Reservation History | Guest Management              | Manager                          | Generate complete historical record of guest's past stays and reservations  |
| UC4   | Manage Guest Status                       | Guest Management              | Manager                          | Mark guest as blacklisted or inactive with justification                    |
| UC5   | Manage Room Records                       | Room Management               | Manager, Receptionist            | Create, update, retrieve, and delete room records and room-type definitions |
| UC6   | Manage Room Operational Status            | Room Management               | Receptionist, Housekeeping Staff | Update room status (Available, Occupied, Under Cleaning, Out of Service)    |
| UC7   | Manage Room Pricing                       | Room Management               | Manager                          | Define and modify room pricing structures                                   |
| UC8   | Display Room Availability                 | Room Management               | Receptionist, Guest              | Present calendar-based view of room availability across date ranges         |
| UC9   | Perform Reservation Operations            | Reservation Management        | Guest, Receptionist              | Create, modify, or cancel room reservations                                 |
| UC10  | Search for Available Rooms                | Reservation Management        | Guest, Receptionist              | Provide real-time availability based on dates, room type, and occupancy     |
| UC11  | Manage Reservation Allocation             | Reservation Management        | Receptionist                     | Assign and reassign rooms to reservations                                   |
| UC12  | Track Reservation Status                  | Reservation Management        | Guest, Receptionist              | View current reservation status (Reserved, Confirmed, Cancelled, No-Show)   |
| UC13  | Check In Guest                            | Check-In/Check-Out Management | Receptionist                     | Verify reservation details and confirm guest arrival                        |
| UC14  | Assign Room and Access Credentials        | Check-In/Check-Out Management | Receptionist                     | Assign rooms and issue access credentials (key cards)                       |
| UC15  | Record Incidental Charges                 | Check-In/Check-Out Management | Receptionist                     | Record additional services or consumables during guest stay                 |
| UC16  | Check Out Guest                           | Check-In/Check-Out Management | Receptionist                     | Finalize stay, compute charges, trigger cleaning status, close checkout     |
| UC17  | Generate Billing Documents                | Billing & Payment             | Receptionist, Manager            | Create invoices/folios with all charges, taxes, fees, and services          |
| UC18  | Process & Record Payments                 | Billing & Payment             | Receptionist                     | Record payment transactions including method and amount                     |
| UC19  | Manage Outstanding Balances               | Billing & Payment             | Manager, Receptionist            | Track and update unpaid or partially paid invoices                          |
| UC20  | Compute Total Charges                     | Billing & Payment             | System (Automated)               | Calculate total payable amount including room charges, taxes, and discounts |

---

## Detailed Use Case Descriptions

### Guest Management Module (Ma Wenting)

Use Case Name
UC1-Manage Guest Records
Description
Allows authorized staff to create, update, view, or deactivate a guest profile within the system.
Actor(s)
Manager
Pre-condition
The actor is authenticated and authorized to access guest information.
Post-condition
Guest profile is created, updated, viewed, or deactivated successfully.
All changes are saved into the Guest Management database.
Main Flow
The actor selects “Manage Guest Records” from the system menu.
System displays available actions (Create, Update, View, Deactivate).
The actor chooses the desired action.
The system processes the request and updates or retrieves guest data.
The system displays confirmation or requested information.
Alternative Flow
A1 – Update existing record 1. Actor selects “Update”. 2. Actor modifies guest information. 3. System validates and saves the updated details.
A2 – Deactivate profile 1. Actor selects “Deactivate”. 2. Actor provides reason for deactivation. 3. System updates guest status accordingly
Exception Flow
E1 – Missing or invalid data
• The system rejects the request and shows an error message.
• The actor is asked to re-enter valid information.
E2 – Guest record not found (for Update/View/Deactivate)
• The system displays “Guest Not Found”.

Use Case Name
UC2-Search Guest Profiles
Description
Allows staff to locate guest information using search criteria such as name, ID number, phone number, or email.
Actor(s)
Manager
Pre-condition
• The actor is authenticated.
• Guest records exist in the system.
Post-condition
System returns a list of matching guest profiles or indicates no match.
Main Flow

1. The actor selects “Search Guest Profiles”.
2. Actors enter search criteria.
3. The system retrieves matching records.
4. The system displays the results.
   Alternative Flow
   Multiple results found
   • The system displays a list and prompts actors to select one.
   Exception Flow
   No matching records
   • System displays “No guest found

Use Case Name
UC3-Retrieve Guest Stay & Reservation History
Description
Generates a complete historical record of a guest’s past stays and reservations by retrieving data from the Reservation Management Module.
Actor(s)
Manager
Pre-condition
• Guest exists in the Guest Management Module.
• Reservation Management Module is available.
• The actor has selected a valid guest profile.
Post-condition
Guest history is retrieved and displayed.
Main Flow

1. The actor selects “Retrieve Guest Stay & Reservation History”.
2. The system requests reservation data from the Reservation Management Module.
3. Reservation Module returns reservation status and history.
4. The system compiles and displays guest stay history.
   Alternative Flow
   Guest has no past reservations
   • The system displays “No history available”.
   Exception Flow
   E1 – Reservation Module is offline / unreachable
   • The system returns an error: “Unable to retrieve reservation history.”
   E2 – Guest profile not selected
   • The system prompts actor to select a guest first.

Use Case Name
UC4-Manage Guest Status
Description
Allows authorized staff to mark a guest as blacklisted or inactive and store a justification.
Actor(s)
Manager
Pre-condition
• Guest records exists.
• The actor has authorization to update guest status.
Post-condition
Guest status is updated to Blacklisted or Inactive.
Main Flow

1. The actor selects “Manage Guest Status”.
2. The actor chooses a status option (Blacklist / Inactive).
3. The actor enters justification.
4. The system updates the guest status.
5. The system confirms the update.
   Alternative Flow
   Status reactivation
   • The actor sets guest back to “Active”.
   • The system updates the status and saves changes.
   Exception Flow
   E1 – Actor lacks permission
   • The system denies access and shows “Unauthorized”.
   E2 – Justification missing
   • The system rejects the request and prompts actor to provide justification.

Room Management Module (Eisraq Rejab)
Use Case Name
UC5-Manage Room Records
Description
Allow authorized staff to create, update, retrieve and delete room records.
Actor(s)
Hotel Manager, Receptionist
Pre-condition
Users must be authenticated.
Post-condition
Room record is created, updated, retrieved, or deleted in the system database.
Main Flow
The actor selects “Manage Room Records.”
The system shows room list and actions (Create / Update / View / Delete).
The actor chooses an action.
The actor enters or edits room details.
The actor submits.
The system saves changes and confirms.
Alternative Flow

- Exception Flow
  6.1 If a user enters an invalid input, the system will display an error.

Use Case Name
UC6-Manage Room Operational Status
Description
Staff can update the status of a room (Available, Occupied, Under Cleaning, Out of Service).
Actor(s)
Receptionist, Housekeeping Staff
Pre-condition
Room exists in the system.
Post-condition
Room status is updated.
Main Flow
The actor selects a room.
The actor chooses “Update Status.”
The actor selects a new status.
The actor submits.
System updates and confirms.
Alternative Flow

- Exception Flow
-

Use Case Name
UC7-Manage Room Pricing
Description
Manager sets or updates room pricing.
Actor(s)
Hotel Manager
Pre-condition
The user is logged in as a manager.
Post-condition
Room pricing is updated.
Main Flow
Manager opens “Room Pricing.”
The system shows a list of room types and prices.
The manager selects a room type.
The manager enters a new price.
Manager submits.
System saves and confirms.
Alternative Flow

- Exception Flow
  5.1 If the hotel manager enters an invalid input, the system will show an error.

Use Case Name
UC8-Display Room Availability
Description
The system shows room availability for selected dates.
Actor(s)
Receptionist, Guest
Pre-condition
Room and reservation data exist.
Post-condition
Room availability is displayed.
Main Flow
The actor selects “Check Room Availability.”
The actor enters the date range.
The actor submits.
The system retrieves data.
The system displays available rooms.
Alternative Flow

- Exception Flow
  3.1 If the actor enters an invalid date range, the system displays an error message and requests the actor to correct the dates.
  4.1 If no rooms are available for the selected dates, the system displays a “No rooms available” message.

Reservation Management Module (Li Yuhang)
Use Case Name
UC9-Perform Reservation Operations
Description
Allow guests or receptionists to create, modify, or cancel room reservations.
Actor(s)
Guest, Receptionist
Pre-condition
User is logged in.
Post-condition
Reservation is created, updated, or cancelled in the system
Main Flow
The actor selects “Reservation Operations.”
The system displays options (Create / Modify / Cancel).
The actor chooses an option.
The actor enters or updates reservation details.
The actor confirms the action.
The system saves the reservation changes.
Alternative Flow

- Exception Flow
  6.1 If required details are missing or invalid, the system shows an error.

Use Case Name
UC10-Search for Available Rooms
Description
Allow the actor to check available rooms based on date, type, or occupancy.
Actor(s)
Receptionist, Guest
Pre-condition
Requested dates and criteria are provided.
Post-condition
System displays the list of available rooms.
Main Flow
The actor selects “Search for Available Rooms.”
The actor enters search criteria (dates, room type).
The system retrieves matching available rooms.
The system displays the results.
Alternative Flow
4.1 If no rooms match the criteria, the system shows “No rooms available.”
Exception Flow
2.1 If input format is invalid, the system prompts correction.

Use Case Name
UC11-Manage Reservation Allocation
Description
Assign or update a room for a confirmed reservation.
Actor(s)
Receptionist
Pre-condition
A valid reservation exists.
Post-condition
Room allocation is updated in the system.
Main Flow
The actor selects “Manage Reservation Allocation.”
The system displays reservation details.
The actor selects a room to assign or change.
The actor confirms the allocation.
The system updates and saves the allocation.
Alternative Flow
3.1 If the selected room is no longer available, the system requests a new room selection.
Exception Flow
1.1 If the reservation cannot be found, the system displays an error.

Use Case Name
UC12-Track Reservation Status
Description
Allow the actor to view the current status of a reservation.
Actor(s)
Receptionist, Guest
Pre-condition
Reservation ID or guest information must be provided.
Post-condition
Reservation status is shown to the actor.
Main Flow
The actor selects “Track Reservation Status.”
The actor enters reservation details.
The system retrieves the reservation information.
The system displays the reservation status.
Alternative Flow
4.1 If the reservation was updated recently, the system shows the latest status.
Exception Flow
3.1 If the reservation does not exist, the system displays an error.

Check-In / Check-Out Management Module (Elvis Sawing)
Use Case Name
UC13-Check In Guest
Description
The system verifies reservation details and confirms guest arrival to complete the check-in process.
Actor(s)
Receptionist
Pre-condition
The guest must have a valid reservation or walk-in request. The guest must be physically present.
Post-condition
The guest is checked in and a record is created
Main Flow
The Receptionist selects "Check In Guest".
The Receptionist enters the guest's name or Reservation ID.
The system retrieves and displays the reservation details.
The Receptionist verifies the guest's identity documents.
The system performs the Assign Room and Access Credentials use case.
The system confirms the check-in is complete.
Alternative Flow
2.1 If no reservation exists, the Receptionist creates a new reservation and guest profile first.
Exception Flow
3.1 If the reservation is "Cancelled" or "No-Show", the system alerts the Receptionist and prohibits check-in until reinstated.

Use Case Name
UC14-Assign Room and Access Credentials
Description
The system assigns rooms and issues room-access credentials (e.g., key cards) during the check-in process.
Actor(s)
Receptionist
Pre-condition
The check-in process has been initiated. A room matching the reserved type must be available.
Post-condition
A specific room number is assigned to the stay and the key card is active.
Main Flow
The system identifies an available room matching the reserved Room Type.
The Receptionist confirms the specific room number assignment.
The Receptionist places a key card on the encoder.
The system writes the access code to the key card.
The system links the key code to the current record.
Alternative Flow

- Exception Flow
  4.1 If the key encoder fails or is disconnected, the system displays an error "Device Connection Failed" and prompts to retry.

Use Case Name
UC15-Record Incidental Charges
Description
The system records additional services or consumables (such as F&B, laundry, or minibar usage) during a guest’s stay.
Actor(s)
Receptionist
Pre-condition
The guest must be currently checked in.
Post-condition
The charge amount is added to the guest's total outstanding balance.
Main Flow
The Receptionist selects "Record Charge".
The Receptionist enters the Room Number.
The system displays the current guest details.
The Receptionist selects the Service Type.
The Receptionist enters the amount and description.
The Receptionist submits the charge.
The system saves the charge and updates the total bill.
Alternative Flow

- Exception Flow
  2.1 If the Room Number is invalid or the room is empty, the system displays "No Active Stay Found".

Use Case Name
UC16-Check Out Guest
Description
The system finalizes the stay, computes outstanding charges, triggers room-cleaning status, and closes the check-out process.
Actor(s)
Receptionist
Pre-condition
The guest must be currently checked in.
Post-condition
The stay is closed and payment is recorded.
Main Flow
The Receptionist selects "Check Out Guest" and enters the Room Number.
The system retrieves the stay details and calculates the Outstanding Balance (Total Room Charges + Incidental Charges).
The system generates and displays the final billing invoice.
The Receptionist collects the full payment and records it in the system.
The system finalizes the transaction and archives the stay record.
Alternative Flow
2.1 If the current time is past the standard check-out time, the system adds a "Late Fee" to the balance.
4.1 If the guest fully pre-paid online (Balance = 0), the system skips the payment collection and proceeds directly to closing the stay.
Exception Flow
6.1 If the payment is declined, the system prompts for an alternative payment

Billing & Payment Module (Huang Di)
Use Case Name
UC17-Generate Billing Documents
Description
Creates a billing document for a guest at checkout or upon request. Automatically includes charges by invoking the Compute Total Charges process.
Actor(s)
Receptionist, Hotel Manager
Pre-condition
Guest has outstanding charges or requires a billing statement.
Post-condition
A billing document is generated and stored in the system.
Main Flow
The actor selects “Generate Billing Documents.”
The system retrieves guest stay and charge information.
The system computes all charges (includes → Compute Total Charges).
The system produces the billing document.
The actor reviews or prints the document.
Alternative Flow

- Exception Flow
  5.1 If the billing document cannot be created, the system displays an error.

Use Case Name
UC18-Process & Record Payments
Description
Handles the recording of guest payments using available methods (cash, card, online).
Actor(s)
Receptionist
Pre-condition
Billing document must exist.
Post-condition
Payment is recorded and the guest balance is updated.
Main Flow
The actor selects “Process & Record Payments.”
The system displays the total amount due.
The actor enters payment information.
The system verifies and records the payment.
The system updates the guest’s balance.
Alternative Flow
3.1 Actor cancels the payment → System returns to previous screen.
Exception Flow
4.1 If payment fails or is invalid, the system shows an error.

Use Case Name
UC19-Manage Outstanding Balances
Description
Allows staff to review unpaid balances and take follow-up actions.
Actor(s)
Hotel Manager, Receptionist
Pre-condition
Guest account exists in the system.
Post-condition
Outstanding balance information is viewed or updated.
Main Flow
The actor selects “Manage Outstanding Balances.”
The system displays a list of guests with unpaid amounts.
The actor views or updates payment status.
The system saves any changes.
Alternative Flow

- Exception Flow
  2.1 If no outstanding balances exist, the system shows an empty list.

Use Case Name
UC20-Compute Total Charges
Description
Calculates the total amount a guest needs to pay, including room charges, taxes, discounts, and incidental fees.
Actor(s)
System (no direct human actor)
Pre-condition
Guest stay information and recorded charges must exist.
Post-condition
A computed total charge value is returned to the calling use case.
Main Flow
The system retrieves room charges and stay duration.
The system retrieves any additional or incidental charges.
The system applies tax and discounts as applicable.
The system calculates the final total amount.
The total amount is returned to the calling use case (Generate Billing Documents).
Alternative Flow

- Exception Flow
  1.1 If required charge data is missing, the system returns an error to the calling use case.

Design of the Componentized Application
Rule 1 : Separation of the entity class in a common library (EISRAQ)
Within the hotel management system, the data carrier entity classes that only serve to transport data are all packaged into a common system-wide library because these data carrier classes can then easily be reused by all of the other system components. These data carrier entity classes include the Guest, RoomType, Room, Reservation, Stay, Invoice, Payment, and IncidentalCharge classes. Each of these classes contains only code that serves to store, manage, and transport various pieces of information such as guest information, room-level information, reservation times, and payment data, but none of these classes contain business processing code. Because these data carrier entity classes only serve the purpose of carrying or containing state information, all of these classes are packaged into a common system-wide library because they do not serve as individual system components.

Rule 2 : Group classes by business functionality （HUANG DI）
In the component-based architecture, function-related implementation classes are grouped into the same business component. The goal is to place classes with closely related responsibilities and frequent interactions together, so that changes in one business area will have minimal impact on others (low coupling, high cohesion).

Entity classes are not placed inside business components. Instead, all shared domain entities are moved into the Base Library so that they can be reused consistently by all components. Meanwhile, each business component keeps only its internal implementation class (Manager) that encapsulates the business logic of that area.

Based on the dependency analysis and business responsibilities, the following business components are formed:
Guest Management Component
i. Internal implementation class: GuestManager
ii.Business responsibility: Manage guest information and guest-related operations (e.g., create/update guest records, retrieve guest details).
iii. Notes on placement: Domain entities (e.g., Guest) are stored in Base Library, not inside this component.

Reservation Management Component
i. Internal implementation class: ReservationManager
ii. Business responsibility: Handle reservation lifecycle operations (e.g., create/modify/cancel reservations, check reservation status).
iii. Notes on placement: Shared entities used by reservation logic (e.g., Reservation, RoomType) remain in Base Library.

Room Management Component
i. Internal implementation class: RoomManager
ii.Business responsibility: Manage room-related operations (e.g., room information, room availability/status, room type reference).
iii. Notes on placement: Room and RoomType are shared entities and are placed in Base Library.

Stay Management Component
i. Internal implementation class: StayManager
ii. Business responsibility: Manage stay/check-in/check-out flow and stay-related operations, including handling stay records and linking to charges/invoices where needed.
iii. Notes on placement: Entities such as Stay, Invoice, and IncidentalCharge are placed in Base Library for shared use.

Billing & Payment Component
i. Internal implementation class: BillingManager
ii. Business responsibility: Handle billing activities such as computing total charges, generating invoices, recording payments, and retrieving outstanding balances.
iii. Notes on placement: Invoice, Payment, and IncidentalCharge are shared entities and remain in Base Library.

Summary of Rule 2 outcome:
Business components contain only internal implementation logic (Manager classes).
Base Library centralizes all entity classes (domain model) and provides shared definitions needed across components.
This grouping reduces ripple effects: changes to one business component’s internal logic are less likely to break other components, because shared domain entities are centralized and business logic is isolated by functionality.

Rule 3 : Expose business functionality via interfaces （LI YU HANG)
To reduce coupling between components, business functionality is exposed exclusively through service interfaces rather than concrete implementation classes. This ensures that components depend on stable contracts (interfaces), allowing internal implementation changes without affecting other components.
In this project, each business component provides exactly one corresponding service interface, which defines the operations offered by that component. Other components and the application layer (SystemUI) access functionality only through these service interfaces, while the internal implementation classes (Manager classes) remain fully encapsulated inside their respective components.
The service interfaces used in the system are defined as follows:
Guest Management Component provides GuestService
i. Internal implementation class: GuestManager
ii. Description:
GuestService exposes guest-related operations such as creating, updating, and retrieving guest information. It provides a controlled access point for guest data without exposing internal guest management logic.

Reservation Management Component provides ReservationService
i. Internal implementation class: ReservationManager
ii. Description:
ReservationService handles reservation lifecycle operations, including creating, modifying, and canceling reservations, as well as querying reservation status. It encapsulates all reservation-related business rules.

Room Management Component provides RoomService
i. Internal implementation class: RoomManager
ii. Description:
RoomService provides access to room-related functionality such as retrieving room details, checking availability, and managing room status, while hiding internal room management logic.

Stay Management Component provides StayService
i. Internal implementation class: StayManager
ii. Description:
StayService supports stay-related operations, including check-in/check-out processes and stay record management. It may internally coordinate with reservation or billing logic through service interfaces, without direct component coupling.

Billing & Payment Component provides BillingService
i. Internal implementation class: BillingManager
ii. Description:
BillingService exposes billing-related functionality such as calculating total charges, generating invoices, recording payments, and retrieving outstanding balances, while isolating billing rules and payment logic.

With this design, all cross-component interactions occur strictly through service interfaces. For example, when the Stay Management Component requires reservation or billing functionality, it communicates via ReservationService or BillingService instead of directly accessing internal classes. This approach enforces loose coupling, improves maintainability, and aligns with component-based software engineering principles.

Rule 4 : Group mutually dependent classes together （MA WENTING）
This rule does not apply to this hotel management system. There are no interdependent classes across components in the current class diagram. This is attributed to the early design and planning. Closely related and highly coupled classes are pre-classified into the same component, thereby actively avoiding the generation of cross-component dependencies.
The system actually adopts a componentized structure. Each core function is encapsulated in an independent component. Guest management, reservation management, room management and bill payment are all such independent components. System interface components interact with them through clearly defined service interfaces. For example, it calls interfaces such as the guest manager and reservation manager, and never directly accesses the implementation classes inside the component.
This design achieves loosely coupled component interaction. The responsibilities of each part are clearly defined. It fundamentally eliminates the circular dependencies among components. The system thus maintains good modular characteristics. The maintainability is guaranteed, and it will be easier to expand functions in the future.

Rule 5 : Move Interface Definitions to the Base Library (ELVIS)
In the final stage of componentization, all service interface definitions which consist of GuestService, RoomService, ReservationService, StayService and BillingAndPaymentService are moved into the common base library alongside the entity classes Guest, Room, RoomType, Reservation, Stay, Invoice, Payment, and IncidentalCharge which have been identified in Rule 1. These interfaces define the contracts through which components interact while remaining independent of any specific component implementation. By doing so, all components depend only on the base library interfaces rather than directly depending on each other’s implementation classes.
This design reduces coupling between components. For example, the Stay Management component can interact with billing or reservation functionality through the BillingService and ReservationService interfaces without the need to access the actual implementation details. As a result, if we need to modify pricing structures, payment methods, or billing logic later, those updates are isolated within the Billing & Payment component and won't disrupt the rest of the system.

Dependency Depth Analysis (Componentized)

## Dependency Depth Table

| No  | Class              | Depends On (D/I)                                                                               | Dependency Depth |
| --- | ------------------ | ---------------------------------------------------------------------------------------------- | ---------------- |
| 1   | Guest              | -                                                                                              | 0                |
| 2   | RoomType           | -                                                                                              | 0                |
| 3   | IncidentalCharge   | -                                                                                              | 0                |
| 4   | Payment            | -                                                                                              | 0                |
| 5   | Room               | RoomType (D)                                                                                   | 0                |
| 6   | Reservation        | RoomType (D)                                                                                   | 0                |
| 7   | Invoice            | Payment (D)                                                                                    | 0                |
| 8   | Stay               | -                                                                                              | 0                |
| 9   | GuestService       | -                                                                                              | 0                |
| 10  | RoomService        | -                                                                                              | 0                |
| 11  | ReservationService | -                                                                                              | 0                |
| 12  | StayService        | -                                                                                              | 0                |
| 13  | BillingService     | -                                                                                              | 0                |
| 14  | GuestManager       | Guest (D), GuestService (D)                                                                    | 1                |
| 15  | RoomManager        | Room (D), RoomType (I), RoomService (D)                                                        | 1                |
| 16  | ReservationManager | Reservation (D), RoomType (I), ReservationService (D)                                          | 1                |
| 17  | StayManager        | Stay (D), StayService (D), IncidentalCharge (D), Invoice (D), Payment (I)                      | 1                |
| 18  | BillingManager     | Invoice (D), Payment (I), BillingService (D)                                                   | 1                |
| 19  | SystemUI           | GuestService (D), RoomService (D), ReservationService (D), StayService (D), BillingService (D) | 1                |

**Legend:**

- **D** = Direct Dependency
- **I** = Indirect Dependency
- **Dependency Depth 0** = No dependencies (base classes/interfaces)
- **Dependency Depth 1** = Depends only on base classes/interfaces
