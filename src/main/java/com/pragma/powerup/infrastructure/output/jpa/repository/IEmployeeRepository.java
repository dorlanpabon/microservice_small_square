package com.pragma.powerup.infrastructure.output.jpa.repository;

import com.pragma.powerup.infrastructure.output.jpa.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IEmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

    Optional<EmployeeEntity> findById(Long employeeId);

    void deleteById(Long employeeId);

    Optional<EmployeeEntity> findByEmployeeId(Long userId);

    List<EmployeeEntity> getEmployeeIdsByRestaurantId(Long id);
}
