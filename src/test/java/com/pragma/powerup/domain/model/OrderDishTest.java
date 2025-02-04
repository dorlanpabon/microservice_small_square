package com.pragma.powerup.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderDishTest {

    private OrderDish orderDish;
    private Order order;
    private Dish dish;

    @BeforeEach
    void setUp() {
        orderDish = new OrderDish();
        orderDish.setId(100L);

        order = new Order();
        order.setId(1L);
        order.setCode("ABC123");

        dish = new Dish();
        dish.setId(10L);
        dish.setName("Pasta Carbonara");

        orderDish.setOrder(order);
        orderDish.setDish(dish);
        orderDish.setQuantity(2);
    }

    @Test
    void testGetId() {
        assertEquals(100L, orderDish.getId());
    }

    @Test
    void testSetId() {
        orderDish.setId(101L);
        assertEquals(101L, orderDish.getId());
    }

    @Test
    void testGetOrder() {
        assertNotNull(orderDish.getOrder());
        assertEquals(1L, orderDish.getOrder().getId());
        assertEquals("ABC123", orderDish.getOrder().getCode());
    }

    @Test
    void testSetOrder() {
        Order newOrder = new Order();
        newOrder.setId(2L);
        newOrder.setCode("XYZ789");

        orderDish.setOrder(newOrder);

        assertEquals(2L, orderDish.getOrder().getId());
        assertEquals("XYZ789", orderDish.getOrder().getCode());
    }

    @Test
    void testGetDish() {
        assertNotNull(orderDish.getDish());
        assertEquals(10L, orderDish.getDish().getId());
        assertEquals("Pasta Carbonara", orderDish.getDish().getName());
    }

    @Test
    void testSetDish() {
        Dish newDish = new Dish();
        newDish.setId(11L);
        newDish.setName("Pizza Margherita");

        orderDish.setDish(newDish);

        assertEquals(11L, orderDish.getDish().getId());
        assertEquals("Pizza Margherita", orderDish.getDish().getName());
    }

    @Test
    void testGetQuantity() {
        assertEquals(2, orderDish.getQuantity());
    }

    @Test
    void testSetQuantity() {
        orderDish.setQuantity(3);
        assertEquals(3, orderDish.getQuantity());
    }

    @Test
    void testToString() {
        String result = orderDish.toString();
        assertTrue(result.contains("id=100"));
        assertTrue(result.contains("order="));
        assertTrue(result.contains("dish="));
        assertTrue(result.contains("quantity=2"));
    }
}
