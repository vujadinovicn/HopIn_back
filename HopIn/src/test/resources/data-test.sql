insert into "users"("address", "email", "is_activated", "is_blocked", "name", "password", "profile_picture", "surname", "telephone_number", "role") values ('Bulevar Oslobodjenja 5', 't.s.mihajlovic25@gmail.com', true, false, 'Pera', '$2a$12$t9Nsxg2fYHtDyJdnuZaRNeBPbg6EGxkUy7wpwZxbK5v2BKIIIWgK2', null, 'Peric', '+3819720132 ', 0);
insert into "passengers"("id") values (1);

insert into "locations"("address", "latitude", "longitude") values ('Novi Sad', 45.26, 19.83);
insert into "locations"("address", "latitude", "longitude") values ('Beograd', 44.78, 20.45);
insert into "locations"("address", "latitude", "longitude") values ('Kragujevac',  44.02, 20.92);
insert into "locations"("address", "latitude", "longitude") values ('Jirecekova', 45.240397, 19.847994);

insert into "vehicle_types" ("name", "price_per_km") values (0, 60);  
insert into "vehicle_types" ("name", "price_per_km") values (1, 80);
insert into "vehicle_types" ("name", "price_per_km") values (2, 100); 

insert into "users"("address", "email", "is_activated", "is_blocked", "name", "password", "profile_picture", "surname", "telephone_number", "role") values ('Hajduk Veljka', 'driver@gmail.com', true, false, 'Mark', '$2a$12$DdZJHu0xNmWhNo6qeZrB..dFEGPNqdCFmStxusNrElLQso5ZLVUkW', null , 'Jacobs', '+381603454212', 1);
insert into "vehicles"("baby_transport", "driver_id", "license_number", "model", "passenger_seats", "pet_transport", "current_location_id", "vehicle_type_id") values (true, 2, 'BP-030-HR', 'AUDI A6', 4, true, 4, 1);

insert into "drivers"("id", "vehicle_id", "is_active") values (2, 1, false);    

insert into "documents"("name", "document_image", "driver_id") values ('Drivers license 1', 'sss', 2);
insert into "documents"("name", "document_image", "driver_id") values ('Registration license 1', 'sss', 2);
   
insert into "drivers_documents" values (2,1);
insert into "drivers_documents" values (2,2);     
  
insert into "rides"("baby_transport", "distance", "end_time", "estimated_time_in_minutes", "panic", "pet_transport", "start_time", "status", "total_cost", "total_distance", "driver_id", "rejection_notice_id", "vehicle_type_id", "departure_location_id", "destination_location_id", "scheduled_time") values (false, 95, null, 5, false, false, null, 1, 250, 3, 2, null, 1, 1, 2, '2023-02-05T13:11:11');
insert into "rides"("baby_transport", "distance", "end_time", "estimated_time_in_minutes", "panic", "pet_transport", "start_time", "status", "total_cost", "total_distance", "driver_id", "rejection_notice_id", "vehicle_type_id", "departure_location_id", "destination_location_id")  values (false, 100, '2023-01-23T13:50:11', 5, false, false, '2023-01-23T13:11:11', 4, 780, 43, 2, null, 1, 2, 1);

insert into "rides_passengers"("ride_id", "passengers_id") values (1, 1);
insert into "rides_passengers"("ride_id", "passengers_id") values (2, 1);

insert into "users"("address", "email", "is_activated", "is_blocked", "name", "password", "profile_picture", "surname", "telephone_number", "role") values ('Hajduk Milosa', 'vozac@gmail.com', true, false, 'Vozac', '$2a$12$DdZJHu0xNmWhNo6qeZrB..dFEGPNqdCFmStxusNrElLQso5ZLVUkW', null , 'Vozacevic', '+381603454212', 1);
insert into "vehicles"("baby_transport", "driver_id", "license_number", "model", "passenger_seats", "pet_transport", "current_location_id", "vehicle_type_id") values (true, 2, 'BP-061-GC', 'FIAT 500', 4, true, 2, 1);

insert into "drivers"("id", "vehicle_id", "is_active") values (3, 2, false);  

insert into "rides"("baby_transport", "distance", "end_time", "estimated_time_in_minutes", "panic", "pet_transport", "start_time", "status", "total_cost", "total_distance", "driver_id", "rejection_notice_id", "vehicle_type_id", "departure_location_id", "destination_location_id")  values (false, 100, '2023-01-23T13:50:11', 5, false, false, null, 6, 780, 43, 3, null, 1, 2, 1);
insert into "rides"("baby_transport", "distance", "end_time", "estimated_time_in_minutes", "panic", "pet_transport", "start_time", "status", "total_cost", "total_distance", "driver_id", "rejection_notice_id", "vehicle_type_id", "departure_location_id", "destination_location_id")  values (false, 100, '2023-01-23T13:50:11', 5, false, false, null, 0, 780, 43, 3, null, 1, 2, 1);



--insert into "rides"("baby_transport", "distance", "end_time", "estimated_time_in_minutes", "panic", "pet_transport", "start_time", "status", "total_cost", "total_distance", "driver_id", "rejection_notice_id", "vehicle_type_id", "departure_location_id", "destination_location_id")  values (false, 100, '2023-01-23T13:50:11', 5, false, false, null, 1, 780, 43, 3, null, 1, 2, 1);
--insert into "rides"("baby_transport", "distance", "end_time", "estimated_time_in_minutes", "panic", "pet_transport", "start_time", "status", "total_cost", "total_distance", "driver_id", "rejection_notice_id", "vehicle_type_id", "departure_location_id", "destination_location_id")  values (false, 100, '2023-01-23T13:50:11', 5, false, false, null, 1, 780, 43, 3, '2023-01-23T13:50:11', 1, 2, 1);

insert into "users"("address", "email", "is_activated", "is_blocked", "name", "password", "profile_picture", "surname", "telephone_number", "role") values ('Bulevar Oslobodjenja 5', 'mika@gmail.com', true, false, 'Pera', '$2a$12$t9Nsxg2fYHtDyJdnuZaRNeBPbg6EGxkUy7wpwZxbK5v2BKIIIWgK2', null, 'Peric', '+3819720132 ', 0);
insert into "passengers"("id") values (3);

insert into "rides_passengers"("ride_id", "passengers_id") values (3, 3);
insert into "rides_passengers"("ride_id", "passengers_id") values (4, 1);