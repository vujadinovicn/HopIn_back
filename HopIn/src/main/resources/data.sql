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

insert into "rides"("baby_transport", "distance", "end_time", "estimated_time_in_minutes", "panic", "pet_transport", "start_time", "status", "total_cost", "total_distance", "driver_id", "rejection_notice_id", "vehicle_type_id", "departure_location_id", "destination_location_id") values (false, 95, '2023-01-23T12:21:11', 5, false, false, '2023-01-23T12:10:11', 4, 250, 3, 2, null, 1, 1, 2);
insert into "rides"("baby_transport", "distance", "end_time", "estimated_time_in_minutes", "panic", "pet_transport", "start_time", "status", "total_cost", "total_distance", "driver_id", "rejection_notice_id", "vehicle_type_id", "departure_location_id", "destination_location_id")  values (false, 100, '2023-01-23T13:50:11', 5, false, false, '2023-01-23T13:11:11', 4, 780, 43, 2, null, 1, 2, 1);
insert into "rides"("baby_transport", "distance", "end_time", "estimated_time_in_minutes", "panic", "pet_transport", "start_time", "status", "total_cost", "total_distance", "driver_id", "rejection_notice_id", "vehicle_type_id", "departure_location_id", "destination_location_id")  values (false, 56, '2023-01-21T9:15:11', 5, false, false, '2023-01-21T08:42:11', 4, 360, 24, 2, null, 1, 1, 3);
insert into "rides"("baby_transport", "distance", "end_time", "estimated_time_in_minutes", "panic", "pet_transport", "start_time", "status", "total_cost", "total_distance", "driver_id", "rejection_notice_id", "vehicle_type_id", "departure_location_id", "destination_location_id")  values (false, 56, '2023-01-25T18:30:11', 5, false, false, '2023-01-25T18:05:11', 4, 290, 4, 2, null, 1, 3, 2);

insert into "routes"("departure_id", "destination_id", "distance") values (2, 1, 120);

insert into "reviews" ("comment", "rating", "type", "passenger_id", "ride_id") values ('Good looking driver ;)', 3, 1, 1, 1);
insert into "reviews" ("comment", "rating", "type", "passenger_id", "ride_id") values ('Clean and new.', 4, 0, 1, 1);


insert into "rides_passengers"("ride_id", "passengers_id") values (1, 1);
insert into "rides_passengers"("ride_id", "passengers_id") values (2, 1);
insert into "rides_passengers"("ride_id", "passengers_id") values (3, 1);
insert into "rides_passengers"("ride_id", "passengers_id") values (4, 1);

insert into "rides_reviews"("ride_id", "reviews_id") values (1, 1);
insert into "rides_reviews"("ride_id", "reviews_id") values (1, 2);


insert into "routes"("distance", "departure_id", "destination_id") values (95, 1, 2);
insert into "passengers_favourite_routes"("passenger_id", "favourite_routes_id") values (1,1);

insert into "users"("address", "email", "is_activated", "is_blocked", "name", "password", "profile_picture", "surname", "telephone_number", "role") values ('Bulevar Oslobodjenja 5', 'admin@gmail.com', true, false, 'Mika', '$2a$12$DdZJHu0xNmWhNo6qeZrB..dFEGPNqdCFmStxusNrElLQso5ZLVUkW', null, 'Mikic', '+3819720132 ', 2);
insert into "admins" ("id") values (3);

insert into "users"("address", "email", "is_activated", "is_blocked", "name", "password", "profile_picture", "surname", "telephone_number", "role") values ('Bulevar Oslobodjenja 5', 'peraa@gmail.com', false, false, 'Mika', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', null, 'Mikic', '+3819720132 ', 2);
insert into "admins" ("id") values (4);

insert into "users"("address", "email", "is_activated", "is_blocked", "name", "password", "profile_picture", "surname", "telephone_number", "role") values ('Bulevar Oslobodjenja 5', 'mika@gmail.com', true, false, 'Mika', '$2a$12$DdZJHu0xNmWhNo6qeZrB..dFEGPNqdCFmStxusNrElLQso5ZLVUkW', null, 'Peric', '+3819720132 ', 0);
insert into "passengers"("id") values (5);

insert into "users"("address", "email", "is_activated", "is_blocked", "name", "password", "profile_picture", "surname", "telephone_number", "role") values ('Bulevar Oslobodjenja 5', 'laza@gmail.com', true, false, 'Laza', '$2a$12$DdZJHu0xNmWhNo6qeZrB..dFEGPNqdCFmStxusNrElLQso5ZLVUkW', null, 'Kostic', '+3819720132 ', 0);
insert into "passengers"("id") values (6);

insert into "users"("address", "email", "is_activated", "is_blocked", "name", "password", "profile_picture", "surname", "telephone_number", "role") values ('Bulevar Oslobodjenja 5', 'kika@gmail.com', false, false, 'Mika', '$2a$12$DdZJHu0xNmWhNo6qeZrB..dFEGPNqdCFmStxusNrElLQso5ZLVUkW', null, 'Peric', '+3819720132 ', 0);
insert into "passengers"("id") values (7);

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


insert into "secure_token"("expiration_date", "type", "user_id", "token", "used") values('2023-12-23T11:11:11', 0, 7, 'hdeuwir49834574hnfdj43829449305', false);
insert into "secure_token"("expiration_date", "type", "user_id", "token", "used") values('2022-12-22T11:11:11', 0, 5, 'hdeuwir49834574hnfdj43829449304', false);

insert into "secure_token"("expiration_date", "type", "user_id", "token", "used") values('2023-12-23T11:11:11', 1, 1, 'hdeuwir49834574hnfdj43829449302', false);
insert into "secure_token"("expiration_date", "type", "user_id", "token", "used") values('2022-12-22T11:11:11', 1, 5, 'hdeuwir49834574hnfdj43829449301', false);

insert into "passengers_favourite_routes"("favourite_routes_id", "passenger_id") values (1, 5);
insert into "passengers_favourite_routes"("favourite_routes_id", "passenger_id") values (2, 5);

insert into "rides_passengers"("ride_id", "passengers_id") values (1, 5);
insert into "rides_passengers"("ride_id", "passengers_id") values (2, 5);
insert into "rides_passengers"("ride_id", "passengers_id") values (3, 5);
insert into "rides_passengers"("ride_id", "passengers_id") values (4, 5);

insert into "users"("address", "email", "is_activated", "is_blocked", "name", "password", "profile_picture", "surname", "telephone_number", "role") values ('Hajduk Milosa', 'vozac@gmail.com', true, false, 'Vozac', '$2a$12$DdZJHu0xNmWhNo6qeZrB..dFEGPNqdCFmStxusNrElLQso5ZLVUkW', null , 'Vozacevic', '+381603454212', 1);
insert into "vehicles"("baby_transport", "driver_id", "license_number", "model", "passenger_seats", "pet_transport", "current_location_id", "vehicle_type_id") values (true, 2, 'BP-061-GC', 'FIAT 500', 4, true, 2, 1);

insert into "drivers"("id", "vehicle_id", "is_active") values (8, 2, false);  

insert into "rides_passengers"("ride_id", "passengers_id") values (1, 6);

--insert into "reviews" ("comment", "rating", "type", "passenger_id", "ride_id") values ('Pleasant.', 3, 1, 5, 1);
--insert into "reviews" ("comment", "rating", "type", "passenger_id", "ride_id") values ('Tidy and spacious.', 4, 0, 5, 1);
insert into "reviews" ("comment", "rating", "type", "passenger_id", "ride_id") values ('Great guy.', 5, 1, 6, 1);
insert into "reviews" ("comment", "rating", "type", "passenger_id", "ride_id") values ('Clean and new.', 4, 0, 6, 1);

insert into "reviews" ("comment", "rating", "type", "passenger_id", "ride_id") values ('Would ride again.', 5, 1, 5, 3);
insert into "reviews" ("comment", "rating", "type", "passenger_id", "ride_id") values ('Nice.', 4, 0, 5, 3);

insert into "reviews" ("comment", "rating", "type", "passenger_id", "ride_id") values ('Would ride again.', 5, 1, 1, 4);
insert into "reviews" ("comment", "rating", "type", "passenger_id", "ride_id") values ('Nice.', 4, 0, 1, 4);


insert into "rides_reviews"("ride_id", "reviews_id") values (1, 3);
insert into "rides_reviews"("ride_id", "reviews_id") values (1, 4);
insert into "rides_reviews"("ride_id", "reviews_id") values (1, 5);
insert into "rides_reviews"("ride_id", "reviews_id") values (1, 6);
insert into "rides_reviews"("ride_id", "reviews_id") values (4, 7);
insert into "rides_reviews"("ride_id", "reviews_id") values (4, 8);

