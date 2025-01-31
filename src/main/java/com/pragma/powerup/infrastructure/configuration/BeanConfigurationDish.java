package com.pragma.powerup.infrastructure.configuration;

import com.pragma.powerup.domain.api.IDishServicePort;
import com.pragma.powerup.domain.api.IUserServicePort;
import com.pragma.powerup.domain.model.Dish;
import com.pragma.powerup.domain.spi.IDishPersistencePort;
import com.pragma.powerup.domain.spi.IRestaurantPersistencePort;
import com.pragma.powerup.domain.usecase.DishUseCase;
import com.pragma.powerup.infrastructure.adapter.UserServiceAdapter;
import com.pragma.powerup.infrastructure.client.UserFeignClient;
import com.pragma.powerup.infrastructure.output.jpa.adapter.DishJpaAdapter;
import com.pragma.powerup.infrastructure.output.jpa.adapter.RestaurantJpaAdapter;
import com.pragma.powerup.infrastructure.output.jpa.mapper.DishEntityMapper;
import com.pragma.powerup.infrastructure.output.jpa.mapper.RestaurantEntityMapper;
import com.pragma.powerup.infrastructure.output.jpa.repository.IDishRepository;
import com.pragma.powerup.infrastructure.output.jpa.repository.IRestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;

@Configuration
@RequiredArgsConstructor
public class BeanConfigurationDish {

    private final IDishRepository dishRepository;
    private final DishEntityMapper dishEntityMapper;
    private final IRestaurantRepository restaurantRepository;
    private final RestaurantEntityMapper restaurantEntityMapper;
    private final UserFeignClient userFeignClient;
    private final HttpServletRequest request;

    @Bean
    public IRestaurantPersistencePort dishRestaurantPersistencePort() {
        return new RestaurantJpaAdapter(restaurantRepository, restaurantEntityMapper);
    }

    @Bean
    public IDishPersistencePort dishPersistencePort() {
        return new DishJpaAdapter(dishRepository, dishEntityMapper);
    }

    @Bean
    public IUserServicePort dishUserServicePort() {
        return new UserServiceAdapter(userFeignClient, request);
    }

    @Bean
    public IDishServicePort dishServicePort() {
        return new DishUseCase(dishPersistencePort(), dishRestaurantPersistencePort(), dishUserServicePort());
    }
}
