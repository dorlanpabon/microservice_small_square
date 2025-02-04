package com.pragma.powerup.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    private Order order;
    private Restaurant restaurant;
    private OrderDish orderDish;

    @BeforeEach
    void setUp() {
        order = new Order();
        order.setId(1L);
        order.setClientId(123L);
        order.setDate(LocalDateTime.of(2023, 12, 1, 12, 0));
        order.setStatus("PENDING");

        restaurant = new Restaurant();
        restaurant.setId(10L);
        restaurant.setName("Gourmet Restaurant");

        order.setRestaurant(restaurant);

        orderDish = new OrderDish();
        orderDish.setId(100L);
        orderDish.setQuantity(2);

        order.setOrderDishes(Collections.singletonList(orderDish));

        order.setChefId(555L);
        order.setCode("ABC123");
    }

    @Test
    void testGetId() {
        assertEquals(1L, order.getId());
    }

    @Test
    void testSetId() {
        order.setId(2L);
        assertEquals(2L, order.getId());
    }

    @Test
    void testGetClientId() {
        assertEquals(123L, order.getClientId());
    }

    @Test
    void testSetClientId() {
        order.setClientId(456L);
        assertEquals(456L, order.getClientId());
    }

    @Test
    void testGetDate() {
        assertEquals(LocalDateTime.of(2023, 12, 1, 12, 0), order.getDate());
    }

    @Test
    void testSetDate() {
        LocalDateTime newDate = LocalDateTime.of(2023, 12, 2, 15, 30);
        order.setDate(newDate);
        assertEquals(newDate, order.getDate());
    }

    @Test
    void testGetStatus() {
        assertEquals("PENDING", order.getStatus());
    }

    @Test
    void testSetStatus() {
        order.setStatus("COMPLETED");
        assertEquals("COMPLETED", order.getStatus());
    }

    @Test
    void testGetRestaurant() {
        assertNotNull(order.getRestaurant());
        assertEquals(10L, order.getRestaurant().getId());
        assertEquals("Gourmet Restaurant", order.getRestaurant().getName());
    }

    @Test
    void testSetRestaurant() {
        Restaurant newRestaurant = new Restaurant();
        newRestaurant.setId(20L);
        newRestaurant.setName("Vegan Bistro");

        order.setRestaurant(newRestaurant);

        assertEquals(20L, order.getRestaurant().getId());
        assertEquals("Vegan Bistro", order.getRestaurant().getName());
    }

    @Test
    void testGetChefId() {
        assertEquals(555L, order.getChefId());
    }

    @Test
    void testSetChefId() {
        order.setChefId(777L);
        assertEquals(777L, order.getChefId());
    }

    @Test
    void testGetOrderDishes() {
        List<OrderDish> dishes = order.getOrderDishes();
        assertNotNull(dishes);
        assertFalse(dishes.isEmpty());
        assertEquals(1, dishes.size());
        assertEquals(100L, dishes.get(0).getId());
    }

    @Test
    void testSetOrderDishes() {
        OrderDish newDish = new OrderDish();
        newDish.setId(101L);
        newDish.setQuantity(3);

        order.setOrderDishes(Collections.singletonList(newDish));

        assertNotNull(order.getOrderDishes());
        assertEquals(1, order.getOrderDishes().size());
        assertEquals(101L, order.getOrderDishes().get(0).getId());
        assertEquals(3, order.getOrderDishes().get(0).getQuantity());
    }

    @Test
    void testGetCode() {
        assertEquals("ABC123", order.getCode());
    }

    @Test
    void testSetCode() {
        order.setCode("XYZ987");
        assertEquals("XYZ987", order.getCode());
    }

    @Test
    void testToString() {
        String result = order.toString();
        assertTrue(result.contains("id=1"));
        assertTrue(result.contains("clientId=123"));
        assertTrue(result.contains("status='PENDING'"));
    }
}
