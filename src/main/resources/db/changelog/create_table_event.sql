create table _events (
    id int primary key AUTO_INCREMENT,
    title varchar(30),
    avatar MEDIUMBLOB,
    count_person int,
    count_max int,
    description text,
    latitude float,
    longitude float,
    day_start datetime,
    day_end datetime
);