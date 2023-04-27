create table _users_events (
    event_id int not null,
    user_id int not null,
    constraint fk_event_user foreign key (event_id) REFERENCES _events(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    constraint fk_user_event foreign key  (user_id) REFERENCES _user(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    PRIMARY KEY (event_id, user_id),
    is_registered boolean,
    is_visited boolean
);