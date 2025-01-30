package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.DishRequest;
import com.pragma.powerup.application.dto.DishResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IDishHandler {

    Page<DishResponse> getDishs(int page, int size, String sortDirection);

    void saveDishInDish(DishRequest dishRequest);

    List<DishResponse> getDishFromDish();

    DishResponse getDishFromDish(Long dishId);

    void updateDishInDish(DishRequest dishRequest);

    void deleteDishFromDish(Long dishId);

}
