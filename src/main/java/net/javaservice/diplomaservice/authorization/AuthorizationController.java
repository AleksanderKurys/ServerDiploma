package net.javaservice.diplomaservice.authorization;

import lombok.RequiredArgsConstructor;
import net.javaservice.diplomaservice.authorization.dto.LoginRequest;
import net.javaservice.diplomaservice.authorization.dto.RegisterRequest;
import net.javaservice.diplomaservice.authorization.service.AuthorizationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/authorization")
@RequiredArgsConstructor
public class AuthorizationController {
    private final AuthorizationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registration(
        @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.registration(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody LoginRequest request
    ) {
        return ResponseEntity.ok(service.login(request));
    }
}
