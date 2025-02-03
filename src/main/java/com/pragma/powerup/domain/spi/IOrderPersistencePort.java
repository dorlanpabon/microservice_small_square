package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.dto.PaginatedModel;
import com.pragma.powerup.domain.enums.OrderStatusEnum;
import com.pragma.powerup.domain.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IOrderPersistencePort {

    Order saveOrder(Order order);

    List<Order> getAllOrder();

    Order getOrderById(Long orderId);

    void updateOrder(Order order);

    void deleteOrder(Long orderId);

    Page<Order> getOrders(int page, int size, boolean ascending);

    PaginatedModel<Order> getOrders(int pageNumber, int pageSize, String sortDirection, OrderStatusEnum status, Long restaurantId);

    Page<Order> getOrders(PageRequest pageRequest);

    Page<Order> findAll(Pageable pageable);

    Long countByClientIdAndStatus(Long clientId, List<OrderStatusEnum> pending);

    Optional<Order> getOrderByIdAndRestaurantId(Long id, Long restaurantId);

    List<Long> getOrderIdsByRestaurantIdAndStatus(Long id, OrderStatusEnum status);

    List<Long> getOrderIdsByChefIdAndStatus(Long employeeId, OrderStatusEnum orderStatusEnum);
}
