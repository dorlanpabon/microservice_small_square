package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.OrderDishRequest;
import com.pragma.powerup.application.dto.OrderDishResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IOrderDishHandler {

    Page<OrderDishResponse> getOrderDishs(int page, int size, String sortDirection);

    void saveOrderDishInOrderDish(OrderDishRequest orderdishRequest);

    List<OrderDishResponse> getOrderDishFromOrderDish();

    OrderDishResponse getOrderDishFromOrderDish(Long orderdishId);

    void updateOrderDishInOrderDish(OrderDishRequest orderdishRequest);

    void deleteOrderDishFromOrderDish(Long orderdishId);

}
