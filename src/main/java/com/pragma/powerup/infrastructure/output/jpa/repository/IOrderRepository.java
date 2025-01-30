package com.pragma.powerup.infrastructure.output.jpa.repository;

import com.pragma.powerup.infrastructure.output.jpa.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IOrderRepository extends JpaRepository<OrderEntity, Long> {

    Optional<OrderEntity> findById(Long orderId);

    void deleteById(Long orderId);
}
