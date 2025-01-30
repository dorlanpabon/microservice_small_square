package com.pragma.powerup.infrastructure.output.jpa.mapper;

import com.pragma.powerup.domain.model.OrderDish;
import com.pragma.powerup.infrastructure.output.jpa.entity.OrderDishEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface OrderDishEntityMapper {

    OrderDishEntity toEntity(OrderDish orderdish);

    OrderDish toOrderDish(OrderDishEntity orderdishEntity);

    List<OrderDish> toOrderDishList(List<OrderDishEntity> orderdishEntityList);

}
