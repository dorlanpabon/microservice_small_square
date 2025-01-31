package com.pragma.powerup.infrastructure.configuration;

import com.pragma.powerup.domain.api.IEmployeeServicePort;
import com.pragma.powerup.domain.model.Employee;
import com.pragma.powerup.domain.spi.IEmployeePersistencePort;
import com.pragma.powerup.domain.spi.IRestaurantPersistencePort;
import com.pragma.powerup.domain.usecase.EmployeeUseCase;
import com.pragma.powerup.infrastructure.output.jpa.adapter.EmployeeJpaAdapter;
import com.pragma.powerup.infrastructure.output.jpa.mapper.EmployeeEntityMapper;
import com.pragma.powerup.infrastructure.output.jpa.repository.IEmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;

@Configuration
@RequiredArgsConstructor
public class BeanConfigurationEmployee {

    private final IEmployeeRepository employeeRepository;
    private final EmployeeEntityMapper employeeEntityMapper;

    @Bean
    public IEmployeePersistencePort employeePersistencePort() {
        return new EmployeeJpaAdapter(employeeRepository, employeeEntityMapper);
    }

    @Bean
    public IEmployeeServicePort employeeServicePort(IRestaurantPersistencePort restaurantPersistencePort) {
        return new EmployeeUseCase(employeePersistencePort(),restaurantPersistencePort);
    }
}
