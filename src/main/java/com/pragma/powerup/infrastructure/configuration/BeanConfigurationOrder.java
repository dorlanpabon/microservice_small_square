package com.pragma.powerup.infrastructure.configuration;

import com.pragma.powerup.domain.api.IOrderServicePort;
import com.pragma.powerup.domain.model.Order;
import com.pragma.powerup.domain.spi.IOrderPersistencePort;
import com.pragma.powerup.domain.usecase.OrderUseCase;
import com.pragma.powerup.infrastructure.output.jpa.adapter.OrderJpaAdapter;
import com.pragma.powerup.infrastructure.output.jpa.mapper.OrderEntityMapper;
import com.pragma.powerup.infrastructure.output.jpa.repository.IOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;

@Configuration
@RequiredArgsConstructor
public class BeanConfigurationOrder {

    private final IOrderRepository orderRepository;
    private final OrderEntityMapper orderEntityMapper;

    @Bean
    public IOrderPersistencePort orderPersistencePort() {
        return new OrderJpaAdapter(orderRepository, orderEntityMapper);
    }

    @Bean
    public IOrderServicePort orderServicePort() {
        return new OrderUseCase(orderPersistencePort()) {
            @Override
            public Page<Order> getOrders(int page, int size, boolean ascending) {
                return null;
            }
        };
    }
}
