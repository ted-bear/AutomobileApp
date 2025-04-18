package com.toporkov.automobileapp.web.rest;

import com.toporkov.automobileapp.model.Manager;
import com.toporkov.automobileapp.service.AuthService;
import com.toporkov.automobileapp.service.ManagerService;
import com.toporkov.automobileapp.web.dto.domain.RegistrationManagerDTO;
import com.toporkov.automobileapp.web.dto.security.JwtRequest;
import com.toporkov.automobileapp.web.dto.security.JwtResponse;
import com.toporkov.automobileapp.web.mapper.ManagerMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final ManagerService managerService;
    private final ManagerMapper managerMapper;

    public AuthController(
        final AuthService authService,
        final ManagerService managerService,
        final ManagerMapper managerMapper
    ) {
        this.authService = authService;
        this.managerService = managerService;
        this.managerMapper = managerMapper;
    }

    @PostMapping("/login")
    public JwtResponse login(@RequestBody JwtRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    public ResponseEntity<HttpStatus> register(
        @RequestBody RegistrationManagerDTO registrationManagerDTO
    ) {
        if (!registrationManagerDTO.getPassword().equals(registrationManagerDTO.getPasswordConfirmation())) {
            throw new IllegalStateException("Password and password confirmation do not match");
        }

        final Manager manager = managerMapper.mapDtoToEntity(registrationManagerDTO);

        managerService.create(manager);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/refresh")
    public JwtResponse refresh(@RequestBody String refreshToken) {
        return authService.refresh(refreshToken);
    }
}
