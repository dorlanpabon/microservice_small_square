package com.pragma.powerup.infrastructure.output.jpa.repository;

import com.pragma.powerup.infrastructure.output.jpa.entity.DishEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IDishRepository extends JpaRepository<DishEntity, Long> {

    Optional<DishEntity> findById(Long dishId);

    void deleteById(Long dishId);
}
