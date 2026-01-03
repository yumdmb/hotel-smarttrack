-- ========================================
-- Hotel SmartTrack - Seed Data
-- ========================================
-- This file is automatically executed on startup
-- to populate the database with sample data.
-- Column names follow Hibernate's default naming strategy (camelCase -> snake_case)

-- ========================================
-- Room Types
-- ========================================
MERGE INTO room_types (room_type_id, type_name, description, max_occupancy, base_price, tax_rate) KEY(room_type_id) VALUES
(1, 'Standard', 'Basic room with essential amenities', 2, 100.00, 0.10);
MERGE INTO room_types (room_type_id, type_name, description, max_occupancy, base_price, tax_rate) KEY(room_type_id) VALUES
(2, 'Deluxe', 'Premium room with city view and minibar', 3, 200.00, 0.10);
MERGE INTO room_types (room_type_id, type_name, description, max_occupancy, base_price, tax_rate) KEY(room_type_id) VALUES
(3, 'Suite', 'Luxury suite with separate living area', 4, 350.00, 0.10);
MERGE INTO room_types (room_type_id, type_name, description, max_occupancy, base_price, tax_rate) KEY(room_type_id) VALUES
(4, 'Family', 'Spacious room ideal for families', 5, 280.00, 0.10);

-- ========================================
-- Rooms
-- ========================================
MERGE INTO rooms (room_id, room_number, floor_number, status, room_type_id) KEY(room_id) VALUES
(1, '101', 1, 'Available', 1);
MERGE INTO rooms (room_id, room_number, floor_number, status, room_type_id) KEY(room_id) VALUES
(2, '102', 1, 'Available', 1);
MERGE INTO rooms (room_id, room_number, floor_number, status, room_type_id) KEY(room_id) VALUES
(3, '103', 1, 'Available', 1);
MERGE INTO rooms (room_id, room_number, floor_number, status, room_type_id) KEY(room_id) VALUES
(4, '201', 2, 'Available', 2);
MERGE INTO rooms (room_id, room_number, floor_number, status, room_type_id) KEY(room_id) VALUES
(5, '202', 2, 'Available', 2);
MERGE INTO rooms (room_id, room_number, floor_number, status, room_type_id) KEY(room_id) VALUES
(6, '301', 3, 'Available', 3);
MERGE INTO rooms (room_id, room_number, floor_number, status, room_type_id) KEY(room_id) VALUES
(7, '302', 3, 'Available', 3);
MERGE INTO rooms (room_id, room_number, floor_number, status, room_type_id) KEY(room_id) VALUES
(8, '401', 4, 'Available', 4);

-- ========================================
-- Sample Guests
-- ========================================
MERGE INTO guests (guest_id, name, email, phone, identification_number, status) KEY(guest_id) VALUES
(1, 'John Doe', 'john.doe@email.com', '0123456789', 'A12345678', 'Active');
MERGE INTO guests (guest_id, name, email, phone, identification_number, status) KEY(guest_id) VALUES
(2, 'Jane Smith', 'jane.smith@email.com', '0987654321', 'B87654321', 'Active');
MERGE INTO guests (guest_id, name, email, phone, identification_number, status) KEY(guest_id) VALUES
(3, 'Michael Johnson', 'michael.j@email.com', '0112233445', 'C11223344', 'Active');
