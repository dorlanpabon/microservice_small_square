package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IOrderPersistencePort {

    void saveOrder(Order order);

    List<Order> getAllOrder();

    Order getOrderById(Long orderId);

    void updateOrder(Order order);

    void deleteOrder(Long orderId);

    Page<Order> getOrders(int page, int size, boolean ascending);

    Page<Order> getOrders(int pageNumber, int pageSize, String sortDirection);

    Page<Order> getOrders(PageRequest pageRequest);

    Page<Order> findAll(Pageable pageable);
}
