create table _user (
    id int primary key AUTO_INCREMENT,
    firstname varchar(255),
    lastname varchar(255),
    middlename varchar(255),
    course tinyint,
    department varchar(8),
    avatar varchar(255),
    email varchar(255),
    password varchar(255),
    role enum('USER','ADMIN')
);