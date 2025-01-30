package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.OrderDishRequest;
import com.pragma.powerup.domain.model.OrderDish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface OrderDishRequestMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "order", source = "order")
    @Mapping(target = "dish", source = "dish")
    @Mapping(target = "quantity", source = "quantity")
    OrderDish toOrderDish(OrderDishRequest orderdishRequest);
}
