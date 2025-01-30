package com.pragma.powerup.infrastructure.configuration;

import com.pragma.powerup.domain.api.IOrderDishServicePort;
import com.pragma.powerup.domain.model.OrderDish;
import com.pragma.powerup.domain.spi.IOrderDishPersistencePort;
import com.pragma.powerup.domain.usecase.OrderDishUseCase;
import com.pragma.powerup.infrastructure.output.jpa.adapter.OrderDishJpaAdapter;
import com.pragma.powerup.infrastructure.output.jpa.mapper.OrderDishEntityMapper;
import com.pragma.powerup.infrastructure.output.jpa.repository.IOrderDishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;

@Configuration
@RequiredArgsConstructor
public class BeanConfigurationOrderDish {

    private final IOrderDishRepository orderdishRepository;
    private final OrderDishEntityMapper orderdishEntityMapper;

    @Bean
    public IOrderDishPersistencePort orderdishPersistencePort() {
        return new OrderDishJpaAdapter(orderdishRepository, orderdishEntityMapper);
    }

    @Bean
    public IOrderDishServicePort orderdishServicePort() {
        return new OrderDishUseCase(orderdishPersistencePort()) {
            @Override
            public Page<OrderDish> getOrderDishs(int page, int size, boolean ascending) {
                return null;
            }
        };
    }
}
