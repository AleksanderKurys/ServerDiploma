create table _event (
    id int primary key,
    title varchar(30),
    avatar varchar(255),
    count_person int,
    description tinytext,
    latitude float,
    longitude float,
    _day datetime,
    tag_id int,
    constraint fk_event foreign key (tag_id) references _tag(id)
);