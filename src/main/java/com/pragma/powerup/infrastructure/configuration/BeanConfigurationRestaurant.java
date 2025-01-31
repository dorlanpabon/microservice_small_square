package com.pragma.powerup.infrastructure.configuration;

import com.pragma.powerup.domain.api.IRestaurantServicePort;
import com.pragma.powerup.domain.api.IUserServicePort;
import com.pragma.powerup.domain.model.Restaurant;
import com.pragma.powerup.domain.spi.IRestaurantPersistencePort;
import com.pragma.powerup.domain.usecase.RestaurantUseCase;
import com.pragma.powerup.infrastructure.adapter.UserServiceAdapter;
import com.pragma.powerup.infrastructure.client.UserFeignClient;
import com.pragma.powerup.infrastructure.output.jpa.adapter.RestaurantJpaAdapter;
import com.pragma.powerup.infrastructure.output.jpa.mapper.RestaurantEntityMapper;
import com.pragma.powerup.infrastructure.output.jpa.repository.IRestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;

@Configuration
@RequiredArgsConstructor
public class BeanConfigurationRestaurant {

    private final IRestaurantRepository restaurantRepository;
    private final RestaurantEntityMapper restaurantEntityMapper;
    private final UserFeignClient userFeignClient;
    private final HttpServletRequest request;


    @Bean
    public IRestaurantPersistencePort restaurantPersistencePort() {
        return new RestaurantJpaAdapter(restaurantRepository, restaurantEntityMapper);
    }

    @Bean
    public IUserServicePort userServicePort() {
        return new UserServiceAdapter(userFeignClient, request);
    }

    @Bean
    public IRestaurantServicePort restaurantServicePort() {
        return new RestaurantUseCase(restaurantPersistencePort(), userServicePort());
    }
}
