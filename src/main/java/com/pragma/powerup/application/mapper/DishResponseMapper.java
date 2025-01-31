package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.DishResponse;
import com.pragma.powerup.domain.model.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface DishResponseMapper {
    @Mapping(target = "dishId", source = "id")
    @Mapping(target = "dishName", source = "name")
    @Mapping(target = "dishDescription", source = "description")
    @Mapping(target = "dishPrice", source = "price")
    @Mapping(target = "dishImageUrl", source = "imageUrl")
    @Mapping(target = "dishActive", source = "active")
    DishResponse toDishResponse(Dish entity);
}
