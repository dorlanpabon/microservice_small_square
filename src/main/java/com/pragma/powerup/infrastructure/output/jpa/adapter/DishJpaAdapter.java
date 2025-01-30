package com.pragma.powerup.infrastructure.output.jpa.adapter;

import com.pragma.powerup.domain.model.Dish;
import com.pragma.powerup.domain.spi.IDishPersistencePort;
import com.pragma.powerup.infrastructure.output.jpa.entity.DishEntity;
import com.pragma.powerup.infrastructure.output.jpa.mapper.DishEntityMapper;
import com.pragma.powerup.infrastructure.output.jpa.repository.IDishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@RequiredArgsConstructor
public class DishJpaAdapter implements IDishPersistencePort {

    private final IDishRepository dishRepository;

    private final DishEntityMapper dishEntityMapper;

    @Override
    public void saveDish(Dish dish) {
        //if (dishRepository.findByName(dish.getName()).isPresent()) {
        //    throw new IllegalArgumentException("Dish already exists");
        //}
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
        //if (dishRepository.findByName(dish.getName()).isPresent()) {
        //    throw new IllegalArgumentException("Dish already exists");
        //}
        dishRepository.save(dishEntityMapper.toEntity(dish));
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
    public Page<Dish> getDishs(int page, int size, boolean ascending) {
        Pageable pageable = PageRequest.of(page, size, ascending ? Sort.by("id").ascending() : Sort.by("id").descending());
        return dishRepository.findAll(pageable).map(dishEntityMapper::toDish);
    }

    @Override
    public Page<Dish> getDishs(int pageNumber, int pageSize, String sortDirection) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortDirection.equals("asc") ? Sort.by("id").ascending() : Sort.by("id").descending());
        return dishRepository.findAll(pageable).map(dishEntityMapper::toDish);
    }

    @Override
    public Page<Dish> findAll(Pageable pageable) {
        return dishRepository.findAll(pageable).map(dishEntityMapper::toDish);
    }
}
