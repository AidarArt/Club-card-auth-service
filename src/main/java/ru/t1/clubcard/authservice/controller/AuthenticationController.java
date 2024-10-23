package ru.t1.clubcard.authservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.t1.clubcard.authservice.dto.in.ClubMemberLoginRequest;
import ru.t1.clubcard.authservice.dto.in.ClubMemberRegisterRequest;
import ru.t1.clubcard.authservice.dto.in.RefreshTokenRequest;
import ru.t1.clubcard.authservice.dto.out.ClubMemberLoginResponse;
import ru.t1.clubcard.authservice.dto.out.ClubMemberRegisterResponse;
import ru.t1.clubcard.authservice.service.AuthenticationService;

@RestController
@RequestMapping("/api/v1/club-card/auth-service")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<ClubMemberRegisterResponse> register(@RequestBody ClubMemberRegisterRequest request) {
        return ResponseEntity.status(201).body(authenticationService.register(request));

    }

    @PostMapping("/login")
    public ResponseEntity<ClubMemberLoginResponse> login(@RequestBody ClubMemberLoginRequest request) {
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ClubMemberLoginResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(authenticationService.refreshToken(request));
    }
}
