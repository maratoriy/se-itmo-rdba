package se.itmo.moratorium.rdba.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.itmo.moratorium.rdba.dto.UserLoginDto;
import se.itmo.moratorium.rdba.dto.UserRegistrationDto;
import se.itmo.moratorium.rdba.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegistrationDto registrationDto) {
        String token = authService.register(registrationDto);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDto loginDto) {
        String token = authService.authenticate(loginDto.getLogin(), loginDto.getPassword());
        return ResponseEntity.ok(token);
    }
}