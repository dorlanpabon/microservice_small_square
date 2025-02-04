package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IUserServicePort;
import com.pragma.powerup.domain.dto.PaginatedModel;
import com.pragma.powerup.domain.exception.DomainException;
import com.pragma.powerup.domain.model.Category;
import com.pragma.powerup.domain.model.Dish;
import com.pragma.powerup.domain.model.Restaurant;
import com.pragma.powerup.domain.spi.IDishPersistencePort;
import com.pragma.powerup.domain.spi.IRestaurantPersistencePort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.*;

class DishUseCaseTest {
    @Mock
    IDishPersistencePort dishPersistencePort;
    @Mock
    IRestaurantPersistencePort restaurantPersistencePort;
    @Mock
    IUserServicePort userServicePort;
    @InjectMocks
    DishUseCase dishUseCase;

    @Mock
    Restaurant restaurant;

    @Mock
    Dish dish;

    @Mock
    Category category;

    @Mock
    List<Dish> listDish;

    @Mock
    PaginatedModel<Dish> paginatedModel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setOwnerId(1L);

        category = new Category();
        category.setId(1L);
        category.setName("name");

        dish = new Dish();
        dish.setId(1L);
        dish.setName("name");
        dish.setPrice(1.0);
        dish.setActive(true);
        dish.setDescription("description");
        dish.setImageUrl("imageUrl");
        dish.setCategory(category);
        dish.setRestaurant(restaurant);

        listDish = List.of(new Dish());
        paginatedModel = new PaginatedModel<Dish>(List.of(new Dish()), 0, 0, 0L);
    }

    @Test
    void testSaveDish() {
        when(userServicePort.getUserId()).thenReturn(Long.valueOf(1));
        when(restaurantPersistencePort.getRestaurantIdByOwnerId(anyLong())).thenReturn(restaurant);
        when(restaurantPersistencePort.isOwnerOfRestaurant(anyLong(), anyLong())).thenReturn(true);

        dishUseCase.saveDish(dish);
        verify(dishPersistencePort).saveDish(any(Dish.class));
    }

    @Test
    void testSaveDish_Failure_NotOwner() {
        when(userServicePort.getUserId()).thenReturn(Long.valueOf(1));
        when(restaurantPersistencePort.getRestaurantIdByOwnerId(anyLong())).thenReturn(restaurant);
        when(restaurantPersistencePort.isOwnerOfRestaurant(anyLong(), anyLong())).thenReturn(false);

        Assertions.assertThrows(DomainException.class, () -> {
            dishUseCase.saveDish(dish);
        });
    }

    @Test
    void testSaveDish_Failure_PriceLessThanZero() {
        dish.setPrice(-1.0);
        when(userServicePort.getUserId()).thenReturn(Long.valueOf(1));
        when(restaurantPersistencePort.getRestaurantIdByOwnerId(anyLong())).thenReturn(restaurant);
        when(restaurantPersistencePort.isOwnerOfRestaurant(anyLong(), anyLong())).thenReturn(true);

        Assertions.assertThrows(DomainException.class, () -> {
            dishUseCase.saveDish(dish);
        });
    }

    @Test
    void testSaveDish_Failure_DescriptionEmpty() {
        dish.setDescription("");
        when(userServicePort.getUserId()).thenReturn(Long.valueOf(1));
        when(restaurantPersistencePort.getRestaurantIdByOwnerId(anyLong())).thenReturn(restaurant);
        when(restaurantPersistencePort.isOwnerOfRestaurant(anyLong(), anyLong())).thenReturn(true);

        Assertions.assertThrows(DomainException.class, () -> {
            dishUseCase.saveDish(dish);
        });
    }

    @Test
    void testSaveDish_Failure_DishNameEmpty() {
        dish.setName("");
        when(userServicePort.getUserId()).thenReturn(Long.valueOf(1));
        when(restaurantPersistencePort.getRestaurantIdByOwnerId(anyLong())).thenReturn(restaurant);
        when(restaurantPersistencePort.isOwnerOfRestaurant(anyLong(), anyLong())).thenReturn(true);

        Assertions.assertThrows(DomainException.class, () -> {
            dishUseCase.saveDish(dish);
        });
    }

    @Test
    void testSaveDish_Failure_ImageUrlEmpty() {
        dish.setImageUrl("");
        when(userServicePort.getUserId()).thenReturn(Long.valueOf(1));
        when(restaurantPersistencePort.getRestaurantIdByOwnerId(anyLong())).thenReturn(restaurant);
        when(restaurantPersistencePort.isOwnerOfRestaurant(anyLong(), anyLong())).thenReturn(true);

        Assertions.assertThrows(DomainException.class, () -> {
            dishUseCase.saveDish(dish);
        });
    }

    @Test
    void testSaveDish_Failure_CategoryNull() {
        dish.setCategory(null);
        when(userServicePort.getUserId()).thenReturn(Long.valueOf(1));
        when(restaurantPersistencePort.getRestaurantIdByOwnerId(anyLong())).thenReturn(restaurant);
        when(restaurantPersistencePort.isOwnerOfRestaurant(anyLong(), anyLong())).thenReturn(true);

        Assertions.assertThrows(DomainException.class, () -> {
            dishUseCase.saveDish(dish);
        });
    }

    @Test
    void testGetAllDish() {
        when(dishPersistencePort.getAllDish()).thenReturn(listDish);

        List<Dish> result = dishUseCase.getAllDish();
        Assertions.assertEquals(listDish, result);
    }

    @Test
    void testGetDishById() {
        when(dishPersistencePort.getDishById(anyLong())).thenReturn(dish);

        Dish result = dishUseCase.getDishById(Long.valueOf(1));
        Assertions.assertEquals(dish, result);
    }

    @Test
    void testUpdateDish() {
        when(dishPersistencePort.getDishById(anyLong())).thenReturn(dish);
        when(restaurantPersistencePort.isOwnerOfRestaurant(anyLong(), anyLong())).thenReturn(true);
        when(userServicePort.getUserId()).thenReturn(Long.valueOf(1));

        dishUseCase.updateDish(dish);
        verify(dishPersistencePort).updateDish(any(Dish.class));
    }

    @Test
    void testUpdateDish_Failure_DishNotFound() {
        when(dishPersistencePort.getDishById(anyLong())).thenReturn(null);
        when(restaurantPersistencePort.isOwnerOfRestaurant(anyLong(), anyLong())).thenReturn(true);
        when(userServicePort.getUserId()).thenReturn(Long.valueOf(1));

        Assertions.assertThrows(DomainException.class, () -> {
            dishUseCase.updateDish(dish);
        });
    }

    @Test
    void testUpdateDish_Failure_NotOwner() {
        when(dishPersistencePort.getDishById(anyLong())).thenReturn(dish);
        when(restaurantPersistencePort.isOwnerOfRestaurant(anyLong(), anyLong())).thenReturn(false);
        when(userServicePort.getUserId()).thenReturn(Long.valueOf(1));

        Assertions.assertThrows(DomainException.class, () -> {
            dishUseCase.updateDish(dish);
        });
    }

    @Test
    void testUpdateDish_Failure_PriceLessThanZero() {
        dish.setPrice(-1.0);
        when(dishPersistencePort.getDishById(anyLong())).thenReturn(dish);
        when(restaurantPersistencePort.isOwnerOfRestaurant(anyLong(), anyLong())).thenReturn(true);
        when(userServicePort.getUserId()).thenReturn(Long.valueOf(1));

        Assertions.assertThrows(DomainException.class, () -> {
            dishUseCase.updateDish(dish);
        });
    }

    @Test
    void testUpdateDish_Failure_DescriptionEmpty() {
        dish.setDescription("");
        when(dishPersistencePort.getDishById(anyLong())).thenReturn(dish);
        when(restaurantPersistencePort.isOwnerOfRestaurant(anyLong(), anyLong())).thenReturn(true);
        when(userServicePort.getUserId()).thenReturn(Long.valueOf(1));

        Assertions.assertThrows(DomainException.class, () -> {
            dishUseCase.updateDish(dish);
        });
    }

    @Test
    void testDeleteDish() {
        dishUseCase.deleteDish(Long.valueOf(1));
        verify(dishPersistencePort).deleteDish(anyLong());
    }

    @Test
    void testGetDishs() {
        when(dishPersistencePort.getDishs(anyInt(), anyInt(), anyString(), anyLong())).thenReturn(paginatedModel);

        PaginatedModel<Dish> result = dishUseCase.getDishs(0, 1, "asc", Long.valueOf(1));
        Assertions.assertEquals(paginatedModel, result);
    }

    @Test
    void testToggleDishStatus() {
        when(dishPersistencePort.getDishById(anyLong())).thenReturn(dish);
        when(restaurantPersistencePort.isOwnerOfRestaurant(anyLong(), anyLong())).thenReturn(true);
        when(userServicePort.getUserId()).thenReturn(Long.valueOf(1));

        dishUseCase.toggleDishStatus(Long.valueOf(1));
        verify(dishPersistencePort).saveDish(any(Dish.class));
    }

    @Test
    void testToggleDishStatus_Failure_DishNotFound() {
        when(dishPersistencePort.getDishById(anyLong())).thenReturn(null);
        when(restaurantPersistencePort.isOwnerOfRestaurant(anyLong(), anyLong())).thenReturn(true);
        when(userServicePort.getUserId()).thenReturn(Long.valueOf(1));

        Assertions.assertThrows(DomainException.class, () -> {
            dishUseCase.toggleDishStatus(Long.valueOf(1));
        });
    }

    @Test
    void testToggleDishStatus_Failure_NotOwner() {
        when(dishPersistencePort.getDishById(anyLong())).thenReturn(dish);
        when(restaurantPersistencePort.isOwnerOfRestaurant(anyLong(), anyLong())).thenReturn(false);
        when(userServicePort.getUserId()).thenReturn(Long.valueOf(1));

        Assertions.assertThrows(DomainException.class, () -> {
            dishUseCase.toggleDishStatus(Long.valueOf(1));
        });
    }
}