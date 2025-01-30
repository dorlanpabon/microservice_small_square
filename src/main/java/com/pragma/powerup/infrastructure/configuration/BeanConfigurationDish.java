package com.pragma.powerup.infrastructure.configuration;

import com.pragma.powerup.domain.api.IDishServicePort;
import com.pragma.powerup.domain.model.Dish;
import com.pragma.powerup.domain.spi.IDishPersistencePort;
import com.pragma.powerup.domain.usecase.DishUseCase;
import com.pragma.powerup.infrastructure.output.jpa.adapter.DishJpaAdapter;
import com.pragma.powerup.infrastructure.output.jpa.mapper.DishEntityMapper;
import com.pragma.powerup.infrastructure.output.jpa.repository.IDishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;

@Configuration
@RequiredArgsConstructor
public class BeanConfigurationDish {

    private final IDishRepository dishRepository;
    private final DishEntityMapper dishEntityMapper;

    @Bean
    public IDishPersistencePort dishPersistencePort() {
        return new DishJpaAdapter(dishRepository, dishEntityMapper);
    }

    @Bean
    public IDishServicePort dishServicePort() {
        return new DishUseCase(dishPersistencePort()) {
            @Override
            public Page<Dish> getDishs(int page, int size, boolean ascending) {
                return null;
            }
        };
    }
}
