package net.javaservice.diplomaservice.authorization.service;

import lombok.RequiredArgsConstructor;
import net.javaservice.diplomaservice.authorization.response.AuthenticationResponse;
import net.javaservice.diplomaservice.authorization.request.LoginRequest;
import net.javaservice.diplomaservice.authorization.request.RegisterRequest;
import net.javaservice.diplomaservice.configuration.JwtService;
import net.javaservice.diplomaservice.authorization.repository.UserRepository;
import net.javaservice.diplomaservice.authorization.entity.Role;
import net.javaservice.diplomaservice.authorization.entity.User;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthenticationResponse registration(RegisterRequest request) {

        var checkUser = repository.findByEmail(request.getEmail()).isPresent();

        if (checkUser == true) {
            throw new BadCredentialsException("Emails equitable");
        }

        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        repository.save(user);
        var token = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    public AuthenticationResponse login(LoginRequest request) {

        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("BadCredentials");
        }

        var token = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }
}
