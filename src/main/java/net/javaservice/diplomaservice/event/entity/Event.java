package net.javaservice.diplomaservice.event.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_events")
public class Event {
    @Id
    private int id;
    private String title;
    private String avatar;
    private Integer count_person;
    private String description;
    private double latitude;
    private double longitude;

    @Column(name = "_day")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date datetime;

    @ManyToMany
    private Set<Tag> tags;

    @OneToMany(targetEntity = Image.class, mappedBy = "event")
    private List<Image> images;
}