package com.pragma.powerup.infrastructure.output.jpa.adapter;

import com.pragma.powerup.domain.dto.PaginatedModel;
import com.pragma.powerup.domain.model.Dish;
import com.pragma.powerup.domain.model.Restaurant;
import com.pragma.powerup.domain.spi.IDishPersistencePort;
import com.pragma.powerup.infrastructure.output.jpa.entity.DishEntity;
import com.pragma.powerup.infrastructure.output.jpa.entity.RestaurantEntity;
import com.pragma.powerup.infrastructure.output.jpa.mapper.DishEntityMapper;
import com.pragma.powerup.infrastructure.output.jpa.repository.IDishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class DishJpaAdapter implements IDishPersistencePort {

    private final IDishRepository dishRepository;

    private final DishEntityMapper dishEntityMapper;

    @Override
    public void saveDish(Dish dish) {
        dishRepository.save(dishEntityMapper.toEntity(dish));
    }

    @Override
    public List<Dish> getAllDish() {
        List<DishEntity> entityList = dishRepository.findAll();
        if (entityList.isEmpty()) {
            throw new IllegalArgumentException("No Dishs found");
        }
        return dishEntityMapper.toDishList(entityList);
    }

    @Override
    public Dish getDishById(Long dishId) {
        return dishEntityMapper.toDish(dishRepository.findById(dishId)
                .orElseThrow(() -> new IllegalArgumentException("Dish not found")));
    }

    @Override
    public void updateDish(Dish dish) {
        dishRepository.updateDish(dishEntityMapper.toEntity(dish));
    }

    @Override
    public void deleteDish(Long dishId) {
        dishRepository.deleteById(dishId);
    }

    @Override
    public Page<Dish> getDishs(PageRequest pageRequest) {
        Page<DishEntity> entityPage = dishRepository.findAll(pageRequest);
        if (entityPage.isEmpty()) {
            throw new IllegalArgumentException("No Dishs found");
        }
        return entityPage.map(dishEntityMapper::toDish);
    }


    @Override
    public PaginatedModel<Dish> getDishs(int page, int size, String sortDirection, Long categoryId) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), "name");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<DishEntity> dishEntities;

        if (categoryId != null) {
            dishEntities = dishRepository.findByCategoryId(categoryId, pageable);
        } else {
            dishEntities = dishRepository.findAll(pageable);
        }

        List<Dish> content = dishEntities.getContent().stream()
                .map(dishEntityMapper::toDish)
                .collect(Collectors.toList());

        return new PaginatedModel<>(content, dishEntities.getNumber(),
                dishEntities.getTotalPages(), dishEntities.getTotalElements());
    }

    @Override
    public Page<Dish> findAll(Pageable pageable) {
        return dishRepository.findAll(pageable).map(dishEntityMapper::toDish);
    }
}
