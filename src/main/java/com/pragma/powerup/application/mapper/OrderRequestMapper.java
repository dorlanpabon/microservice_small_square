package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.OrderRequest;
import com.pragma.powerup.domain.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface OrderRequestMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "clientId", source = "clientId")
    @Mapping(target = "date", source = "date")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "restaurant", source = "restaurant")
    @Mapping(target = "chefId", source = "chefId")
    @Mapping(target = "orderDishes", source = "orderDishes")
    Order toOrder(OrderRequest orderRequest);
}
