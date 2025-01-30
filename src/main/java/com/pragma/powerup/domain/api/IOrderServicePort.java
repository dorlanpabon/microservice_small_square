package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.Order;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IOrderServicePort {

    void saveOrder(Order order);

    List<Order> getAllOrder();

    Order getOrderById(Long orderId);

    void updateOrder(Order order);

    void deleteOrder(Long orderId);

    Page<Order> getOrders(int page, int size, boolean ascending);

    Page<Order> getOrders(int pageNumber, int pageSize, String sortDirection);
}
