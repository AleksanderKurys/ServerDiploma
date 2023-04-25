create table _image (
    id int primary key,
    image_data varchar(255),
    event_id int,
    constraint fk_event foreign key (event_id) REFERENCES _events(id)
);