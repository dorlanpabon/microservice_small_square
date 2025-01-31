package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.DishRequest;
import com.pragma.powerup.application.dto.DishResponse;
import com.pragma.powerup.application.dto.DishUpdateRequest;
import com.pragma.powerup.application.dto.PaginatedResponse;
import com.pragma.powerup.application.dto.RestaurantResponse;
import com.pragma.powerup.application.mapper.DishRequestMapper;
import com.pragma.powerup.application.mapper.DishResponseMapper;
import com.pragma.powerup.domain.api.IDishServicePort;
import com.pragma.powerup.domain.dto.PaginatedModel;
import com.pragma.powerup.domain.model.Dish;
import com.pragma.powerup.domain.model.Restaurant;
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
    public PaginatedResponse<DishResponse> getDishs(int page, int size, String sortDirection, Long categoryId) {
        PaginatedModel<Dish> paginatedModel = dishServicePort.getDishs(page, size, sortDirection, categoryId);

        List<DishResponse> content = paginatedModel.getContent().stream().map(dishResponseMapper::toDishResponse)
                .collect(Collectors.toList());
        return new PaginatedResponse<>(content, paginatedModel.getCurrentPage(),
                paginatedModel.getTotalPages(), paginatedModel.getTotalElements());
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
    public void updateDishInDish(Long dishId, DishUpdateRequest dishUpdateRequest) {
        Dish dish = dishRequestMapper.toDish(dishId, dishUpdateRequest);
        dishServicePort.updateDish(dish);
    }

    @Override
    public void deleteDishFromDish(Long dishId) {
        dishServicePort.deleteDish(dishId);
    }

    @Override
    public void toggleDishStatus(Long dishId) {
        dishServicePort.toggleDishStatus(dishId);
    }

}
