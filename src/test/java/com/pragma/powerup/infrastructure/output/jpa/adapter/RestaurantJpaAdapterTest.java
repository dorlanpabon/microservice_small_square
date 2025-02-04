package com.pragma.powerup.infrastructure.output.jpa.adapter;

import com.pragma.powerup.domain.dto.PaginatedModel;
import com.pragma.powerup.domain.model.Restaurant;
import com.pragma.powerup.infrastructure.output.jpa.entity.RestaurantEntity;
import com.pragma.powerup.infrastructure.output.jpa.mapper.RestaurantEntityMapper;
import com.pragma.powerup.infrastructure.output.jpa.repository.IRestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RestaurantJpaAdapterTest {

    @Mock
    private IRestaurantRepository restaurantRepository;

    @Mock
    private RestaurantEntityMapper restaurantEntityMapper;

    @InjectMocks
    private RestaurantJpaAdapter restaurantJpaAdapter;

    private Restaurant restaurant;
    private RestaurantEntity restaurantEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setName("Test Restaurant");
        restaurant.setNit("123456789");
        restaurant.setOwnerId(101L);

        restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(1L);
        restaurantEntity.setName("Test Restaurant");
        restaurantEntity.setNit("123456789");
        restaurantEntity.setOwnerId(101L);
    }

    @Test
    void testSaveRestaurant() {
        when(restaurantEntityMapper.toEntity(restaurant)).thenReturn(restaurantEntity);
        when(restaurantRepository.save(restaurantEntity)).thenReturn(restaurantEntity);

        restaurantJpaAdapter.saveRestaurant(restaurant);

        verify(restaurantRepository, times(1)).save(restaurantEntity);
        verify(restaurantEntityMapper, times(1)).toEntity(restaurant);
    }

    @Test
    void testExistsRestaurant() {
        String nit = "123456789";
        when(restaurantRepository.existsByNit(nit)).thenReturn(true);

        boolean result = restaurantJpaAdapter.existsRestaurant(nit);

        assertTrue(result);
        verify(restaurantRepository, times(1)).existsByNit(nit);
    }

    @Test
    void testIsOwnerOfRestaurant() {
        Long ownerId = 101L;
        Long restaurantId = 1L;
        when(restaurantRepository.existsByIdAndOwnerId(restaurantId, ownerId)).thenReturn(true);

        boolean result = restaurantJpaAdapter.isOwnerOfRestaurant(ownerId, restaurantId);

        assertTrue(result);
        verify(restaurantRepository, times(1)).existsByIdAndOwnerId(restaurantId, ownerId);
    }

    @Test
    void testGetRestaurants() {
        int page = 0;
        int size = 10;
        String sortDirection = "asc";
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), "name");
        Pageable pageable = PageRequest.of(page, size, sort);

        List<RestaurantEntity> restaurantEntities = Arrays.asList(restaurantEntity);
        Page<RestaurantEntity> restaurantPage = new PageImpl<>(restaurantEntities, pageable, restaurantEntities.size());

        when(restaurantRepository.findAll(pageable)).thenReturn(restaurantPage);
        when(restaurantEntityMapper.toRestaurant(any(RestaurantEntity.class))).thenReturn(restaurant);

        PaginatedModel<Restaurant> result = restaurantJpaAdapter.getRestaurants(page, size, sortDirection);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertEquals(restaurant.getId(), result.getContent().get(0).getId());

        verify(restaurantRepository, times(1)).findAll(pageable);
        verify(restaurantEntityMapper, times(1)).toRestaurant(any(RestaurantEntity.class));
    }

    @Test
    void testGetByOwnerId() {
        Long ownerId = 101L;
        when(restaurantRepository.findByOwnerId(ownerId)).thenReturn(restaurantEntity);
        when(restaurantEntityMapper.toRestaurant(restaurantEntity)).thenReturn(restaurant);

        Restaurant result = restaurantJpaAdapter.getByOwnerId(ownerId);

        assertNotNull(result);
        assertEquals(ownerId, result.getOwnerId());

        verify(restaurantRepository, times(1)).findByOwnerId(ownerId);
        verify(restaurantEntityMapper, times(1)).toRestaurant(restaurantEntity);
    }

    @Test
    void testGetRestaurantIdByOwnerId() {
        Long userId = 101L;
        when(restaurantRepository.findByOwnerId(userId)).thenReturn(restaurantEntity);
        when(restaurantEntityMapper.toRestaurant(restaurantEntity)).thenReturn(restaurant);

        Restaurant result = restaurantJpaAdapter.getRestaurantIdByOwnerId(userId);

        assertNotNull(result);
        assertEquals(userId, result.getOwnerId());

        verify(restaurantRepository, times(1)).findByOwnerId(userId);
        verify(restaurantEntityMapper, times(1)).toRestaurant(restaurantEntity);
    }
}
