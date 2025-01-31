package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IOrderServicePort;
import com.pragma.powerup.domain.api.IUserServicePort;
import com.pragma.powerup.domain.dto.PaginatedModel;
import com.pragma.powerup.domain.enums.OrderStatusEnum;
import com.pragma.powerup.domain.model.Order;
import com.pragma.powerup.domain.spi.IOrderPersistencePort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;

public class OrderUseCase implements IOrderServicePort {

    private final IOrderPersistencePort orderPersistencePort;
    private final IUserServicePort userServicePort;

    public OrderUseCase(IOrderPersistencePort orderPersistencePort, IUserServicePort userServicePort) {
        this.orderPersistencePort = orderPersistencePort;
        this.userServicePort = userServicePort;
    }

    @Override
    public void saveOrder(Order order) {
        order.setClientId(userServicePort.getUserId());
        order.setDate(LocalDateTime.now());
        order.setStatus(OrderStatusEnum.PENDING.toString());

        System.out.println("order = " + order);

        orderPersistencePort.saveOrder(order);
    }

    @Override
    public List<Order> getAllOrder() {
        return orderPersistencePort.getAllOrder();
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderPersistencePort.getOrderById(orderId);
    }

    @Override
    public void updateOrder(Order order) {
        orderPersistencePort.updateOrder(order);
    }

    @Override
    public void deleteOrder(Long orderId) {
        orderPersistencePort.deleteOrder(orderId);
    }

    @Override
    public Page<Order> getOrders(int page, int size, boolean ascending) {
        return null;
    }

    @Override
    public PaginatedModel<Order> getOrders(int pageNumber, int pageSize, String sortDirection, OrderStatusEnum status) {
        Long userId = userServicePort.getUserId();
        return orderPersistencePort.getOrders(pageNumber, pageSize, sortDirection, status, userId);
    }

}
