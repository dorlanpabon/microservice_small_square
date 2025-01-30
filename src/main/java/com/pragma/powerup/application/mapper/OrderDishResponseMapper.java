package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.OrderDishResponse;
import com.pragma.powerup.domain.model.OrderDish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface OrderDishResponseMapper {
    @Mapping(target = "orderdishId", source = "id")
    @Mapping(target = "orderdishOrder", source = "order")
    @Mapping(target = "orderdishDish", source = "dish")
    @Mapping(target = "orderdishQuantity", source = "quantity")
    OrderDishResponse toOrderDishResponse(OrderDish entity);
}
