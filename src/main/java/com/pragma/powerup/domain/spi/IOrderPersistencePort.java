package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.dto.PaginatedModel;
import com.pragma.powerup.domain.enums.OrderStatusEnum;
import com.pragma.powerup.domain.model.Order;

import java.util.List;
import java.util.Optional;

public interface IOrderPersistencePort {

    Order saveOrder(Order order);

    List<Order> getAllOrder();

    Order getOrderById(Long orderId);

    void updateOrder(Order order);

    void deleteOrder(Long orderId);

    PaginatedModel<Order> getOrders(int pageNumber, int pageSize, String sortDirection, OrderStatusEnum status, Long restaurantId);

    Long countByClientIdAndStatus(Long clientId, List<OrderStatusEnum> pending);

    Optional<Order> getOrderByIdAndRestaurantId(Long id, Long restaurantId);

    List<Long> getOrderIdsByRestaurantIdAndStatus(Long id, OrderStatusEnum status);

    List<Long> getOrderIdsByChefIdAndStatus(Long employeeId, OrderStatusEnum orderStatusEnum);
}
