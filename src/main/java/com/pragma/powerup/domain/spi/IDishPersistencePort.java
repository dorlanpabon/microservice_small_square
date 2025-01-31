package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.dto.PaginatedModel;
import com.pragma.powerup.domain.model.Dish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IDishPersistencePort {

    void saveDish(Dish dish);

    List<Dish> getAllDish();

    Dish getDishById(Long dishId);

    void updateDish(Dish dish);

    void deleteDish(Long dishId);

    PaginatedModel<Dish> getDishs(int pageNumber, int pageSize, String sortDirection, Long categoryId);

    Page<Dish> getDishs(PageRequest pageRequest);

    Page<Dish> findAll(Pageable pageable);
}
