package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.Employee;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IEmployeeServicePort {

    void saveEmployee(Employee employee);

    List<Employee> getAllEmployee();

    Employee getEmployeeById(Long employeeId);

    void updateEmployee(Employee employee);

    void deleteEmployee(Long employeeId);

    Page<Employee> getEmployees(int page, int size, boolean ascending);

    Page<Employee> getEmployees(int pageNumber, int pageSize, String sortDirection);
}
