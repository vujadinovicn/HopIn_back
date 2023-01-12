insert into "users"("address", "email", "is_activated", "is_blocked", "name", "password", "profile_picture", "surname", "telephone_number", "role") values ('Bulevar Oslobodjenja 5', 'pera@gmail.com', true, false, 'Pera', '$2a$12$DdZJHu0xNmWhNo6qeZrB..dFEGPNqdCFmStxusNrElLQso5ZLVUkW', null, 'Peric', '+3819720132 ', 0);
insert into "passengers"("id") values (1);

insert into "locations"("id", "address", "latitude", "longitude") values (1, 'Novi Sad', 45.257256668230646, 19.81587422680622);
insert into "locations"("id", "address", "latitude", "longitude") values (2, 'Beograd', 45.24145917302709, 19.786520390372694);

insert into "vehicle_types" ("name", "price_per_km") values (0, 60);
insert into "vehicle_types" ("name", "price_per_km") values (1, 80);
insert into "vehicle_types" ("name", "price_per_km") values (2, 100);

insert into "users"("address", "email", "is_activated", "is_blocked", "name", "password", "profile_picture", "surname", "telephone_number", "role") values ('Hajduk Veljka', 'driver@gmail.com', false, false, 'Mark', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', null , 'Jacobs', '+381603454212', 1);
insert into "vehicles"("baby_transport", "driver_id", "license_number", "model", "passenger_seats", "pet_transport", "current_location_id", "vehicle_type_id") values (true, 0, 'BP-030-HR', 'AUDI A6', 4, true, 2, 1);

insert into "drivers"("id", "vehicle_id", "is_active") values (2, 1, false);

insert into "documents"("name", "document_image", "driver_id") values ('Drivers license 1', 'sss', 2);
insert into "documents"("name", "document_image", "driver_id") values ('Registration license 1', 'sss', 2);

insert into "drivers_documents" values (2,1);
insert into "drivers_documents" values (2,2);   

<<<<<<< HEAD
insert into "rides"("id", "baby_transport", "distance", "end_time", "estimated_time_in_minutes", "panic", "pet_transport", "start_time", "status", "total_cost", "total_distance", "driver_id", "rejection_notice_id", "vehicle_type_id", "departure_location_id", "destination_location_id") values (3, false, 5, null, 5, false, false, '2022-12-23T11:11:11', null, 5, 5, 2, null, null, 1, 2);
insert into "rides"("id", "baby_transport", "distance", "end_time", "estimated_time_in_minutes", "panic", "pet_transport", "start_time", "status", "total_cost", "total_distance", "driver_id", "rejection_notice_id", "vehicle_type_id") values (2, false, 5, null, 5, false, false, '2022-12-21T11:11:11', null, 5, 5, 2, null, null);
insert into "rides"("id", "baby_transport", "distance", "end_time", "estimated_time_in_minutes", "panic", "pet_transport", "start_time", "status", "total_cost", "total_distance", "driver_id", "rejection_notice_id", "vehicle_type_id") values (1, false, 5, null, 5, false, false, '2022-12-21T11:11:11', null, 5, 5, 2, null, null);

insert into "rides_passengers"("ride_id", "passengers_id") values (1, 1);
insert into "rides_passengers"("ride_id", "passengers_id") values (2, 1);
insert into "rides_passengers"("ride_id", "passengers_id") values (3, 1);


insert into "routes"("id", "distance", "departure_id", "destination_id") values (1, 95, 1, 2);
insert into "passengers_favourite_routes"("passenger_id", "favourite_routes_id") values (1,1);

insert into "users"("address", "email", "is_activated", "is_blocked", "name", "password", "profile_picture", "surname", "telephone_number", "role") values ('Bulevar Oslobodjenja 5', 'pera.peric@gmail.com', false, false, 'Mika', 'Password123', null, 'Mikic', '+3819720132 ', 2);
=======
insert into "users"("address", "email", "is_activated", "is_blocked", "name", "password", "profile_picture", "surname", "telephone_number", "role") values ('Bulevar Oslobodjenja 5', 'pera.peric@gmail.com', false, false, 'Mika', 'Password123', 'ooooo', 'Mikic', '+3819720132 ', 2);
>>>>>>> feature/9.5-implement-algorithm-find-ride-driver

insert into "users"("address", "email", "is_activated", "is_blocked", "name", "password", "profile_picture", "surname", "telephone_number", "role") values ('Bulevar Oslobodjenja 5', 'peraa@gmail.com', false, false, 'Mika', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', null, 'Mikic', '+3819720132 ', 2);
insert into "admins" ("id") values (3);

<<<<<<< HEAD
insert into "users"("address", "email", "is_activated", "is_blocked", "name", "password", "profile_picture", "surname", "telephone_number", "role") values ('Bulevar Oslobodjenja 5', 'mika@gmail.com', true, false, 'Mika', '$2a$12$DdZJHu0xNmWhNo6qeZrB..dFEGPNqdCFmStxusNrElLQso5ZLVUkW', null, 'Peric', '+3819720132 ', 0);
insert into "passengers"("id") values (5);

insert into "users"("address", "email", "is_activated", "is_blocked", "name", "password", "profile_picture", "surname", "telephone_number", "role") values ('Bulevar Oslobodjenja 5', 'laza@gmail.com', true, false, 'Laza', '$2a$12$DdZJHu0xNmWhNo6qeZrB..dFEGPNqdCFmStxusNrElLQso5ZLVUkW', null, 'Kostic', '+3819720132 ', 0);
insert into "passengers"("id") values (6);
=======

insert into "users"("address", "email", "is_activated", "is_blocked", "name", "password", "profile_picture", "surname", "telephone_number", "role") values ('Bulevar Oslobodjenja riki', 'rikilino@gmail.com', false, false, 'Rika', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'ooooo', 'Rikic', '+3819720132 ', 0);
insert into "passengers"("id") values (5);

insert into "users"("address", "email", "is_activated", "is_blocked", "name", "password", "profile_picture", "surname", "telephone_number", "role") values ('Hajduk Veljka', 'mia@gmail.com', false, false, 'MIA', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', null , 'Jacobs', '+381603454212', 1);
insert into "vehicles"("baby_transport", "driver_id", "license_number", "model", "passenger_seats", "pet_transport", "current_location_id", "vehicle_type_id") values (true, 0, 'BP-030-SS', 'AUDI A8', 4, true, 1, 1);

insert into "drivers"("id", "vehicle_id", "is_active") values (6, 2, false);

insert into "documents"("name", "document_image", "driver_id") values ('Drivers license 1', 'sss', 6);
insert into "documents"("name", "document_image", "driver_id") values ('Registration license 1', 'sss', 6);

insert into "drivers_documents" values (6,3);
insert into "drivers_documents" values (6,4); 

insert into "rides"("id", "baby_transport", "distance", "estimated_time_in_minutes", "panic", "pet_transport", "start_time", "end_time", "status", "total_cost", "total_distance", "driver_id", "rejection_notice_id", "vehicle_type_id", "departure_location_id", "destination_location_id") values (1, false, 5, 5, false, false, '2023-01-10T23:25:11', '2023-01-10T23:59:11', 4, 5, 5, 2, null, null,1,2);
insert into "rides"("id", "baby_transport", "distance", "estimated_time_in_minutes", "panic", "pet_transport", "start_time", "end_time", "status", "total_cost", "total_distance", "driver_id", "rejection_notice_id", "vehicle_type_id", "departure_location_id", "destination_location_id") values (2, false, 5, 5, false, false, '2022-12-21T11:11:11', '2023-01-09T05:10:11', 4, 5, 5, 2, null, null,1,2);
insert into "rides"("id", "baby_transport", "distance", "estimated_time_in_minutes", "panic", "pet_transport", "start_time", "end_time", "status", "total_cost", "total_distance", "driver_id", "rejection_notice_id", "vehicle_type_id", "departure_location_id", "destination_location_id") values (3, false, 5, 5, false, false, '2022-12-21T11:11:11', '2023-01-09T05:10:11', 4, 5, 5, 2, null, null,1,2);
insert into "rides"("id", "baby_transport", "distance", "estimated_time_in_minutes", "panic", "pet_transport", "start_time", "end_time", "status", "total_cost", "total_distance", "driver_id", "rejection_notice_id", "vehicle_type_id", "departure_location_id", "destination_location_id") values (4, false, 5, 38, false, false, '2023-01-11T03:40:11', '2023-01-11T04:18:11', 3, 5, 5, 2, null, null,1,2);
insert into "rides"("id", "baby_transport", "distance", "estimated_time_in_minutes", "panic", "pet_transport", "start_time", "end_time", "status", "total_cost", "total_distance", "driver_id", "rejection_notice_id", "vehicle_type_id", "departure_location_id", "destination_location_id") values (6, false, 5, 10, false, false, '2023-01-11T04:40:11', '2023-01-11T04:50:11', 1, 5, 5, 2, null, null,1,2);
insert into "rides"("id", "baby_transport", "distance", "estimated_time_in_minutes", "panic", "pet_transport", "start_time", "end_time", "status", "total_cost", "total_distance", "driver_id", "rejection_notice_id", "vehicle_type_id", "departure_location_id", "destination_location_id") values (5, false, 5, 49, false, false, '2023-01-11T03:40:11', '2023-01-11T04:29:11', 3, 5, 5, 6, null, null,2,1);
insert into "rides"("id", "baby_transport", "distance", "estimated_time_in_minutes", "panic", "pet_transport", "start_time", "end_time", "status", "total_cost", "total_distance", "driver_id", "rejection_notice_id", "vehicle_type_id", "departure_location_id", "destination_location_id") values (7, false, 5, 10, false, false, '2023-01-11T04:50:11', '2023-01-11T05:00:11', 1, 5, 5, 6, null, null,2,1);

insert into "routes"("id", "distance", "departure_id", "destination_id") values (1, 95, 1, 2);
insert into "passengers_favourite_routes"("passenger_id", "favourite_routes_id") values (1,1);


insert into "rides_passengers"("ride_id", "passengers_id") values (1, 5);
insert into "rides_passengers"("ride_id", "passengers_id") values (2, 1);
insert into "rides_passengers"("ride_id", "passengers_id") values (3, 1);

>>>>>>> feature/9.5-implement-algorithm-find-ride-driver

insert into "driver_account_update_requests"("reason", "status", "admin_id", "driver_id", "type", "time") values (null, 1, null, 2, 0, '2022-12-26T11:11:11');
insert into "driver_account_update_password_requests"("new_password", "old_password", "id") values('lofi1123', 'larako2', 1);
insert into "driver_account_update_requests"("reason", "status", "admin_id", "driver_id", "type", "time") values ('Too many password changes this month.', 2, 3, 2, 0, '2022-12-26T11:11:11');
insert into "driver_account_update_password_requests"("new_password", "old_password", "id") values('novisad1', 'beograd1', 2);
insert into "driver_account_update_requests"("reason", "status", "admin_id", "driver_id", "type", "time") values (null, 0, 3, 2, 0, '2022-12-26T11:11:11');
insert into "driver_account_update_password_requests"("new_password", "old_password", "id") values('lepsika', 'kekios', 3);

insert into "driver_account_update_requests"("reason", "status", "admin_id", "driver_id", "type", "time") values (null, 1, null, 2, 1, '2022-12-26T11:11:11');
insert into "driver_account_update_document_requests"("document_id", "document_image", "document_operation_type", "name", "id") values(2, 'djskd', 1, 'Drivers license SRB', 4);

insert into "driver_account_update_requests"("reason", "status", "type", "admin_id", "driver_id") values ('blablatruc', 1, 2, 3, 2);
insert into "driver_account_update_info_requests"("address", "email", "name", "profile_picture", "surname", "telephone_number", "id") values('Beograd', 'mark@gmail.com', 'Neca', 'a', 'Vijic', '+3816492031', 5);