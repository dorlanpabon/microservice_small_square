package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.Dish;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IDishServicePort {

    void saveDish(Dish dish);

    List<Dish> getAllDish();

    Dish getDishById(Long dishId);

    void updateDish(Dish dish);

    void deleteDish(Long dishId);

    Page<Dish> getDishs(int page, int size, boolean ascending);

    Page<Dish> getDishs(int pageNumber, int pageSize, String sortDirection);
}
