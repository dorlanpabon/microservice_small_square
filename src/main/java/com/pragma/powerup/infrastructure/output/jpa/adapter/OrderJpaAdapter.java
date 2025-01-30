package com.pragma.powerup.infrastructure.output.jpa.adapter;

import com.pragma.powerup.domain.model.Order;
import com.pragma.powerup.domain.spi.IOrderPersistencePort;
import com.pragma.powerup.infrastructure.output.jpa.entity.OrderEntity;
import com.pragma.powerup.infrastructure.output.jpa.mapper.OrderEntityMapper;
import com.pragma.powerup.infrastructure.output.jpa.repository.IOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@RequiredArgsConstructor
public class OrderJpaAdapter implements IOrderPersistencePort {

    private final IOrderRepository orderRepository;

    private final OrderEntityMapper orderEntityMapper;

    @Override
    public void saveOrder(Order order) {
        //if (orderRepository.findByName(order.getName()).isPresent()) {
        //    throw new IllegalArgumentException("Order already exists");
        //}
        orderRepository.save(orderEntityMapper.toEntity(order));
    }

    @Override
    public List<Order> getAllOrder() {
        List<OrderEntity> entityList = orderRepository.findAll();
        if (entityList.isEmpty()) {
            throw new IllegalArgumentException("No Orders found");
        }
        return orderEntityMapper.toOrderList(entityList);
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderEntityMapper.toOrder(orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found")));
    }

    @Override
    public void updateOrder(Order order) {
        //if (orderRepository.findByName(order.getName()).isPresent()) {
        //    throw new IllegalArgumentException("Order already exists");
        //}
        orderRepository.save(orderEntityMapper.toEntity(order));
    }

    @Override
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    @Override
    public Page<Order> getOrders(PageRequest pageRequest) {
        Page<OrderEntity> entityPage = orderRepository.findAll(pageRequest);
        if (entityPage.isEmpty()) {
            throw new IllegalArgumentException("No Orders found");
        }
        return entityPage.map(orderEntityMapper::toOrder);
    }

    @Override
    public Page<Order> getOrders(int page, int size, boolean ascending) {
        Pageable pageable = PageRequest.of(page, size, ascending ? Sort.by("id").ascending() : Sort.by("id").descending());
        return orderRepository.findAll(pageable).map(orderEntityMapper::toOrder);
    }

    @Override
    public Page<Order> getOrders(int pageNumber, int pageSize, String sortDirection) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortDirection.equals("asc") ? Sort.by("id").ascending() : Sort.by("id").descending());
        return orderRepository.findAll(pageable).map(orderEntityMapper::toOrder);
    }

    @Override
    public Page<Order> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable).map(orderEntityMapper::toOrder);
    }
}
