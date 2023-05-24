package net.javaservice.diplomaservice.event.response;

import jakarta.persistence.Column;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventResponse {
    private int id;
    private String title;
    private byte[] avatar;
    private Integer countPeopleMax;
    private Integer countPeople;
    private String description;
    private double latitude;
    private double longitude;
    private ZonedDateTime startDateTime;
    private ZonedDateTime endDateTime;
    private List<TagResponse> tags;
    private List<ImageResponse> images;
}