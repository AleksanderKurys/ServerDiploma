package net.javaservice.diplomaservice.event.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_image")
public class Image {
    @Id
    private int id;
    @Column(name = "image_data")
    private String imageData;

    @ManyToOne(targetEntity = Event.class)
    @JoinColumn(name="event_id")
    private Event event;
}
