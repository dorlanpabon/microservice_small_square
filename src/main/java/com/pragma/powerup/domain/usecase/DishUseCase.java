package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IDishServicePort;
import com.pragma.powerup.domain.api.IUserServicePort;
import com.pragma.powerup.domain.constants.DomainConstants;
import com.pragma.powerup.domain.dto.PaginatedModel;
import com.pragma.powerup.domain.exception.DomainException;
import com.pragma.powerup.domain.model.Dish;
import com.pragma.powerup.domain.model.Restaurant;
import com.pragma.powerup.domain.spi.IDishPersistencePort;
import com.pragma.powerup.domain.spi.IRestaurantPersistencePort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public class DishUseCase implements IDishServicePort {

    private final IDishPersistencePort dishPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IUserServicePort userServicePort;

    public DishUseCase(IDishPersistencePort dishPersistencePort, IRestaurantPersistencePort restaurantPersistencePort, IUserServicePort userServicePort) {
        this.dishPersistencePort = dishPersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.userServicePort = userServicePort;
    }

    @Override
    public void saveDish(Dish dish) {
        
        Long userId = userServicePort.getUserId();
        Restaurant restaurant = restaurantPersistencePort.getRestaurantIdByOwnerId(userId);

        if (!restaurantPersistencePort.isOwnerOfRestaurant(userId, restaurant.getId())) {
            throw new DomainException(DomainConstants.NOT_OWNER_MESSAGE);
        }

        if (dish.getName() == null || dish.getName().trim().isEmpty()) {
            throw new DomainException(DomainConstants.DISH_NAME_REQUIRED);
        }
        if (dish.getPrice() == null || dish.getPrice() <= 0) {
            throw new DomainException(DomainConstants.PRICE_REQUIRED);
        }
        if (dish.getDescription() == null || dish.getDescription().trim().isEmpty()) {
            throw new DomainException(DomainConstants.DESCRIPTION_REQUIRED);
        }
        if (dish.getImageUrl() == null || dish.getImageUrl().trim().isEmpty()) {
            throw new DomainException(DomainConstants.IMAGE_URL_REQUIRED);
        }
        if (dish.getCategory() == null || dish.getCategory().getId() == null) {
            throw new DomainException(DomainConstants.CATEGORY_REQUIRED);
        }

        dish.setActive(true);
        dish.setRestaurant(restaurant);

        dishPersistencePort.saveDish(dish);
    }

    @Override
    public List<Dish> getAllDish() {
        return dishPersistencePort.getAllDish();
    }

    @Override
    public Dish getDishById(Long dishId) {
        return dishPersistencePort.getDishById(dishId);
    }

    @Override
    public void updateDish(Dish updatedDish) {
        Dish existingDish = dishPersistencePort.getDishById(updatedDish.getId());
        if (existingDish == null) {
            throw new DomainException(DomainConstants.DISH_NOT_FOUND);
        }

        Long userId = userServicePort.getUserId();
        Long restaurantId = existingDish.getRestaurant().getId();
        if (!restaurantPersistencePort.isOwnerOfRestaurant(userId, restaurantId)) {
            throw new DomainException(DomainConstants.NOT_OWNER_MESSAGE);
        }
        if (updatedDish.getPrice() == null || updatedDish.getPrice() <= 0) {
            throw new DomainException(DomainConstants.PRICE_REQUIRED);
        }
        if (updatedDish.getDescription() == null || updatedDish.getDescription().trim().isEmpty()) {
            throw new DomainException(DomainConstants.DESCRIPTION_REQUIRED);
        }

        dishPersistencePort.updateDish(updatedDish);
    }

    @Override
    public void deleteDish(Long dishId) {
        dishPersistencePort.deleteDish(dishId);
    }


    @Override
    public PaginatedModel<Dish> getDishs(int page, int size, String sortDirection, Long categoryId) {
        return dishPersistencePort.getDishs(page, size, sortDirection, categoryId);
    }

    @Override
    public void toggleDishStatus(Long dishId) {
        Dish dish = dishPersistencePort.getDishById(dishId);
        if (dish == null) {
            throw new DomainException(DomainConstants.DISH_NOT_FOUND);
        }

        Long userId = userServicePort.getUserId();
        Long restaurantId = dish.getRestaurant().getId();
        if (!restaurantPersistencePort.isOwnerOfRestaurant(userId, restaurantId)) {
            throw new DomainException(DomainConstants.NOT_OWNER_MESSAGE);
        }

        dish.setActive(!dish.getActive());
        dishPersistencePort.saveDish(dish);
    }

}
