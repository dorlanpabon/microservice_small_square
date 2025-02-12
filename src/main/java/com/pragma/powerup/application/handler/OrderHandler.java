package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.OrderRequest;
import com.pragma.powerup.application.dto.OrderResponse;
import com.pragma.powerup.application.mapper.OrderRequestMapper;
import com.pragma.powerup.application.mapper.OrderResponseMapper;
import com.pragma.powerup.domain.api.IOrderServicePort;
import com.pragma.powerup.domain.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderHandler implements IOrderHandler {

    private final OrderRequestMapper orderRequestMapper;
    private final OrderResponseMapper orderResponseMapper;
    private final IOrderServicePort orderServicePort;

    @Override
    public Page<OrderResponse> getOrders(int page, int size, String sortDirection) {
        return orderServicePort.getOrders(page, size, sortDirection)
                .map(orderResponseMapper::toOrderResponse);
    }

    @Override
    public void saveOrderInOrder(OrderRequest orderRequest) {
        Order order = orderRequestMapper.toOrder(orderRequest);
        orderServicePort.saveOrder(order);
    }

    @Override
    public List<OrderResponse> getOrderFromOrder() {
        return orderServicePort.getAllOrder().stream()
                .map(orderResponseMapper::toOrderResponse)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponse getOrderFromOrder(Long orderId) {
        Order order = orderServicePort.getOrderById(orderId);
        return orderResponseMapper.toOrderResponse(order);
    }

    @Override
    public void updateOrderInOrder(OrderRequest orderRequest) {
        Order order = orderRequestMapper.toOrder(orderRequest);
        orderServicePort.updateOrder(order);
    }

    @Override
    public void deleteOrderFromOrder(Long orderId) {
        orderServicePort.deleteOrder(orderId);
    }

}
