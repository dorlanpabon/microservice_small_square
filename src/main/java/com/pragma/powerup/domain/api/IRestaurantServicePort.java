package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.Restaurant;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IRestaurantServicePort {

    void saveRestaurant(Restaurant restaurant);

}
