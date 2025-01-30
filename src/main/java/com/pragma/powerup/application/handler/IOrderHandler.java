package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.OrderRequest;
import com.pragma.powerup.application.dto.OrderResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IOrderHandler {

    Page<OrderResponse> getOrders(int page, int size, String sortDirection);

    void saveOrderInOrder(OrderRequest orderRequest);

    List<OrderResponse> getOrderFromOrder();

    OrderResponse getOrderFromOrder(Long orderId);

    void updateOrderInOrder(OrderRequest orderRequest);

    void deleteOrderFromOrder(Long orderId);

}
