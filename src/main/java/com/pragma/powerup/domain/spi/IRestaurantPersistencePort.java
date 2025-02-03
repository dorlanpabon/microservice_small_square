package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.dto.PaginatedModel;
import com.pragma.powerup.domain.model.Restaurant;

public interface IRestaurantPersistencePort {

    void saveRestaurant(Restaurant restaurant);

    boolean existsRestaurant(String nit);

    boolean isOwnerOfRestaurant(Long ownerId, Long restaurantId);

    PaginatedModel<Restaurant> getRestaurants(int page, int size, String sortDirection);

    Restaurant getByOwnerId(Long ownerId);

    Restaurant getRestaurantIdByOwnerId(Long userId);
}
