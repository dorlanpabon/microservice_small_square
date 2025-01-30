package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.RestaurantRequest;
import com.pragma.powerup.application.dto.RestaurantResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IRestaurantHandler {

    void saveRestaurantInRestaurant(RestaurantRequest restaurantRequest);

}
