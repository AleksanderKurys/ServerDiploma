create table _event_tags (
    event_id int not null,
    tags_id int not null,
    constraint fk_event_tag foreign key (event_id) REFERENCES _event(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    constraint fk_tag_event foreign key  (tags_id) REFERENCES _tag(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    PRIMARY KEY (event_id, tags_id)
);