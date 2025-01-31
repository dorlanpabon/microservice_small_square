package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.DishRequest;
import com.pragma.powerup.application.dto.DishUpdateRequest;
import com.pragma.powerup.domain.model.Category;
import com.pragma.powerup.domain.model.Dish;
import com.pragma.powerup.domain.model.Restaurant;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface DishRequestMapper {

    @Mapping(target = "name", source = "name")
    @Mapping(target = "category", source = "categoryId")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "imageUrl", source = "imageUrl")
    @Mapping(target = "restaurant", source = "restaurantId")
    Dish toDish(DishRequest dishRequest);

    @Mapping(target = "id", expression  = "java(dishId)")
    @Mapping(target = "description", expression = "java(dishUpdateRequest.getDescription())")
    @Mapping(target = "price", expression = "java(dishUpdateRequest.getPrice())")
    Dish toDish(Long dishId, DishUpdateRequest dishUpdateRequest);


    default Category toCategory(Long categoryId) {
        Category category = new Category();
        category.setId(categoryId);
        return category;
    }

    default Restaurant toRestaurant(Long restaurantId) {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);
        return restaurant;
    }
}
