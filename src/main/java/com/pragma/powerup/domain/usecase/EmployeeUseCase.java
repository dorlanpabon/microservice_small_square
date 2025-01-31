package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IEmployeeServicePort;
import com.pragma.powerup.domain.constants.DomainConstants;
import com.pragma.powerup.domain.exception.DomainException;
import com.pragma.powerup.domain.model.Employee;
import com.pragma.powerup.domain.model.Restaurant;
import com.pragma.powerup.domain.spi.IEmployeePersistencePort;
import com.pragma.powerup.domain.spi.IRestaurantPersistencePort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public class EmployeeUseCase implements IEmployeeServicePort {

    private final IEmployeePersistencePort employeePersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;

    public EmployeeUseCase(IEmployeePersistencePort employeePersistencePort, IRestaurantPersistencePort restaurantPersistencePort) {
        this.employeePersistencePort = employeePersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
    }

    @Override
    public void saveEmployee(Employee employee) {
        Restaurant restaurant = restaurantPersistencePort.getByOwnerId(employee.getOwnerId());
        if (restaurant == null) {
            throw new DomainException(DomainConstants.RESTAURANT_NOT_FOUND);
        }
        employee.setRestaurant(restaurant);

        employeePersistencePort.saveEmployee(employee);
    }

    @Override
    public List<Employee> getAllEmployee() {
        return employeePersistencePort.getAllEmployee();
    }

    @Override
    public Employee getEmployeeById(Long employeeId) {
        return employeePersistencePort.getEmployeeById(employeeId);
    }

    @Override
    public void updateEmployee(Employee employee) {
        employeePersistencePort.updateEmployee(employee);
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        employeePersistencePort.deleteEmployee(employeeId);
    }

    @Override
    public Page<Employee> getEmployees(int page, int size, boolean ascending) {
        return null;
    }

    @Override
    public Page<Employee> getEmployees(int pageNumber, int pageSize, String sortDirection) {
        Sort sort = Sort.by("name");
        if ("desc".equalsIgnoreCase(sortDirection)) {
            sort = sort.descending();
        } else {
            sort = sort.ascending();
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        return employeePersistencePort.findAll(pageable);
    }
}
