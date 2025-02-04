package com.pragma.powerup.infrastructure.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class JwtFilterTest {

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private JwtFilter jwtFilter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.clearContext();
    }

    @Test
    public void testDoFilterInternal_withoutAuthorizationHeader() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = mock(FilterChain.class);

        jwtFilter.doFilter(request, response, chain);

        assertNull(SecurityContextHolder.getContext().getAuthentication(), "The authentication must be null");

        verify(chain, times(1)).doFilter(request, response);
    }

    @Test
    public void testDoFilterInternal_withInvalidAuthorizationHeader() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = mock(FilterChain.class);

        request.addHeader("Authorization", "Basic someToken");

        jwtFilter.doFilter(request, response, chain);

        assertNull(SecurityContextHolder.getContext().getAuthentication(), "The authentication must be null");

        verify(chain, times(1)).doFilter(request, response);
    }
}
