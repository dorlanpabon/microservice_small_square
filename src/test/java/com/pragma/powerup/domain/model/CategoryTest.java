package com.pragma.powerup.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    private Category category;
    private Dish dish;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setId(1L);
        category.setName("Fast Food");
        category.setDescription("Burgers, fries, and more");

        dish = new Dish();
        dish.setId(101L);
        dish.setName("Cheeseburger");

        category.setDishes(Collections.singletonList(dish));
    }

    @Test
    void testGetId() {
        assertEquals(1L, category.getId());
    }

    @Test
    void testSetId() {
        category.setId(2L);
        assertEquals(2L, category.getId());
    }

    @Test
    void testGetName() {
        assertEquals("Fast Food", category.getName());
    }

    @Test
    void testSetName() {
        category.setName("Healthy Food");
        assertEquals("Healthy Food", category.getName());
    }

    @Test
    void testGetDescription() {
        assertEquals("Burgers, fries, and more", category.getDescription());
    }

    @Test
    void testSetDescription() {
        category.setDescription("Salads and smoothies");
        assertEquals("Salads and smoothies", category.getDescription());
    }

    @Test
    void testGetDishes() {
        List<Dish> dishes = category.getDishes();
        assertNotNull(dishes);
        assertFalse(dishes.isEmpty());
        assertEquals(1, dishes.size());
        assertEquals("Cheeseburger", dishes.get(0).getName());
    }

    @Test
    void testSetDishes() {
        Dish newDish = new Dish();
        newDish.setId(102L);
        newDish.setName("Veggie Wrap");

        category.setDishes(Collections.singletonList(newDish));

        assertNotNull(category.getDishes());
        assertEquals(1, category.getDishes().size());
        assertEquals("Veggie Wrap", category.getDishes().get(0).getName());
    }
}
