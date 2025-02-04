package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.OrderResponse;
import com.pragma.powerup.domain.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface OrderResponseMapper {
    @Mapping(target = "orderId", source = "id")
    @Mapping(target = "orderClientId", source = "clientId")
    @Mapping(target = "orderDate", source = "date")
    @Mapping(target = "orderStatus", source = "status")
//    @Mapping(target = "orderRestaurant", source = "restaurant")
    @Mapping(target = "orderChefId", source = "chefId")
//    @Mapping(target = "orderOrderDishes", source = "orderDishes")
    OrderResponse toOrderResponse(Order entity);
}
