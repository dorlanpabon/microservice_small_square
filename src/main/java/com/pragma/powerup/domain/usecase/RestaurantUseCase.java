package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IRestaurantServicePort;
import com.pragma.powerup.domain.api.IUserServicePort;
import com.pragma.powerup.domain.constants.DomainConstants;
import com.pragma.powerup.domain.exception.DomainException;
import com.pragma.powerup.domain.model.Restaurant;
import com.pragma.powerup.domain.spi.IRestaurantPersistencePort;

public class RestaurantUseCase implements IRestaurantServicePort {

    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IUserServicePort userServicePort;


    public RestaurantUseCase(IRestaurantPersistencePort restaurantPersistencePort, IUserServicePort userServicePort) {
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.userServicePort = userServicePort;
    }

    @Override
    public void saveRestaurant(Restaurant restaurant) {

        if (!userServicePort.isOwner(restaurant.getOwnerId())) {
            throw new DomainException(DomainConstants.USER_NOT_OWNER);
        }

        if (restaurantPersistencePort.existsRestaurant(restaurant.getNit())) {
            throw new DomainException(DomainConstants.RESTAURANT_ALREADY_EXISTS);
        }

        if (!restaurant.getName().matches(DomainConstants.NAME_REGEX)) {
            throw new DomainException(DomainConstants.NAME_INVALID_FORMAT);
        }

        if (!restaurant.getNit().matches(DomainConstants.NUMERIC_REGEX)) {
            throw new DomainException(DomainConstants.NIT_INVALID_FORMAT);
        }

        if (!restaurant.getPhone().matches(DomainConstants.PHONE_REGEX)) {
            throw new DomainException(DomainConstants.PHONE_INVALID_FORMAT);
        }

        restaurantPersistencePort.saveRestaurant(restaurant);
    }

}
