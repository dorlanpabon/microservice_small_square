package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.LogEmployeeResponse;
import com.pragma.powerup.application.dto.LogResponse;
import com.pragma.powerup.application.dto.LogTimeResponse;
import com.pragma.powerup.application.dto.OrderAssignRequest;
import com.pragma.powerup.application.dto.OrderRequest;
import com.pragma.powerup.application.dto.OrderResponse;
import com.pragma.powerup.application.dto.PaginatedResponse;
import com.pragma.powerup.domain.enums.OrderStatusEnum;

import java.util.List;

public interface IOrderHandler {

    PaginatedResponse<OrderResponse> getOrders(int page, int size, String sortDirection, OrderStatusEnum status);

    void saveOrderInOrder(OrderRequest orderRequest);

    List<OrderResponse> getOrderFromOrder();

    OrderResponse getOrderFromOrder(Long orderId);

    void updateOrderInOrder(OrderAssignRequest orderAssignRequest);

    void deleteOrderFromOrder(Long orderId);

    void cancelOrder(Long orderId);

    List<LogResponse> getLogsByOrderId(Long orderId);

    LogTimeResponse getLogsTimeByOrderId(Long orderId);

    List<LogTimeResponse> getLogsTimeByRestaurant();

    List<LogEmployeeResponse> getAverageTimeByEmployee();
}
