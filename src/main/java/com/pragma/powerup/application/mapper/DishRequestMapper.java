package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.DishRequest;
import com.pragma.powerup.domain.model.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface DishRequestMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "restaurant", source = "restaurant")
    @Mapping(target = "imageUrl", source = "imageUrl")
    @Mapping(target = "active", source = "active")
    @Mapping(target = "orderDishes", source = "orderDishes")
    Dish toDish(DishRequest dishRequest);
}
