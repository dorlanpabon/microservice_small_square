package com.pragma.powerup.infrastructure.output.jpa.mapper;

import com.pragma.powerup.domain.model.Order;
import com.pragma.powerup.infrastructure.output.jpa.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface OrderEntityMapper {

    OrderEntity toEntity(Order order);

    Order toOrder(OrderEntity orderEntity);

    List<Order> toOrderList(List<OrderEntity> orderEntityList);

}
