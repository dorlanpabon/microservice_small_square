package com.pragma.powerup.infrastructure.output.jpa.repository;

import com.pragma.powerup.domain.enums.OrderStatusEnum;
import com.pragma.powerup.domain.model.Order;
import com.pragma.powerup.infrastructure.output.jpa.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface IOrderRepository extends JpaRepository<OrderEntity, Long> {

    Optional<OrderEntity> findById(Long orderId);

    void deleteById(Long orderId);

    Page<OrderEntity> findAllByStatus(OrderStatusEnum status, Pageable pageable);

    Page<OrderEntity> findAllByStatusAndRestaurantId(OrderStatusEnum status, Long restaurantId, Pageable pageable);

    Long countByClientIdAndStatusIn(Long clientId, List<OrderStatusEnum> pending);

    Optional<OrderEntity> findByIdAndRestaurantId(Long id, Long restaurantId);

    List<OrderEntity> findIdsByRestaurantIdAndStatus(Long restaurant_id, OrderStatusEnum status);

    Optional<OrderEntity> findIdsByChefIdAndStatus(Long employeeId, OrderStatusEnum orderStatusEnum);
}
