package com.courses.controller;

import com.courses.controller.annotation.ValidRegisterRequestDto;
import com.courses.dto.AuthenticateRequestDto;
import com.courses.dto.AuthenticationResponseDto;
import com.courses.dto.RegisterRequestDto;
import com.courses.service.impl.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Validated
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDto> register(@RequestBody @ValidRegisterRequestDto RegisterRequestDto request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDto> authenticate(@RequestBody AuthenticateRequestDto request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

}
