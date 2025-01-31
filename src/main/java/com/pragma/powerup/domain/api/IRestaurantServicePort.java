package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.dto.PaginatedModel;
import com.pragma.powerup.domain.model.Restaurant;

public interface IRestaurantServicePort {

    void saveRestaurant(Restaurant restaurant);

    PaginatedModel<Restaurant> getRestaurants(int page, int size, String sortDirection);
}
