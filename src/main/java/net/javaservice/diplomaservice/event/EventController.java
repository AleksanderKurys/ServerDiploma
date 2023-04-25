package net.javaservice.diplomaservice.event;

import lombok.RequiredArgsConstructor;
import net.javaservice.diplomaservice.event.response.EventResponse;
import net.javaservice.diplomaservice.event.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

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
}