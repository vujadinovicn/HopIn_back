insert into "locations"("address", "latitude", "longitude") values ('Novi Sad', 45.26, 19.83);
insert into "locations"("address", "latitude", "longitude") values ('Beograd', 44.78, 20.45);
insert into "locations"("address", "latitude", "longitude") values ('Kragujevac',  44.02, 20.92);
insert into "locations"("address", "latitude", "longitude") values ('Jirecekova', 45.240397, 19.847994);

insert into "vehicle_types" ("name", "price_per_km") values (0, 60);  
insert into "vehicle_types" ("name", "price_per_km") values (1, 80);
insert into "vehicle_types" ("name", "price_per_km") values (2, 100); 

insert into "users"("address", "email", "is_activated", "is_blocked", "name", "password", "profile_picture", "surname", "telephone_number", "role") values ('Bulevar Oslobodjenja 5', 'mika@gmail.com', true, false, 'Pera', '$2a$12$DdZJHu0xNmWhNo6qeZrB..dFEGPNqdCFmStxusNrElLQso5ZLVUkW', null, 'Peric', '+3819720132 ', 0);
insert into "passengers"("id") values (1);

insert into "users"("address", "email", "is_activated", "is_blocked", "name", "password", "profile_picture", "surname", "telephone_number", "role") values ('Hajduk Veljka', 'driver@gmail.com', true, false, 'Mark', '$2a$12$DdZJHu0xNmWhNo6qeZrB..dFEGPNqdCFmStxusNrElLQso5ZLVUkW', null , 'Jacobs', '+381603454212', 1);
insert into "vehicles"("baby_transport", "driver_id", "license_number", "model", "passenger_seats", "pet_transport", "current_location_id", "vehicle_type_id") values (true, 2, 'BP-030-HR', 'AUDI A6', 4, true, 4, 1);

insert into "drivers"("id", "vehicle_id", "is_active") values (2, 1, false);    

insert into "documents"("name", "document_image", "driver_id") values ('Drivers license 1', 'sss', 2);
insert into "documents"("name", "document_image", "driver_id") values ('Registration license 1', 'sss', 2);
   
insert into "drivers_documents" values (2,1);
insert into "drivers_documents" values (2,2);   

insert into "users"("address", "email", "is_activated", "is_blocked", "name", "password", "profile_picture", "surname", "telephone_number", "role") values ('Bulevar Oslobodjenja 5', 'laza@gmail.com', true, false, 'Pera', '$2a$12$t9Nsxg2fYHtDyJdnuZaRNeBPbg6EGxkUy7wpwZxbK5v2BKIIIWgK2', null, 'Peric', '+3819720132 ', 0);
insert into "passengers"("id") values (3);

insert into "users"("address", "email", "is_activated", "is_blocked", "name", "password", "profile_picture", "surname", "telephone_number", "role") values ('Bulevar Oslobodjenja 5', 'admin@gmail.com', true, false, 'Mika', '$2a$12$DdZJHu0xNmWhNo6qeZrB..dFEGPNqdCFmStxusNrElLQso5ZLVUkW', null, 'Mikic', '+3819720132 ', 2);
insert into "admins" ("id") values (4);

insert into "rides"("baby_transport", "distance", "end_time", "estimated_time_in_minutes", "panic", "pet_transport", "start_time", "status", "total_cost", "total_distance", "driver_id", "rejection_notice_id", "vehicle_type_id", "departure_location_id", "destination_location_id") values (false, 95, null, 5, false, false, null, 1, 250, 3, 2, null, 1, 1, 2);
insert into "rides_passengers"("ride_id", "passengers_id") values (1, 1);

insert into "rides"("baby_transport", "distance", "end_time", "estimated_time_in_minutes", "panic", "pet_transport", "start_time", "status", "total_cost", "total_distance", "driver_id", "rejection_notice_id", "vehicle_type_id", "departure_location_id", "destination_location_id") values (false, 95, null, 5, false, false, null, 0, 250, 3, 2, null, 1, 1, 2);
insert into "rides_passengers"("ride_id", "passengers_id") values (2, 1);

insert into "users"("address", "email", "is_activated", "is_blocked", "name", "password", "profile_picture", "surname", "telephone_number", "role") values ('Hajduk Veljka', 'vozac@gmail.com', true, false, 'Mark', '$2a$12$DdZJHu0xNmWhNo6qeZrB..dFEGPNqdCFmStxusNrElLQso5ZLVUkW', null , 'Jacobs', '+381603454212', 1);
insert into "vehicles"("baby_transport", "driver_id", "license_number", "model", "passenger_seats", "pet_transport", "current_location_id", "vehicle_type_id") values (true, 5, 'BP-030-HR', 'AUDI A6', 4, true, 4, 1);

insert into "drivers"("id", "vehicle_id", "is_active") values (5, 2, false);    

insert into "documents"("name", "document_image", "driver_id") values ('Drivers license 1', 'sss', 5);
insert into "documents"("name", "document_image", "driver_id") values ('Registration license 1', 'sss', 5);
   
insert into "drivers_documents" values (5,3);
insert into "drivers_documents" values (5,4); 

insert into "rides"("baby_transport", "distance", "end_time", "estimated_time_in_minutes", "panic", "pet_transport", "start_time", "status", "total_cost", "total_distance", "driver_id", "rejection_notice_id", "vehicle_type_id", "departure_location_id", "destination_location_id") values (false, 95, null, 5, false, false, null, 6, 250, 3, 2, null, 1, 1, 2);
insert into "rides_passengers"("ride_id", "passengers_id") values (3, 1);

insert into "rides"("baby_transport", "distance", "end_time", "estimated_time_in_minutes", "panic", "pet_transport", "start_time", "status", "total_cost", "total_distance", "driver_id", "rejection_notice_id", "vehicle_type_id", "departure_location_id", "destination_location_id", "scheduled_time") values (false, 95, null, 5, false, false, null, 1, 250, 3, 2, null, 1, 1, 2, '2022-12-26T11:11:11');
insert into "rides_passengers"("ride_id", "passengers_id") values (4, 1);


--insert into "users"("address", "email", "is_activated", "is_blocked", "name", "password", "profile_picture", "surname", "telephone_number", "role") values ('Bulevar Oslobodjenja 5', 'tea@gmail.com', true, false, 'Pera', '$2a$12$t9Nsxg2fYHtDyJdnuZaRNeBPbg6EGxkUy7wpwZxbK5v2BKIIIWgK2', null, 'Peric', '+3819720132 ', 0);
--insert into "passengers"("id") values (5);
--
--insert into "rides_passengers"("ride_id", "passengers_id") values (3, 5);


insert into "users"("address", "email", "is_activated", "is_blocked", "name", "password", "profile_picture", "surname", "telephone_number", "role") values ('Bulevar Oslobodjenja 5', 'tea@gmail.com', true, false, 'Pera', '$2a$12$t9Nsxg2fYHtDyJdnuZaRNeBPbg6EGxkUy7wpwZxbK5v2BKIIIWgK2', null, 'Peric', '+3819720132 ', 0);
insert into "passengers"("id") values (6);

insert into "rides_passengers"("ride_id", "passengers_id") values (3, 6);
insert into "favorite_rides"("vehicle_type_id", "pet_transport", "baby_transport") values (1, true, true);
insert into "favorite_rides_passengers"("favorite_ride_id", "passengers_id") values (1, 1);
insert into "routes"("departure_id", "destination_id", "distance") values (2, 1, 120);
insert into "favorite_rides_routes"("favorite_ride_id", "routes_id") values (1, 1);

insert into "users"("address", "email", "is_activated", "is_blocked", "name", "password", "profile_picture", "surname", "telephone_number", "role") values ('Hajduk Veljka', 'blejac@gmail.com', true, false, 'Mark', '$2a$12$DdZJHu0xNmWhNo6qeZrB..dFEGPNqdCFmStxusNrElLQso5ZLVUkW', null , 'Jacobs', '+381603454212', 1);
insert into "vehicles"("baby_transport", "driver_id", "license_number", "model", "passenger_seats", "pet_transport", "current_location_id", "vehicle_type_id") values (true, 7, 'BP-030-HR', 'AUDI A6', 4, true, 4, 1);

insert into "drivers"("id", "vehicle_id", "is_active") values (7, 3, false); 

insert into "documents"("name", "document_image", "driver_id") values ('Drivers license 1', 'sss', 7);
insert into "documents"("name", "document_image", "driver_id") values ('Registration license 1', 'sss', 7);
   
insert into "drivers_documents" values (7,5);
insert into "drivers_documents" values (7,6); 

insert into "rides"("baby_transport", "distance", "end_time", "estimated_time_in_minutes", "panic", "pet_transport", "start_time", "status", "total_cost", "total_distance", "driver_id", "rejection_notice_id", "vehicle_type_id", "departure_location_id", "destination_location_id", "scheduled_time") values (false, 95, null, 5, false, false, null, 6, 250, 3, 7, null, 1, 1, 2, '2022-12-26T11:11:11');
insert into "rides_passengers"("ride_id", "passengers_id") values (5, 1);

insert into "favorite_rides"("vehicle_type_id", "pet_transport", "baby_transport") values (1, true, true);
insert into "favorite_rides_passengers"("favorite_ride_id", "passengers_id") values (2, 1);
insert into "favorite_rides"("vehicle_type_id", "pet_transport", "baby_transport") values (1, true, true);
insert into "favorite_rides_passengers"("favorite_ride_id", "passengers_id") values (3, 1);
insert into "favorite_rides"("vehicle_type_id", "pet_transport", "baby_transport") values (1, true, true);
insert into "favorite_rides_passengers"("favorite_ride_id", "passengers_id") values (4, 1);
insert into "favorite_rides"("vehicle_type_id", "pet_transport", "baby_transport") values (1, true, true);
insert into "favorite_rides_passengers"("favorite_ride_id", "passengers_id") values (5, 1);
insert into "favorite_rides"("vehicle_type_id", "pet_transport", "baby_transport") values (1, true, true);
insert into "favorite_rides_passengers"("favorite_ride_id", "passengers_id") values (6, 1);
insert into "favorite_rides"("vehicle_type_id", "pet_transport", "baby_transport") values (1, true, true);
insert into "favorite_rides_passengers"("favorite_ride_id", "passengers_id") values (7, 1);
insert into "favorite_rides"("vehicle_type_id", "pet_transport", "baby_transport") values (1, true, true);
insert into "favorite_rides_passengers"("favorite_ride_id", "passengers_id") values (8, 1);
insert into "favorite_rides"("vehicle_type_id", "pet_transport", "baby_transport") values (1, true, true);
insert into "favorite_rides_passengers"("favorite_ride_id", "passengers_id") values (9, 1);
insert into "favorite_rides"("vehicle_type_id", "pet_transport", "baby_transport") values (1, true, true);
insert into "favorite_rides_passengers"("favorite_ride_id", "passengers_id") values (10, 1);

insert into "rides"("baby_transport", "distance", "end_time", "estimated_time_in_minutes", "panic", "pet_transport", "start_time", "status", "total_cost", "total_distance", "driver_id", "rejection_notice_id", "vehicle_type_id", "departure_location_id", "destination_location_id", "scheduled_time") values (false, 95, null, 5, false, false, '2022-12-27T11:11:11', 4, 250, 3, 7, null, 1, 1, 2, null);
insert into "rides_passengers"("ride_id", "passengers_id") values (6, 1);