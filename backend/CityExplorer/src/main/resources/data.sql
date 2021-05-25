-- Passwords are encoded with BCrypt algorithm 

-- password is 'admin' (bcrypt encoded)
INSERT INTO admin (id, username, password, first_name, last_name, email, role, active, registration_date) 
VALUES ( 1000, 'admin', '$2a$10$mj6jiG5FLHp4s2Nvf84mbeeYPjjFmeDDtk7Uzua8sBBWzuXtNoPqW','Boba', 'Bobic', 'tom@gmail.com','ROLE_ADMIN',  true, '2021-05-08 10:00:00.00');

-- password is 'user' (bcrypt encoded)
INSERT INTO registered_user (id, username, password, first_name, last_name, email, role, active, registration_date)  VALUES 
( 100, 'user1', '$2a$10$NBqWWykrf7S2o2V2ja5E7OaDCbjAqcghBIk9dOl1Bj3UIsooKJMkK','Filip', 'Filipovic', 'filipke@gmail.com','ROLE_REGISTERED_USER',  true, '2021-05-08 10:00:00.00'),
( 101, 'user2', '$2a$10$NBqWWykrf7S2o2V2ja5E7OaDCbjAqcghBIk9dOl1Bj3UIsooKJMkK','Anabela', 'Varga', 'annav97@gmail.com','ROLE_REGISTERED_USER',  true, '2021-05-08 10:00:00.00');

INSERT INTO features (id, bus_nearby, childrens_program, outdoor, parking, price, reservations, space, tv, wifi) VALUES
(100, true, true,  true,  true, 'CHEAP', false , 'LARGE', false, false),
(101, true, false, false, false, 'MODERATE', true , 'SMALL', false, true),
(102, true, false, true,  true, 'EXPENSIVE', true , 'MEDIUM', false, true);

INSERT INTO features_keywords (features_id, keywords) VALUES 
(100, 'CHILL'),
(100, 'FAMILY_FRIENDLY'),
(100, 'NATURE'),
(101, 'ADVENTURE'),
(101, 'TEAMBUILDING'),
(102, 'ADRENALINE'),
(102, 'SPORT');

INSERT INTO activity (id,address,description,location,name,features_id, score) VALUES 
(100,'Fruskagorska u. 2, Ledinci', 'Something', 'OUTSIDE_THE_CITY', 'Igraonica Fruska', 100, 0),
(101,'Njegoseva 32', 'Something', 'CITY_CENTER', 'Escape room SAW', 101, 0),
(102,'Bulevar Evropa 22/a', 'Something', 'SUBURBS', 'Go-karting klub NS', 102, 0);


INSERT INTO rating(id, creation, rating, activity_id, user_id) VALUES
(100, '2021-05-08 10:55:33.111400', 5, 100, 100);

INSERT INTO activity_ratings (activity_id, ratings_id) VALUES
(100, 100);


--INSERT INTO registered_user_recommended_activities (registered_user_id, recommended_activities_id) VALUES 
--(100, 100);
