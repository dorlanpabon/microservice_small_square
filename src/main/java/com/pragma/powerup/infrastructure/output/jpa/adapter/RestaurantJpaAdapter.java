package com.pragma.powerup.infrastructure.output.jpa.adapter;

import com.pragma.powerup.application.dto.RestaurantResponse;
import com.pragma.powerup.domain.dto.PaginatedModel;
import com.pragma.powerup.domain.model.Restaurant;
import com.pragma.powerup.domain.spi.IRestaurantPersistencePort;
import com.pragma.powerup.infrastructure.output.jpa.entity.RestaurantEntity;
import com.pragma.powerup.infrastructure.output.jpa.mapper.RestaurantEntityMapper;
import com.pragma.powerup.infrastructure.output.jpa.repository.IRestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class RestaurantJpaAdapter implements IRestaurantPersistencePort {

    private final IRestaurantRepository restaurantRepository;

    private final RestaurantEntityMapper restaurantEntityMapper;

    @Override
    public void saveRestaurant(Restaurant restaurant) {
        restaurantRepository.save(restaurantEntityMapper.toEntity(restaurant));
    }

    @Override
    public boolean existsRestaurant(String nit) {
        return restaurantRepository.existsByNit(nit);
    }

    @Override
    public boolean isOwnerOfRestaurant(Long ownerId, Long restaurantId) {
        return restaurantRepository.existsByIdAndOwnerId(restaurantId, ownerId);
    }


    @Override
    public PaginatedModel<Restaurant> getRestaurants(int page, int size, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), "name");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<RestaurantEntity> restaurantEntities = restaurantRepository.findAll(pageable);

        List<Restaurant> content = restaurantEntities.getContent().stream()
                .map(restaurantEntityMapper::toRestaurant)
                .collect(Collectors.toList());

        return new PaginatedModel<>(content, restaurantEntities.getNumber(),
                restaurantEntities.getTotalPages(), restaurantEntities.getTotalElements());
    }

    @Override
    public Restaurant getByOwnerId(Long ownerId) {
        return restaurantEntityMapper.toRestaurant(restaurantRepository.findByOwnerId(ownerId));
    }

    @Override
    public Restaurant getRestaurantIdByOwnerId(Long userId) {
        System.out.println("RestaurantJpaAdapter.getRestaurantIdByOwnerId");
        return restaurantEntityMapper.toRestaurant(restaurantRepository.findByOwnerId(userId));
    }

}
