package com.toporkov.automobileapp.web.rest;

import com.toporkov.automobileapp.service.AuthService;
import com.toporkov.automobileapp.service.ManagerService;
import com.toporkov.automobileapp.web.dto.RegistrationManagerDTO;
import com.toporkov.automobileapp.web.dto.security.JwtRequest;
import com.toporkov.automobileapp.web.dto.security.JwtResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final ManagerService managerService;

    public AuthController(final AuthService authService,
                          final ManagerService managerService) {
        this.authService = authService;
        this.managerService = managerService;
    }

    @PostMapping("/login")
    public JwtResponse login(@RequestBody JwtRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    public ResponseEntity<HttpStatus> register(
            @RequestBody RegistrationManagerDTO registrationManagerDTO
    ) {
        managerService.create(registrationManagerDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/refresh")
    public JwtResponse refresh(@RequestBody String refreshToken) {
        return authService.refresh(refreshToken);
    }
}
