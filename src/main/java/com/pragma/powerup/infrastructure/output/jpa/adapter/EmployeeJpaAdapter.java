package com.pragma.powerup.infrastructure.output.jpa.adapter;

import com.pragma.powerup.domain.model.Employee;
import com.pragma.powerup.domain.model.Order;
import com.pragma.powerup.domain.spi.IEmployeePersistencePort;
import com.pragma.powerup.infrastructure.output.jpa.entity.EmployeeEntity;
import com.pragma.powerup.infrastructure.output.jpa.mapper.EmployeeEntityMapper;
import com.pragma.powerup.infrastructure.output.jpa.repository.IEmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@RequiredArgsConstructor
public class EmployeeJpaAdapter implements IEmployeePersistencePort {

    private final IEmployeeRepository employeeRepository;

    private final EmployeeEntityMapper employeeEntityMapper;

    @Override
    public void saveEmployee(Employee employee) {
        employeeRepository.save(employeeEntityMapper.toEntity(employee));
    }

    @Override
    public Page<Employee> findAll(Pageable pageable) {
        return employeeRepository.findAll(pageable).map(employeeEntityMapper::toEmployee);
    }

    @Override
    public Employee getByEmployeeId(Long userId) {
        return employeeEntityMapper.toEmployee(employeeRepository.findByEmployeeId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found")));
    }
}
