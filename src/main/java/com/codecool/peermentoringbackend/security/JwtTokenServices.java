package com.codecool.peermentoringbackend.security;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Component
@Slf4j
public class JwtTokenServices {

    @Value("${security.jwt.token.secret-key:secret}")
    private String secretKey = "secret";

    @Value("${security.jwt.token.expire-length:3600000}")
    private long validityInMilliseconds = 36000000; // 10 hours

    private final String rolesFieldName = "roles";

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String username, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put(rolesFieldName, roles);

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String getTokenFromRequest(HttpServletRequest req) {
        String mycookie = null;
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("authentication")) {
                    mycookie = cookie.getValue();
                    return mycookie;
                }
            }
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            if (claims.getBody().getExpiration().before(new Date())) {
                return false;
            }
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.debug("JWT token invalid " + e);
        }
        return false;
    }

    Authentication parseUserFromTokenInfo(String token) throws UsernameNotFoundException {
        Claims body = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        String username = body.getSubject();
        List<String> roles = (List<String>) body.get(rolesFieldName);
        List<SimpleGrantedAuthority> authorities = new LinkedList<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return new UsernamePasswordAuthenticationToken(username, "", authorities);
    }

    public String getUsernameFromToken(HttpServletRequest req) {
        String mycookie = null;
        Cookie[] cookies = req.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("authentication")) {
                    mycookie = cookie.getValue();
                    Claims body = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(mycookie).getBody();
                    return body.getSubject();
                }
            }
        }

        return null;
    }

}