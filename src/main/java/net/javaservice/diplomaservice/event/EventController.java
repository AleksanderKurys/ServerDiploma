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

    @GetMapping("/pagination")
    public ResponseEntity<ArrayList<EventResponse>> pagination (
            @RequestParam Integer page,
            @RequestParam Integer size
    ){
        return ResponseEntity.ok(service.getEvents(page, size));
    }
}