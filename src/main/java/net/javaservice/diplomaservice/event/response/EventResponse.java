package net.javaservice.diplomaservice.event.response;

import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventResponse {
    private int id;
    private String title;
    private String avatar;
    private Integer count_person;
    private String description;
    private double latitude;
    private double longitude;
    private Date datetime;
    private List<TagResponse> tags;
    private List<ImageResponse> images;
}