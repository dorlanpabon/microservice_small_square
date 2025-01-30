package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.DishRequest;
import com.pragma.powerup.application.dto.DishResponse;
import com.pragma.powerup.application.mapper.DishRequestMapper;
import com.pragma.powerup.application.mapper.DishResponseMapper;
import com.pragma.powerup.domain.api.IDishServicePort;
import com.pragma.powerup.domain.model.Dish;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DishHandler implements IDishHandler {

    private final DishRequestMapper dishRequestMapper;
    private final DishResponseMapper dishResponseMapper;
    private final IDishServicePort dishServicePort;

    @Override
    public Page<DishResponse> getDishs(int page, int size, String sortDirection) {
        return dishServicePort.getDishs(page, size, sortDirection)
                .map(dishResponseMapper::toDishResponse);
    }

    @Override
    public void saveDishInDish(DishRequest dishRequest) {
        Dish dish = dishRequestMapper.toDish(dishRequest);
        dishServicePort.saveDish(dish);
    }

    @Override
    public List<DishResponse> getDishFromDish() {
        return dishServicePort.getAllDish().stream()
                .map(dishResponseMapper::toDishResponse)
                .collect(Collectors.toList());
    }

    @Override
    public DishResponse getDishFromDish(Long dishId) {
        Dish dish = dishServicePort.getDishById(dishId);
        return dishResponseMapper.toDishResponse(dish);
    }

    @Override
    public void updateDishInDish(DishRequest dishRequest) {
        Dish dish = dishRequestMapper.toDish(dishRequest);
        dishServicePort.updateDish(dish);
    }

    @Override
    public void deleteDishFromDish(Long dishId) {
        dishServicePort.deleteDish(dishId);
    }

}
