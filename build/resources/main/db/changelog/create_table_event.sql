create table _events (
    id int primary key,
    title varchar(30),
    avatar varchar(255),
    count_person int,
    count_max int,
    description text,
    latitude float,
    longitude float,
    _day datetime
);