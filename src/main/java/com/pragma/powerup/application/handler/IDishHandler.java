package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.DishRequest;
import com.pragma.powerup.application.dto.DishResponse;
import com.pragma.powerup.application.dto.DishUpdateRequest;
import com.pragma.powerup.application.dto.PaginatedResponse;

import java.util.List;

public interface IDishHandler {

    PaginatedResponse<DishResponse> getDishs(int page, int size, String sortDirection, Long categoryId);

    void saveDishInDish(DishRequest dishRequest);

    List<DishResponse> getDishFromDish();

    DishResponse getDishFromDish(Long dishId);

    void updateDishInDish(Long dishId, DishUpdateRequest dishUpdateRequest);

    void deleteDishFromDish(Long dishId);

    void toggleDishStatus(Long dishId);
}
