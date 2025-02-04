package com.pragma.powerup.infrastructure.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JwtUtilTest {

    private final JwtUtil jwtUtil = new JwtUtil();
    UserDetails userDetails = new User("username", "password",
            Collections.singleton(new SimpleGrantedAuthority("ROLE_OWNER")));
    private final String role = "OWNER";
    private final Long userId = 1L;
    private String token;

    @BeforeEach
    void setUp() {
        String rawSecret = "abcdefghijklmnopqrstuvxyz0123456";
        String secretKeyBase64 = Base64.getEncoder().encodeToString(rawSecret.getBytes(StandardCharsets.UTF_8));
        ReflectionTestUtils.setField(jwtUtil, "secretKey", secretKeyBase64);
        ReflectionTestUtils.setField(jwtUtil, "expirationTime", 3600000L);

        token = jwtUtil.generateToken(userDetails, role, userId);
    }

    @Test
    void testGenerateToken() {
        assertTrue(jwtUtil.validateToken(token, "username"));
    }

    @Test
    void testExtractUsername() {
        String extractedUsername = jwtUtil.extractUsername(token);
        assertEquals("username", extractedUsername);
    }

    @Test
    void testExtractRole() {
        String extractedRole = jwtUtil.extractRole(token);
        assertEquals(role, extractedRole);
    }

    @Test
    void testValidateToken() {
        boolean valid = jwtUtil.validateToken(token, "username");
        assertTrue(valid);

        boolean invalid = jwtUtil.validateToken(token, "otroUsername");
        assertFalse(invalid);
    }

    @Test
    void testExtractUserId() {
        Long extractedUserId = jwtUtil.extractUserId(token);
        assertEquals(userId, extractedUserId);
    }
}
