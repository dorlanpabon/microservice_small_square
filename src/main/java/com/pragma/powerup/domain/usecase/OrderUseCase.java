package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IOrderServicePort;
import com.pragma.powerup.domain.model.Order;
import com.pragma.powerup.domain.spi.IOrderPersistencePort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public abstract class OrderUseCase implements IOrderServicePort {

    private final IOrderPersistencePort orderPersistencePort;

    public OrderUseCase(IOrderPersistencePort orderPersistencePort) {
        this.orderPersistencePort = orderPersistencePort;
    }

    @Override
    public void saveOrder(Order order) {
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
    public Page<Order> getOrders(int pageNumber, int pageSize, String sortDirection) {
        Sort sort = Sort.by("name");
        if ("desc".equalsIgnoreCase(sortDirection)) {
            sort = sort.descending();
        } else {
            sort = sort.ascending();
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        return orderPersistencePort.findAll(pageable);
    }

    //TODO: Add pagination support if needed
    public abstract Page<Order> getOrders(int page, int size, boolean ascending);
}
