package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IDishServicePort;
import com.pragma.powerup.domain.model.Dish;
import com.pragma.powerup.domain.spi.IDishPersistencePort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public abstract class DishUseCase implements IDishServicePort {

    private final IDishPersistencePort dishPersistencePort;

    public DishUseCase(IDishPersistencePort dishPersistencePort) {
        this.dishPersistencePort = dishPersistencePort;
    }

    @Override
    public void saveDish(Dish dish) {
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
    public void updateDish(Dish dish) {
        dishPersistencePort.updateDish(dish);
    }

    @Override
    public void deleteDish(Long dishId) {
        dishPersistencePort.deleteDish(dishId);
    }

    @Override
    public Page<Dish> getDishs(int pageNumber, int pageSize, String sortDirection) {
        Sort sort = Sort.by("name");
        if ("desc".equalsIgnoreCase(sortDirection)) {
            sort = sort.descending();
        } else {
            sort = sort.ascending();
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        return dishPersistencePort.findAll(pageable);
    }

    //TODO: Add pagination support if needed
    public abstract Page<Dish> getDishs(int page, int size, boolean ascending);
}
