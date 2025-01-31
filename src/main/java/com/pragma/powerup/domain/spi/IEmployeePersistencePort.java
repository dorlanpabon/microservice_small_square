package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.Employee;
import com.pragma.powerup.domain.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IEmployeePersistencePort {

    void saveEmployee(Employee employee);

    List<Employee> getAllEmployee();

    Employee getEmployeeById(Long employeeId);

    void updateEmployee(Employee employee);

    void deleteEmployee(Long employeeId);

    Page<Employee> getEmployees(int page, int size, boolean ascending);

    Page<Employee> getEmployees(int pageNumber, int pageSize, String sortDirection);

    Page<Employee> getEmployees(PageRequest pageRequest);

    Page<Employee> findAll(Pageable pageable);

    Employee getByEmployeeId(Long userId);
}
