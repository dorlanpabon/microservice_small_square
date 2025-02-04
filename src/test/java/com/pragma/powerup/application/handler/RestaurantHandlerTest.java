package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.PaginatedResponse;
import com.pragma.powerup.application.dto.RestaurantRequest;
import com.pragma.powerup.application.dto.RestaurantResponse;
import com.pragma.powerup.application.mapper.RestaurantRequestMapper;
import com.pragma.powerup.application.mapper.RestaurantResponseMapper;
import com.pragma.powerup.domain.api.IRestaurantServicePort;
import com.pragma.powerup.domain.dto.PaginatedModel;
import com.pragma.powerup.domain.model.Restaurant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.*;

class RestaurantHandlerTest {
    @Mock
    RestaurantRequestMapper restaurantRequestMapper;
    @Mock
    RestaurantResponseMapper restaurantResponseMapper;
    @Mock
    IRestaurantServicePort restaurantServicePort;
    @InjectMocks
    RestaurantHandler restaurantHandler;
    @Mock
    Restaurant restaurant;
    @Mock
    RestaurantRequest restaurantRequest;
    @Mock
    RestaurantResponse restaurantResponse;
    @Mock
    PaginatedModel<Restaurant> paginatedModel;
    @Mock
    PaginatedResponse<RestaurantResponse> paginatedResponse;
    @Mock
    List<Restaurant> listRestaurant;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setOwnerId(1L);
        restaurant.setName("name");
        restaurant.setNit("123456789");
        restaurant.setAddress("address");
        restaurant.setLogoUrl("http://example.com/logo.png");

        listRestaurant = List.of(restaurant);

        restaurantRequest = new RestaurantRequest();
        restaurantRequest.setName("name");
        restaurantRequest.setNit("123456789");
        restaurantRequest.setAddress("address");
        restaurantRequest.setLogoUrl("http://example.com/logo.png");
        restaurantRequest.setOwnerId(1L);

        restaurantResponse = new RestaurantResponse();
        restaurantResponse.setRestaurantName("name");
        restaurantResponse.setRestaurantLogoUrl("http://example.com/logo.png");

        paginatedResponse = new PaginatedResponse<>(List.of(restaurantResponse), 0, 0, 0L);

        paginatedModel = new PaginatedModel<Restaurant>(listRestaurant, 0, 0, 0L);
    }

    @Test
    void testSaveRestaurantInRestaurant() {
        when(restaurantRequestMapper.toRestaurant(any(RestaurantRequest.class))).thenReturn(restaurant);

        restaurantHandler.saveRestaurantInRestaurant(new RestaurantRequest("name", "address", Long.valueOf(1), "phone", "logoUrl", "nit"));
        verify(restaurantServicePort).saveRestaurant(any(Restaurant.class));
    }

    @Test
    void testGetRestaurants() {
        when(restaurantServicePort.getRestaurants(anyInt(), anyInt(), anyString())).thenReturn(paginatedModel);
        when(restaurantResponseMapper.toRestaurantResponse(any(Restaurant.class))).thenReturn(restaurantResponse);

        PaginatedResponse<RestaurantResponse> result = restaurantHandler.getRestaurants(0, 10, "asc");
        Assertions.assertNotNull(result);
    }
}

