package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.dto.PaginatedModel;
import com.pragma.powerup.domain.model.Dish;

import java.util.List;

public interface IDishPersistencePort {

    void saveDish(Dish dish);

    List<Dish> getAllDish();

    Dish getDishById(Long dishId);

    void updateDish(Dish dish);

    void deleteDish(Long dishId);

    PaginatedModel<Dish> getDishs(int pageNumber, int pageSize, String sortDirection, Long categoryId);

}
