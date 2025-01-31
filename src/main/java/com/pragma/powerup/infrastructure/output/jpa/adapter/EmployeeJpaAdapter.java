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
        //if (employeeRepository.findByName(employee.getName()).isPresent()) {
        //    throw new IllegalArgumentException("Employee already exists");
        //}
        employeeRepository.save(employeeEntityMapper.toEntity(employee));
    }

    @Override
    public List<Employee> getAllEmployee() {
        List<EmployeeEntity> entityList = employeeRepository.findAll();
        if (entityList.isEmpty()) {
            throw new IllegalArgumentException("No Employees found");
        }
        return employeeEntityMapper.toEmployeeList(entityList);
    }

    @Override
    public Employee getEmployeeById(Long employeeId) {
        return employeeEntityMapper.toEmployee(employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found")));
    }

    @Override
    public void updateEmployee(Employee employee) {
        //if (employeeRepository.findByName(employee.getName()).isPresent()) {
        //    throw new IllegalArgumentException("Employee already exists");
        //}
        employeeRepository.save(employeeEntityMapper.toEntity(employee));
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        employeeRepository.deleteById(employeeId);
    }

    @Override
    public Page<Employee> getEmployees(PageRequest pageRequest) {
        Page<EmployeeEntity> entityPage = employeeRepository.findAll(pageRequest);
        if (entityPage.isEmpty()) {
            throw new IllegalArgumentException("No Employees found");
        }
        return entityPage.map(employeeEntityMapper::toEmployee);
    }

    @Override
    public Page<Employee> getEmployees(int page, int size, boolean ascending) {
        Pageable pageable = PageRequest.of(page, size, ascending ? Sort.by("id").ascending() : Sort.by("id").descending());
        return employeeRepository.findAll(pageable).map(employeeEntityMapper::toEmployee);
    }

    @Override
    public Page<Employee> getEmployees(int pageNumber, int pageSize, String sortDirection) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortDirection.equals("asc") ? Sort.by("id").ascending() : Sort.by("id").descending());
        return employeeRepository.findAll(pageable).map(employeeEntityMapper::toEmployee);
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
