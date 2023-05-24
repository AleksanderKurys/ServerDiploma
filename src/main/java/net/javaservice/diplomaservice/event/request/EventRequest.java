package net.javaservice.diplomaservice.event.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventRequest {
    private String title;
    private byte[] avatar;
    private Integer countPeopleMax;
    private Integer countPeople;
    private String description;
    private double latitude;
    private double longitude;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private List<TagRequest> tags;
    private List<ImageRequest> images;
}
