package net.javaservice.diplomaservice.authorization.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.javaservice.diplomaservice.event.entity.Event;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_users_events")
public class UserEvent {
    @Id
    private int event_id;
    @Id
    private int user_id;
    @Column(name = "is_registered")
    private Boolean isRegistered;
    @Column(name = "is_visited")
    private Boolean isVisited;

    @ManyToOne(targetEntity = Event.class)
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;
}