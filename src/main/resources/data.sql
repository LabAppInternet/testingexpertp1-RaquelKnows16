insert into userlab(username, name, second_name, email, password)
values('joanra', 'Joan Ramon', 'Roca', 'joanra@gmail.com', '{bcrypt}$2a$10$fVKfcc47q6lrNbeXangjYeY000dmjdjkdBxEOilqhapuTO5ZH0co2');

insert into userlab(username, name, second_name, email, password)
values('tina', 'Cristina', 'Garcia', 'tina@gmail.com', '{bcrypt}$2a$10$fVKfcc47q6lrNbeXangjYeY000dmjdjkdBxEOilqhapuTO5ZH0co2');

insert into station(latitud, longitud, nom) values('41.65434', '0.685766', 'Lleida-Pirineus');
insert into station(latitud, longitud, nom) values('41.654221', '0.685937', 'Alcoletge');
insert into station(latitud, longitud, nom) values('41.687383', '0.72789', 'Vilanova de la Barca');
insert into station(latitud, longitud, nom) values('41.716451', '0.76295', 'Térmens');
insert into station(latitud, longitud, nom) values('41.65434', '0.685766', 'Vallfogona de Balaguer');
insert into station(latitud, longitud, nom) values('41.65434', '0.685766', 'Balaguer');
insert into station(latitud, longitud, nom) values('41.65434', '0.685766', 'Gerb');
insert into station(latitud, longitud, nom) values('41.65434', '0.685766', 'Sant Llorenç de Montgai');
insert into station(latitud, longitud, nom) values('41.65434', '0.685766', 'Vilanova de la Sal');
insert into station(latitud, longitud, nom) values('41.65434', '0.685766', 'Santa Linya');
insert into station(latitud, longitud, nom) values('41.65434', '0.685766', 'Àger');
insert into station(latitud, longitud, nom) values('41.65434', '0.685766', 'Cellers-Llimiana');
insert into station(latitud, longitud, nom) values('41.65434', '0.685766', 'Guàrdia de Tremp');
insert into station(latitud, longitud, nom) values('41.65434', '0.685766', 'Palau de Noguera');
insert into station(latitud, longitud, nom) values('41.65434', '0.685766', 'Tremp');
insert into station(latitud, longitud, nom) values('41.65434', '0.685766', 'Salàs de Pallars');
insert into station(latitud, longitud, nom) values('41.65434', '0.685766', 'La Pobla de Segur');

insert into journey(journey_id, origin, destination) values('1', 'Lleida-Pirineus', 'La Pobla de Segur');
insert into favorite_journey(favorite_journey_id, journey, user_id) values('1', '1', 'tina');
insert into day_time_start (daytime_id, timeStart, day_of_week, favorite_journey_id) values ('1', '12:51', 'Monday', '1');
insert into day_time_start (daytime_id, timeStart, day_of_week, favorite_journey_id) values ('2', '12:30', 'Tuesday', '1');

insert into friend(username, friend) values('tina', 'maria');
insert into friend(username, friend) values('tina', 'pepe');
insert into friend(username, friend) values('tina', 'pepa');
insert into friend(username, friend) values('joanra', 'pepa');
