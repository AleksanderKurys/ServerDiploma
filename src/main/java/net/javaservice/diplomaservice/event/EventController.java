package net.javaservice.diplomaservice.event;

import lombok.RequiredArgsConstructor;
import net.javaservice.diplomaservice.event.request.EventRequest;
import net.javaservice.diplomaservice.event.response.EventResponse;
import net.javaservice.diplomaservice.event.service.EventService;
import net.javaservice.diplomaservice.event.service.ImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/event")
@RequiredArgsConstructor
public class EventController {

    private final EventService service;
    private final ImageService imageService;

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

    @PostMapping("/add")
    public ResponseEntity<Boolean> addEvent(@RequestBody EventRequest request) {
        return ResponseEntity.ok(service.addEvent(request));
    }

    @PutMapping("/update")
    public ResponseEntity<Boolean> updateEvent(@RequestParam Integer id, @RequestBody EventRequest request) {
        return ResponseEntity.ok(service.updateEvent(id, request));
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file, @RequestParam(required = false) Integer eventId) throws IOException {
        String uploadImage = imageService.uploadImage(file, eventId);
        return ResponseEntity.ok(uploadImage);
    }

    @GetMapping("/download")
    public ResponseEntity<ArrayList<byte[]>> downloadImage(@RequestParam Integer eventId) throws IOException {
        ArrayList<byte[]> imageData = imageService.downloadImage(eventId);
        return ResponseEntity.ok(imageData);
    }
}