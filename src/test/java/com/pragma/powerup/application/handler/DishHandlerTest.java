package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.DishRequest;
import com.pragma.powerup.application.dto.DishResponse;
import com.pragma.powerup.application.dto.DishUpdateRequest;
import com.pragma.powerup.application.dto.PaginatedResponse;
import com.pragma.powerup.application.mapper.DishRequestMapper;
import com.pragma.powerup.application.mapper.DishResponseMapper;
import com.pragma.powerup.domain.api.IDishServicePort;
import com.pragma.powerup.domain.dto.PaginatedModel;
import com.pragma.powerup.domain.model.Category;
import com.pragma.powerup.domain.model.Dish;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.*;

class DishHandlerTest {
    @Mock
    DishRequestMapper dishRequestMapper;
    @Mock
    DishResponseMapper dishResponseMapper;
    @Mock
    IDishServicePort dishServicePort;
    @InjectMocks
    DishHandler dishHandler;
    @Mock
    Dish dish;
    @Mock
    DishRequest dishRequest;
    @Mock
    DishResponse dishResponse;
    @Mock
    PaginatedModel<Dish> paginatedModel;
    @Mock
    PaginatedResponse<DishResponse> paginatedResponse;
    @Mock
    List<Dish> listDish;
    @Mock
    List<DishResponse> listDishResponse;
    @Mock
    Category category;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        category = new Category();
        category.setId(1L);
        category.setName("name");
        category.setDescription("description");

        dish = new Dish();
        dish.setId(1L);
        dish.setName("name");
        dish.setCategory(category);
        dish.setDescription("description");
        dish.setPrice(0.0);
        dish.setImageUrl("http://example.com/image.png");

        listDish = List.of(dish);

        dishRequest = new DishRequest();
        dishRequest.setName("name");
        dishRequest.setCategoryId(1L);
        dishRequest.setDescription("description");
        dishRequest.setPrice(0);
        dishRequest.setImageUrl("http://example.com/image.png");

        dishResponse = new DishResponse();
        dishResponse.setDishName("name");
        dishResponse.setDishDescription("description");
        dishResponse.setDishPrice(0.0);
        dishResponse.setDishImageUrl("http://example.com/image.png");

        listDishResponse = List.of(dishResponse);

        paginatedResponse = new PaginatedResponse<>(listDishResponse, 0, 0, 0L);

        paginatedModel = new PaginatedModel<Dish>(listDish, 0, 0, 0L);
    }

    @Test
    void testGetDishs() {
        when(dishServicePort.getDishs(anyInt(), anyInt(), anyString(), anyLong())).thenReturn(paginatedModel);

        PaginatedResponse<DishResponse> result = dishHandler.getDishs(0, 0, "sortDirection", Long.valueOf(1));
        Assertions.assertNotNull(result);
    }

    @Test
    void testSaveDishInDish() {
        when(dishRequestMapper.toDish(any(DishRequest.class))).thenReturn(dish);

        dishHandler.saveDishInDish(new DishRequest("name", Long.valueOf(1), "description", Integer.valueOf(0), "imageUrl"));
        verify(dishServicePort).saveDish(any(Dish.class));
    }

    @Test
    void testGetDishFromDish() {
        when(dishServicePort.getAllDish()).thenReturn(listDish);
        when(dishResponseMapper.toDishResponse(any(Dish.class))).thenReturn(dishResponse);

        List<DishResponse> result = dishHandler.getDishFromDish();
        Assertions.assertEquals(List.of(dishResponse), result);
    }

    @Test
    void testGetDishFromDish2() {
        when(dishResponseMapper.toDishResponse(any(Dish.class))).thenReturn(dishResponse);
        when(dishServicePort.getDishById(anyLong())).thenReturn(dish);

        DishResponse result = dishHandler.getDishFromDish(Long.valueOf(1));
        Assertions.assertEquals(dishResponse, result);
    }

    @Test
    void testUpdateDishInDish() {
        when(dishRequestMapper.toDish(anyLong(), any(DishUpdateRequest.class))).thenReturn(dish);

        dishHandler.updateDishInDish(Long.valueOf(1), new DishUpdateRequest("description", Double.valueOf(0)));
        verify(dishServicePort).updateDish(any(Dish.class));
    }

    @Test
    void testDeleteDishFromDish() {
        dishHandler.deleteDishFromDish(Long.valueOf(1));
        verify(dishServicePort).deleteDish(anyLong());
    }

    @Test
    void testToggleDishStatus() {
        dishHandler.toggleDishStatus(Long.valueOf(1));
        verify(dishServicePort).toggleDishStatus(anyLong());
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme