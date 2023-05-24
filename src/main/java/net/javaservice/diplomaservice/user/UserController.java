package net.javaservice.diplomaservice.user;

import lombok.RequiredArgsConstructor;
import net.javaservice.diplomaservice.event.response.AvatarResponse;
import net.javaservice.diplomaservice.event.response.EventResponse;
import net.javaservice.diplomaservice.user.request.UserRequest;
import net.javaservice.diplomaservice.user.response.UserResponse;
import net.javaservice.diplomaservice.user.service.UserService;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping("/getUser")
    public ResponseEntity<UserResponse> getUser(
            @RequestParam Integer id
    ) {
        return ResponseEntity.ok(service.getUser(id));
    }

    @PutMapping("/updateUser")
    public ResponseEntity<Boolean> updateUser(
            @RequestBody UserRequest userRequest
    ) {
        return ResponseEntity.ok(service.updateUser(userRequest));
    }

    @GetMapping("/getEventsAttended")
    public ResponseEntity<ArrayList<EventResponse>> getEventsAttended(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) throws ParseException {

        return ResponseEntity.ok(service.getEventsAttended(parseToken(token)));
    }

    @GetMapping("/")
    public ResponseEntity<UserResponse> getUserWithToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) throws ParseException {

        return ResponseEntity.ok(service.getUserToEmail(parseToken(token)));
    }

    @PostMapping("/upload")
    public ResponseEntity<Boolean> uploadAvatar(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestParam("image") MultipartFile image) throws ParseException, IOException {

        return ResponseEntity.ok(service.uploadAvatar(image, parseToken(token)));
    }

    @GetMapping("/avatar")
    public ResponseEntity<AvatarResponse> getAvatar(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                        .body(service.getAvatar(parseToken(token)));
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
