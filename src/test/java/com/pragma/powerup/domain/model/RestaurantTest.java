package com.pragma.powerup.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class RestaurantTest {

    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        restaurant = new Restaurant();
    }

    @Test
    void shouldSetAndGetId() {
        Long expectedId = 1L;
        restaurant.setId(expectedId);
        assertThat(restaurant.getId()).isEqualTo(expectedId);
    }

    @Test
    void shouldSetAndGetName() {
        String expectedName = "La Pizzeria";
        restaurant.setName(expectedName);
        assertThat(restaurant.getName()).isEqualTo(expectedName);
    }

    @Test
    void shouldSetAndGetAddress() {
        String expectedAddress = "123 Main Street";
        restaurant.setAddress(expectedAddress);
        assertThat(restaurant.getAddress()).isEqualTo(expectedAddress);
    }

    @Test
    void shouldSetAndGetOwnerId() {
        Long expectedOwnerId = 100L;
        restaurant.setOwnerId(expectedOwnerId);
        assertThat(restaurant.getOwnerId()).isEqualTo(expectedOwnerId);
    }

    @Test
    void shouldSetAndGetPhone() {
        String expectedPhone = "+573005698325";
        restaurant.setPhone(expectedPhone);
        assertThat(restaurant.getPhone()).isEqualTo(expectedPhone);
    }

    @Test
    void shouldSetAndGetLogoUrl() {
        String expectedLogoUrl = "http://pragma.com.co/logo.png";
        restaurant.setLogoUrl(expectedLogoUrl);
        assertThat(restaurant.getLogoUrl()).isEqualTo(expectedLogoUrl);
    }

    @Test
    void shouldSetAndGetNit() {
        String expectedNit = "123456789";
        restaurant.setNit(expectedNit);
        assertThat(restaurant.getNit()).isEqualTo(expectedNit);
    }
}
