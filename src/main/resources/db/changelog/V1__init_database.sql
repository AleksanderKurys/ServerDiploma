create table _user (
    id int primary key,
    firstname varchar(255),
    lastname varchar(255),
    email varchar(255),
    password varchar(255),
    role enum('USER','ADMIN')
);