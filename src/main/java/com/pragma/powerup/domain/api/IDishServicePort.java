package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.dto.PaginatedModel;
import com.pragma.powerup.domain.model.Dish;

import java.util.List;

public interface IDishServicePort {

    void saveDish(Dish dish);

    List<Dish> getAllDish();

    Dish getDishById(Long dishId);

    void updateDish(Dish dish);

    void deleteDish(Long dishId);

    PaginatedModel<Dish> getDishs(int page, int size, String ascending, Long categoryId);

    void toggleDishStatus(Long dishId);
}
