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
    @Mapping(target = "quantity", source = "quantity")
    OrderDish toOrderDish(OrderDishRequest orderdishRequest);
}
