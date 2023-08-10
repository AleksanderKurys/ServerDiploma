package net.javaservice.diplomaservice.event;

import lombok.RequiredArgsConstructor;
import net.javaservice.diplomaservice.event.request.EventRequest;
import net.javaservice.diplomaservice.event.response.EventResponse;
import net.javaservice.diplomaservice.event.service.EventService;
import net.javaservice.diplomaservice.event.service.ImageService;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
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
    public ResponseEntity<Integer> addEvent(@RequestBody EventRequest request) {
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
    public ResponseEntity<byte[]> downloadImage(@RequestParam Integer eventId) throws IOException {
        byte[] imageData = imageService.downloadImage(eventId);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }
    @GetMapping("/getCoordinate")
    public ResponseEntity<List<EventResponse>> getCoordinate()  {
        return ResponseEntity.ok(service.getCoordinate());
    }

    @PostMapping("/signUpEvent")
    public ResponseEntity<Boolean> signUpEvent(@RequestParam Integer eventId,
                                               @RequestHeader(HttpHeaders.AUTHORIZATION) String token) throws ParseException {
        String email = parseToken(token);
        return ResponseEntity.ok(service.signUpEvent(eventId, email));
    }

    @PostMapping("/unsubscribeEvent")
    public ResponseEntity<Boolean> unsubscribeEvent(@RequestParam Integer eventId,
                                               @RequestHeader(HttpHeaders.AUTHORIZATION) String token) throws ParseException {
        String email = parseToken(token);
        return ResponseEntity.ok(service.unsubscribeEvent(eventId, email));
    }

    private String parseToken(String token) throws ParseException {
        String str = token.replace("Bearer ", "");
        String[] newToken = str.split("\\.");

        Base64.Decoder decoder = Base64.getUrlDecoder();

        String payload = new String(decoder.decode(newToken[1]));

        Object obj = new JSONParser().parse(payload);
        JSONObject jo = (JSONObject) obj;
        String email = (String) jo.get("sub");
        return email;
    }
}