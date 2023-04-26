package net.javaservice.diplomaservice.event;

import lombok.RequiredArgsConstructor;
import net.javaservice.diplomaservice.event.response.EventResponse;
import net.javaservice.diplomaservice.event.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/event")
@RequiredArgsConstructor
public class EventController {
    private final EventService service;

    @GetMapping("/getEvents")
    public ResponseEntity<ArrayList<EventResponse>> getEvents (
            @RequestParam Integer page,
            @RequestParam Integer size
    ) {
        return ResponseEntity.ok(service.getEvents(page, size));
    }

    @GetMapping("/getEvent")
    public ResponseEntity<EventResponse> getEvent (
            @RequestParam Integer id
    ) {
        return ResponseEntity.ok(service.getEvent(id));
    }

    @GetMapping("/search")
    public ResponseEntity<ArrayList<EventResponse>> searchEvent (
            @RequestParam String title
    ) {
        return ResponseEntity.ok(service.searchEventOnText(title));
    }

    @GetMapping("/search/tag")
    public ResponseEntity<ArrayList<EventResponse>> searchTagEvent (
            @RequestParam List<String> tags
    ) {
        return ResponseEntity.ok(service.searchEventOnTag(tags));
    }
}