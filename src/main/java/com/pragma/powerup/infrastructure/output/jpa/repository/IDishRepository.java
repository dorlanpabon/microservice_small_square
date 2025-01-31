package com.pragma.powerup.infrastructure.output.jpa.repository;

import com.pragma.powerup.infrastructure.output.jpa.entity.DishEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;

public interface IDishRepository extends JpaRepository<DishEntity, Long> {

    Optional<DishEntity> findById(Long dishId);

    void deleteById(Long dishId);

    @Transactional
    @Modifying
    @Query("UPDATE DishEntity d SET d.description = :#{#entity.description}, d.price = :#{#entity.price} WHERE d.id = :#{#entity.id}")
    void updateDish(DishEntity entity);

    Page<DishEntity> findByCategoryId(Long categoryId, Pageable pageable);
}
