package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IEmployeeServicePort;
import com.pragma.powerup.domain.constants.DomainConstants;
import com.pragma.powerup.domain.exception.DomainException;
import com.pragma.powerup.domain.model.Employee;
import com.pragma.powerup.domain.model.Restaurant;
import com.pragma.powerup.domain.spi.IEmployeePersistencePort;
import com.pragma.powerup.domain.spi.IRestaurantPersistencePort;

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

}
