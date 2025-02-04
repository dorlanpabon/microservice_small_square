package com.pragma.powerup.infrastructure.adapter;

import com.pragma.powerup.infrastructure.client.MessageFeignClient;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MessageServiceAdapterTest {

    @Mock
    private MessageFeignClient messageFeignClient;

    @InjectMocks
    private MessageServiceAdapter messageServiceAdapter;

    private final String phoneNumber = "+1234567890";
    private final String verificationCode = "1234";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendCode_Success() {
        doNothing().when(messageFeignClient).sendCode(phoneNumber);

        boolean result = messageServiceAdapter.sendCode(phoneNumber);

        assertTrue(result);
        verify(messageFeignClient, times(1)).sendCode(phoneNumber);
    }

    @Test
    void testSendCode_Failure() {
        doThrow(FeignException.class).when(messageFeignClient).sendCode(phoneNumber);

        boolean result = messageServiceAdapter.sendCode(phoneNumber);

        assertFalse(result);
        verify(messageFeignClient, times(1)).sendCode(phoneNumber);
    }

    @Test
    void testVerifyCode_Success() {
        doNothing().when(messageFeignClient).verifyCode(phoneNumber, verificationCode);

        boolean result = messageServiceAdapter.verifyCode(phoneNumber, verificationCode);

        assertTrue(result);
        verify(messageFeignClient, times(1)).verifyCode(phoneNumber, verificationCode);
    }

    @Test
    void testVerifyCode_Failure() {
        doThrow(FeignException.class).when(messageFeignClient).verifyCode(phoneNumber, verificationCode);

        boolean result = messageServiceAdapter.verifyCode(phoneNumber, verificationCode);

        assertFalse(result);
        verify(messageFeignClient, times(1)).verifyCode(phoneNumber, verificationCode);
    }
}
