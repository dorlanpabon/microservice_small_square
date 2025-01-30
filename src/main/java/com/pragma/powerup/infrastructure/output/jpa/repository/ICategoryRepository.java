package com.pragma.powerup.infrastructure.output.jpa.repository;

import com.pragma.powerup.infrastructure.output.jpa.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICategoryRepository extends JpaRepository<CategoryEntity, Long> {

    Optional<CategoryEntity> findById(Long categoryId);

    void deleteById(Long categoryId);
}
