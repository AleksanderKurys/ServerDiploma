package net.javaservice.diplomaservice.event.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventResponse {
    private int id;
    private String title;
    private String avatar;
    private Integer countPeopleMax;
    private Integer countPeople;
    private String description;
    private double latitude;
    private double longitude;
    private LocalDateTime datetime;
    private List<TagResponse> tags;
    private List<ImageResponse> images;
}