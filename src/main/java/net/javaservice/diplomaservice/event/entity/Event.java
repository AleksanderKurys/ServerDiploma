package net.javaservice.diplomaservice.event.entity;

import jakarta.persistence.*;
import lombok.*;
import net.javaservice.diplomaservice.authorization.entity.UserEvent;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_events")
public class Event {
    @Id
    @Column(insertable = false)
    private int id;
    private String title;
    @Column(length = 100000)
    private byte[] avatar;
    @Column(name = "count_person")
    private Integer countPerson;
    @Column(name = "count_max")
    private Integer countMax;
    private String description;
    private double latitude;
    private double longitude;

    @Column(name = "day_start")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date startDateTime;

    @Column(name = "day_end")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date endDateTime;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<UserEvent> userEvent;

    @ManyToMany
    private List<Tag> tags;

    @OneToMany(targetEntity = Image.class, mappedBy = "event")
    private List<Image> images;
}