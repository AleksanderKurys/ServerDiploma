package net.javaservice.diplomaservice.event.entity;

import java.io.Serializable;

public class UserEventId implements Serializable {

    private Integer event_id;

    private Integer user_id;


    public UserEventId(Integer event_id, Integer user_id) {
        this.user_id = user_id;
        this.event_id = event_id;
    }

    // equals() and hashCode()
}
