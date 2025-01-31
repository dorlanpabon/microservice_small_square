package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.OrderDishRequest;
import com.pragma.powerup.application.dto.OrderRequest;
import com.pragma.powerup.domain.model.Dish;
import com.pragma.powerup.domain.model.Order;
import com.pragma.powerup.domain.model.OrderDish;
import com.pragma.powerup.domain.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface OrderRequestMapper {
    @Mapping(target = "restaurant", source = "restaurantId")
    @Mapping(target = "orderDishes", source = "orderDishes")
    Order toOrder(OrderRequest orderRequest);

    default Restaurant toRestaurant(Long restaurantId) {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);
        return restaurant;
    }

    default List<OrderDish> toOrderDish(List<OrderDishRequest> orderDishesRequest) {
        return orderDishesRequest.stream().map(orderDishRequest -> {
            Dish dish = new Dish();
            dish.setId(orderDishRequest.getDishId());

            OrderDish orderDish = new OrderDish();
            orderDish.setDish(dish);
            orderDish.setQuantity(orderDishRequest.getQuantity());

            return orderDish;
        }).collect(Collectors.toList());
    }
}
