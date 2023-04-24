package net.javaservice.diplomaservice.event.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.Set;


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
    private Set<String> tags;
}