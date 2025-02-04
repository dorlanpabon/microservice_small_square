package com.pragma.powerup.infrastructure.adapter;

import com.pragma.powerup.infrastructure.client.UserFeignClient;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceAdapterTest {

    @Mock
    private UserFeignClient userFeignClient;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private UserServiceAdapter userServiceAdapter;

    private final Long userId = 1L;
    private final String phoneNumber = "+1234567890";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testIsOwner_Success() {
        doNothing().when(userFeignClient).isOwner(userId);

        boolean result = userServiceAdapter.isOwner(userId);

        assertTrue(result);
        verify(userFeignClient, times(1)).isOwner(userId);
    }

    @Test
    void testIsOwner_Failure() {
        doThrow(FeignException.class).when(userFeignClient).isOwner(userId);

        boolean result = userServiceAdapter.isOwner(userId);

        assertFalse(result);
        verify(userFeignClient, times(1)).isOwner(userId);
    }

    @Test
    void testGetUserId_Success() {
        when(request.getAttribute("userId")).thenReturn(userId);

        Long result = userServiceAdapter.getUserId();

        assertNotNull(result);
        assertEquals(userId, result);
        verify(request, times(1)).getAttribute("userId");
    }

    @Test
    void testGetUserId_Null() {
        when(request.getAttribute("userId")).thenReturn(null);

        Long result = userServiceAdapter.getUserId();

        assertNull(result);
        verify(request, times(1)).getAttribute("userId");
    }

    @Test
    void testGetPhone_Success() {
        when(userFeignClient.getPhone(userId)).thenReturn(phoneNumber);

        String result = userServiceAdapter.getPhone(userId);

        assertNotNull(result);
        assertEquals(phoneNumber, result);
        verify(userFeignClient, times(1)).getPhone(userId);
    }
}
