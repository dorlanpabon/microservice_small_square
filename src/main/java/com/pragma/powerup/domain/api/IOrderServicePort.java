package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.dto.PaginatedModel;
import com.pragma.powerup.domain.enums.OrderStatusEnum;
import com.pragma.powerup.domain.model.Log;
import com.pragma.powerup.domain.model.Order;
import org.springframework.data.domain.Page;

import java.util.Arrays;
import java.util.List;

public interface IOrderServicePort {

    void saveOrder(Order order);

    List<Order> getAllOrder();

    Order getOrderById(Long orderId);

    void updateOrder(Order order);

    void deleteOrder(Long orderId);

    Page<Order> getOrders(int page, int size, boolean ascending);

    PaginatedModel<Order> getOrders(int pageNumber, int pageSize, String sortDirection, OrderStatusEnum status);

    void cancelOrder(Long orderId);

    List<Log> getLogsByOrderId(Long orderId);

    Long getLogsTimeByOrderId(Long orderId);

    List<Log> getLogsTimeByRestaurant();

    List<Log> getAverageTimeByEmployee();
}
