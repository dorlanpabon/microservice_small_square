package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.PaginatedResponse;
import com.pragma.powerup.application.dto.RestaurantRequest;
import com.pragma.powerup.application.dto.RestaurantResponse;
import com.pragma.powerup.application.mapper.RestaurantRequestMapper;
import com.pragma.powerup.application.mapper.RestaurantResponseMapper;
import com.pragma.powerup.domain.api.IRestaurantServicePort;
import com.pragma.powerup.domain.dto.PaginatedModel;
import com.pragma.powerup.domain.model.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RestaurantHandler implements IRestaurantHandler {

    private final RestaurantRequestMapper restaurantRequestMapper;
    private final RestaurantResponseMapper restaurantResponseMapper;
    private final IRestaurantServicePort restaurantServicePort;

    @Override
    public void saveRestaurantInRestaurant(RestaurantRequest restaurantRequest) {
        Restaurant restaurant = restaurantRequestMapper.toRestaurant(restaurantRequest);
        restaurantServicePort.saveRestaurant(restaurant);
    }

    @Override
    public PaginatedResponse<RestaurantResponse> getRestaurants(int page, int size, String sortDirection) {
        PaginatedModel<Restaurant> paginatedModel = restaurantServicePort.getRestaurants(page, size, sortDirection);

        List<RestaurantResponse> content = paginatedModel.getContent().stream().map(restaurantResponseMapper::toRestaurantResponse)
                .collect(Collectors.toList());
        return new PaginatedResponse<>(content, paginatedModel.getCurrentPage(),
                paginatedModel.getTotalPages(), paginatedModel.getTotalElements());
    }

}
