package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.model.Employee;
import com.pragma.powerup.domain.model.Restaurant;
import com.pragma.powerup.domain.spi.IEmployeePersistencePort;
import com.pragma.powerup.domain.spi.IRestaurantPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class EmployeeUseCaseTest {
    @Mock
    IEmployeePersistencePort employeePersistencePort;
    @Mock
    IRestaurantPersistencePort restaurantPersistencePort;
    @InjectMocks
    EmployeeUseCase employeeUseCase;
    @Mock
    Restaurant restaurant;
    @Mock
    Employee employee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setOwnerId(1L);
        restaurant.setName("name");
        restaurant.setNit("123456789");
        restaurant.setAddress("address");
        restaurant.setLogoUrl("http://example.com/logo.png");

        employee = new Employee();
        employee.setId(1L);
        employee.setRestaurant(restaurant);
        employee.setOwnerId(1L);
    }

    @Test
    void testSaveEmployee() {
        when(restaurantPersistencePort.getByOwnerId(anyLong())).thenReturn(restaurant);

        employeeUseCase.saveEmployee(employee);
        verify(employeePersistencePort).saveEmployee(any(Employee.class));
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme