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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int id;

    @Lob
    @Column(name = "image_data", length = 100000)
    private byte[] imageData;

    @ManyToOne(targetEntity = Event.class)
    @JoinColumn(name="event_id")
    private Event event;
}
