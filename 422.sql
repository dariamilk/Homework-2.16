create table cars (id int8 primary key, brand varchar, model varchar, price int8);
insert into cars values (1, 'BMW', 'X6', 2000);
create table drivers (id int8 primary key, name varchar, age int8, driver_license boolean, car_id int8 references cars (id));
insert into drivers values (1, 'Alex', 23, 'true', 1);
insert into drivers values (2, 'Jane', 22, 'true', 1);