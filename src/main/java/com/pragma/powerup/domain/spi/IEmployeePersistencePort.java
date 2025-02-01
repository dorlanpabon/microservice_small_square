package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.Employee;
import com.pragma.powerup.domain.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IEmployeePersistencePort {

    void saveEmployee(Employee employee);

    Page<Employee> findAll(Pageable pageable);

    Employee getByEmployeeId(Long userId);
}
