package com.toporkov.automobileapp.security;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import com.toporkov.automobileapp.model.Manager;
import com.toporkov.automobileapp.model.Role;
import com.toporkov.automobileapp.service.ManagerService;
import com.toporkov.automobileapp.util.exception.TokenExpiredException;
import com.toporkov.automobileapp.web.dto.security.JwtResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;
    private final UserDetailsService userDetailsService;
    private final ManagerService managerService;
    private Key key;

    public JwtTokenProvider(
        final JwtProperties jwtProperties,
        final UserDetailsService userDetailsService,
        final ManagerService managerService
    ) {
        this.jwtProperties = jwtProperties;
        this.userDetailsService = userDetailsService;
        this.managerService = managerService;
    }

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }

    public String createAccessToken(Integer userId, String username, Role role) {
        final Claims claims = Jwts.claims().setSubject(username);
        claims.put("id", userId);
        claims.put("role", role.name());

        final Instant validity = Instant.now().plus(jwtProperties.getAccess(), ChronoUnit.HOURS);

        return Jwts.builder()
            .setClaims(claims)
            .setExpiration(Date.from(validity))
            .signWith(key)
            .compact();
    }

    public String createRefreshToken(Integer userId, String username) {
        final Claims claims = Jwts.claims().setSubject(username);
        claims.put("id", userId);

        final Instant validity = Instant.now()
            .plus(jwtProperties.getAccess(), ChronoUnit.DAYS);

        return Jwts.builder()
            .setClaims(claims)
            .setExpiration(Date.from(validity))
            .signWith(key)
            .compact();
    }

    public JwtResponse refreshUserTokens(String refreshToken) {

        if (isTokenExpired(refreshToken)) {
            throw new TokenExpiredException();
        }

        Integer userId = Integer.valueOf(getId(refreshToken));
        final Manager manager = managerService.getById(userId);
        final JwtResponse jwtResponse = new JwtResponse();
        final String username = manager.getUsername();

        jwtResponse.setId(manager.getId());
        jwtResponse.setUsername(username);
        jwtResponse.setAccessToken(createAccessToken(userId, username, manager.getRole()));
        jwtResponse.setRefreshToken(createRefreshToken(userId, username));

        return jwtResponse;
    }

    public boolean isTokenExpired(String token) {
        final Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        return claimsJws.getBody().getExpiration().before(new Date());
    }

    public Authentication getAuthentication(String token) {
        String username = getUsername(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private String getId(String token) {
        return Jwts
            .parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .get("id")
            .toString();
    }

    private String getUsername(String token) {
        return Jwts
            .parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }
}
