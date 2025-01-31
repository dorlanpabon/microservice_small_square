package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.PaginatedResponse;
import com.pragma.powerup.application.dto.RestaurantRequest;
import com.pragma.powerup.application.dto.RestaurantResponse;
import com.pragma.powerup.domain.model.Restaurant;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IRestaurantHandler {

    void saveRestaurantInRestaurant(RestaurantRequest restaurantRequest);

    PaginatedResponse<RestaurantResponse> getRestaurants(int page, int size, String sortDirection);
}
