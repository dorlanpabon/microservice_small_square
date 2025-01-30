package com.pragma.powerup.infrastructure.output.jpa.mapper;

import com.pragma.powerup.domain.model.Dish;
import com.pragma.powerup.infrastructure.output.jpa.entity.DishEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface DishEntityMapper {

    DishEntity toEntity(Dish dish);

    Dish toDish(DishEntity dishEntity);

    List<Dish> toDishList(List<DishEntity> dishEntityList);

}
