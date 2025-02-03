package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.PaginatedResponse;
import com.pragma.powerup.application.dto.RestaurantRequest;
import com.pragma.powerup.application.dto.RestaurantResponse;

public interface IRestaurantHandler {

    void saveRestaurantInRestaurant(RestaurantRequest restaurantRequest);

    PaginatedResponse<RestaurantResponse> getRestaurants(int page, int size, String sortDirection);
}
