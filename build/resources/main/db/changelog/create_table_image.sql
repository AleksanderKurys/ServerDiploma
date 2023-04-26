create table _image (
    id int primary key AUTO_INCREMENT,
    image_data varchar(255),
    event_id int,
    foreign key (event_id) REFERENCES _events(id)
);