package com.toporkov.automobileapp.service;

import com.toporkov.automobileapp.model.Manager;
import com.toporkov.automobileapp.security.JwtTokenProvider;
import com.toporkov.automobileapp.web.dto.security.JwtRequest;
import com.toporkov.automobileapp.web.dto.security.JwtResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final ManagerService managerService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(
        final AuthenticationManager authenticationManager,
        final ManagerService managerService,
        final JwtTokenProvider jwtTokenProvider
    ) {
        this.authenticationManager = authenticationManager;
        this.managerService = managerService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public JwtResponse login(JwtRequest loginRequest) {
        final Authentication authentication = new UsernamePasswordAuthenticationToken(
            loginRequest.getUsername(),
            loginRequest.getPassword()
        );

        authenticationManager.authenticate(authentication);
        final Manager manager = managerService.getByUsername(loginRequest.getUsername());
        final JwtResponse jwtResponse = new JwtResponse();
        final Integer managerId = manager.getId();
        final String managerUsername = manager.getUsername();

        jwtResponse.setId(managerId);
        jwtResponse.setUsername(managerUsername);
        jwtResponse.setAccessToken(jwtTokenProvider.createAccessToken(managerId, managerUsername, manager.getRole()));
        jwtResponse.setRefreshToken(jwtTokenProvider.createRefreshToken(managerId, managerUsername));

        return jwtResponse;
    }

    public JwtResponse refresh(String refreshToken) {
        return jwtTokenProvider.refreshUserTokens(refreshToken);
    }
}
