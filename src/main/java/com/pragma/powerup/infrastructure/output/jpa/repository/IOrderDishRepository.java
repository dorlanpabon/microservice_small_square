package com.pragma.powerup.infrastructure.output.jpa.repository;

import com.pragma.powerup.infrastructure.output.jpa.entity.OrderDishEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IOrderDishRepository extends JpaRepository<OrderDishEntity, Long> {

    Optional<OrderDishEntity> findById(Long orderdishId);

    void deleteById(Long orderdishId);
}
