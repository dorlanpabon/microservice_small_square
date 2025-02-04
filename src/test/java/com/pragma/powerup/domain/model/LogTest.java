package com.pragma.powerup.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class LogTest {

    private Log log;

    @BeforeEach
    void setUp() {
        log = new Log();
        log.setId("log-123");
        log.setType("ORDER_CREATED");
        log.setChefId(5L);
        log.setClientId(10L);
        log.setOrderId(100L);
        log.setRestaurantId(200L);
        log.setTimestamp(LocalDateTime.of(2023, 5, 15, 10, 30));
        log.setTime(600L);
    }

    @Test
    void testGetId() {
        assertEquals("log-123", log.getId());
    }

    @Test
    void testSetId() {
        log.setId("log-456");
        assertEquals("log-456", log.getId());
    }

    @Test
    void testGetType() {
        assertEquals("ORDER_CREATED", log.getType());
    }

    @Test
    void testSetType() {
        log.setType("ORDER_COMPLETED");
        assertEquals("ORDER_COMPLETED", log.getType());
    }

    @Test
    void testGetChefId() {
        assertEquals(5L, log.getChefId());
    }

    @Test
    void testSetChefId() {
        log.setChefId(6L);
        assertEquals(6L, log.getChefId());
    }

    @Test
    void testGetClientId() {
        assertEquals(10L, log.getClientId());
    }

    @Test
    void testSetClientId() {
        log.setClientId(20L);
        assertEquals(20L, log.getClientId());
    }

    @Test
    void testGetOrderId() {
        assertEquals(100L, log.getOrderId());
    }

    @Test
    void testSetOrderId() {
        log.setOrderId(150L);
        assertEquals(150L, log.getOrderId());
    }

    @Test
    void testGetRestaurantId() {
        assertEquals(200L, log.getRestaurantId());
    }

    @Test
    void testSetRestaurantId() {
        log.setRestaurantId(250L);
        assertEquals(250L, log.getRestaurantId());
    }

    @Test
    void testGetTimestamp() {
        assertNotNull(log.getTimestamp());
        assertEquals(LocalDateTime.of(2023, 5, 15, 10, 30), log.getTimestamp());
    }

    @Test
    void testSetTimestamp() {
        LocalDateTime newTimestamp = LocalDateTime.of(2024, 1, 1, 12, 0);
        log.setTimestamp(newTimestamp);
        assertEquals(newTimestamp, log.getTimestamp());
    }

    @Test
    void testGetTime() {
        assertEquals(600L, log.getTime());
    }

    @Test
    void testSetTime() {
        log.setTime(900L);
        assertEquals(900L, log.getTime());
    }
}
