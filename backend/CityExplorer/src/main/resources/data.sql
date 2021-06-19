-- Passwords are encoded with BCrypt algorithm 

-- password is 'admin' (bcrypt encoded)
INSERT INTO admin (id, username, password, first_name, last_name, email, role, active, registration_date) 
VALUES ( 1000, 'admin', '$2a$10$mj6jiG5FLHp4s2Nvf84mbeeYPjjFmeDDtk7Uzua8sBBWzuXtNoPqW','Boba', 'Bobic', 'tom@gmail.com','ROLE_ADMIN',  true, '2021-05-08 10:00:00.00');

-- password is 'user' (bcrypt encoded)
INSERT INTO registered_user (id, username, password, first_name, last_name, email, role, active, registration_date)  VALUES 
( 100, 'user1', '$2a$10$NBqWWykrf7S2o2V2ja5E7OaDCbjAqcghBIk9dOl1Bj3UIsooKJMkK','Filip', 'Filipovic', 'filipke@gmail.com','ROLE_REGISTERED_USER',  true, '2021-05-08 10:00:00.00'),
( 101, 'user2', '$2a$10$NBqWWykrf7S2o2V2ja5E7OaDCbjAqcghBIk9dOl1Bj3UIsooKJMkK','Anabela', 'Varga', 'annav97@gmail.com','ROLE_REGISTERED_USER',  true, '2021-05-08 10:00:00.00'),
( 102, 'user3', '$2a$10$NBqWWykrf7S2o2V2ja5E7OaDCbjAqcghBIk9dOl1Bj3UIsooKJMkK','Test', 'Testic', 'tester597@gmail.com','ROLE_REGISTERED_USER',  true, '2021-05-08 10:00:00.00'),
( 103, 'user4', '$2a$10$NBqWWykrf7S2o2V2ja5E7OaDCbjAqcghBIk9dOl1Bj3UIsooKJMkK','Test2', 'Testeric', 'vodavoda@gmail.com','ROLE_REGISTERED_USER',  true, '2021-05-08 10:00:00.00'),
( 104, 'user5', '$2a$10$NBqWWykrf7S2o2V2ja5E7OaDCbjAqcghBIk9dOl1Bj3UIsooKJMkK','Asdf', 'Gaziranavoda', 'minaqua@gmail.com','ROLE_REGISTERED_USER',  true, '2021-05-08 10:00:00.00'),
( 105, 'user6', '$2a$10$NBqWWykrf7S2o2V2ja5E7OaDCbjAqcghBIk9dOl1Bj3UIsooKJMkK','Pera', 'Peric', 'satiUser95@gmail.com','ROLE_REGISTERED_USER',  true, '2021-05-08 10:00:00.00'),
( 106, 'user7', '$2a$10$NBqWWykrf7S2o2V2ja5E7OaDCbjAqcghBIk9dOl1Bj3UIsooKJMkK','Mika', 'Mikic', 'disSatiUser@gmail.com','ROLE_REGISTERED_USER',  true, '2021-05-08 10:00:00.00');

INSERT INTO features (id, bus_nearby, childrens_program, outdoor, parking, price, reservations, space, tv, wifi) VALUES
(100, true, true,  true,  true, 'CHEAP', false , 'LARGE', false, false),
(101, true, false, false, false, 'MODERATE', true , 'SMALL', false, true),
(102, true, false, true,  true, 'EXPENSIVE', true , 'MEDIUM', false, true),
(103, false, false, true,  true, 'CHEAP', true , 'MEDIUM', false, false),
(104, true, false, true,  true, 'FREE', false , 'LARGE', false, false),
(105, true, true, false,  false, 'MODERATE', true , 'LARGE', true, true),
(106, true, false, true,  false, 'MODERATE', false , 'SMALL', false, false),
(107, true, false, false,  true, 'CHEAP', true , 'LARGE', true, true),
(108, true, false, false,  true, 'EXPENSIVE', true , 'MEDIUM', false, true),
(109, false, false, false,  true, 'CHEAP', false , 'LARGE', false, true);

INSERT INTO features_keywords (features_id, keywords) VALUES 
(100, 'CHILL'),
(100, 'FAMILY_FRIENDLY'),
(100, 'NATURE'),
(101, 'ADVENTURE'),
(101, 'TEAMBUILDING'),
(102, 'ADRENALINE'),
(102, 'SPORT'),
(103, 'NATURE'),
(103, 'ADVENTURE'),
(103, 'SPORT'),
(104, 'NATURE'),
(104, 'CHILL'),
(104, 'SPORT'),
(105, 'SPORT'),
(105, 'FAMILY_FRIENDLY'),
(105, 'ADRENALINE'),
(106, 'ROMANTIC'),
(106, 'CHILL'),
(107, 'CHILL'),
(107, 'TEAMBUILDING'),
(108, 'CHILL'),
(108, 'LUXURY'),
(108, 'ROMANTIC'),
(109, 'HISTORY'),
(109, 'EDUCATIONAL');

INSERT INTO activity (id,address,description,location,name,features_id, score, image_url, average) VALUES 
(100,'Fruskagorska u. 2, Ledinci', 'A great place to spend your time in the nature - with or without the children.', 'OUTSIDE_THE_CITY', 'Igraonica Fruska', 100, 0, 'https://media1.lepojeziveti.com/2017/07/de%C4%8Dje-igrali%C5%A1te-i-pogled.jpg', 3),
(101,'Njegoseva 32', 'A thrilling experience you will never forget!', 'CITY_CENTER', 'Escape room SAW', 101, 0, 'https://miro.medium.com/max/1024/0*zo_1nSzUsWX8flLs.jpg', 0),
(102,'Bulevar Evropa 22/a', 'The best place for motorheads, at the best go-kart track in Vojvodina.', 'SUBURBS', 'Go-karting klub NS', 102, 0, 'https://www.fluxmagazine.com/wp-content/uploads/2017/02/Karting-Web-3.jpg', 0),
(103,'Dunavska 22', 'Rent kayaks and explore river danube with Kayakind Club ROWER!', 'SUBURBS', 'Kayaking on Danube', 103, 0, 'https://wildserbia.com/wp-content/uploads/2016/04/danube-kayaking-featured.jpg', 0),
(104,'Fruska Gora', 'Explore the hidden hiking paths of Fruska Gora.', 'OUTSIDE_THE_CITY', 'Hiking Fruska gora', 104, 0, 'https://explore-serbia.rs/wp-content/uploads/2018/12/Fru%C5%A1ka-Gora-hiking-scaled-e1575387407623.jpg', 0),
(105,'Primorska 55', 'Best place to cool off and have fun!', 'SUBURBS', 'Trambouline park Amplitudo', 105, 0, 'https://www.funfactorystudio.com/ffs/wp-content/uploads/2020/07/Amplituda-Trambolina-park-Novi-Sad-46.jpg', 0),
(106,'Trg Slobode', 'Discover the city in the most romantic way!', 'CITY_CENTER', 'Travelling with a horse carriage', 106, 0, 'https://image.jimcdn.com/app/cms/image/transf/dimension=1920x400:format=jpg/path/sa6549607c78f5c11/image/id8ba0b5a1c652fa5/version/1574940303/image.jpg', 3.5),
(107,'Bulevar oslobođenja 27', 'Ice hockey, bowling, table tennis, biliard.. You chose what you want to try!', 'CITY_CENTER', 'Bowling club Zabac', 107, 0, 'https://zabac.rs/wp-content/uploads/2018/11/DAC_8679.jpg', 3),
(108,'Novosadskog sajma 35', 'Relaxing massages, luxury private pool and saunas wait for you!', 'CITY_CENTER', 'Spa day at Hotel Park', 108, 0, 'http://www.wellnesshotelparkns.com/images/slideshow/slide-jacuzzi.jpg', 3),
(109,'Petrovaradin fortress', 'Learn about the history of Novi Sad and the serbian culture!', 'SUBURBS', 'Visiting the City Museum', 109, 0, 'http://www.panacomp.net/wp-content/uploads/2016/03/dsc_03121.jpg', 3);


INSERT INTO rating(id, creation, rating, activity_id, user_id) VALUES
(100, '2021-05-08 10:55:33.111400', 5, 100, 100),
(101, '2021-05-08 10:55:33.111400', 2, 100, 103),
(102, '2021-05-08 10:55:33.111400', 2, 100, 104),
--satis/dissatis
(103, '2021-05-08 10:55:33.111400', 5, 106, 105),
(104, '2021-05-08 10:55:33.111400', 5, 107, 105),
(105, '2021-05-08 10:55:33.111400', 5, 108, 105),
(106, '2021-05-08 10:55:33.111400', 5, 109, 105),
(107, '2021-05-08 10:55:33.111400', 1, 106, 106),
(108, '2021-05-08 10:55:33.111400', 1, 107, 106),
(109, '2021-05-08 10:55:33.111400', 1, 108, 106),
(110, '2021-05-08 10:55:33.111400', 1, 109, 106);

INSERT INTO activity_ratings (activity_id, ratings_id) VALUES
(100, 100),
(100, 101),
(100, 102),
--satis/dissatis
(106, 103),
(107, 104),
(108, 105),
(109, 106),
(106, 107),
(107, 108),
(108, 109),
(109, 110);


INSERT INTO registered_user_recommended_activities (registered_user_id, recommended_activities_id) VALUES 
(100, 100),
(103, 100),
(104, 100),
(102, 100),
(102, 101),
(102, 102),
(102, 103),
(102, 104),
(102, 105),
(102, 106),
(102, 107),
(102, 108),
(102, 109),
(105, 103),
(105, 104),
(105, 105),
(105, 106),
(105, 107),
(105, 108),
(105, 109),
(106, 103),
(106, 104),
(106, 105),
(106, 106),
(106, 107),
(106, 108),
(106, 109);
