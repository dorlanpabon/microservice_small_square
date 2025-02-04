package com.pragma.powerup.infrastructure.output.jpa.adapter;

import com.pragma.powerup.domain.model.Dish;
import com.pragma.powerup.infrastructure.output.jpa.entity.DishEntity;
import com.pragma.powerup.infrastructure.output.jpa.mapper.DishEntityMapper;
import com.pragma.powerup.infrastructure.output.jpa.repository.IDishRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DishJpaAdapterTest {
    @Mock
    IDishRepository dishRepository;
    @Mock
    DishEntityMapper dishEntityMapper;
    @InjectMocks
    DishJpaAdapter dishJpaAdapter;
    @Mock
    DishEntity dishEntity;
    @Mock
    Dish dish;
    @Mock
    List<DishEntity> listDishEntity;
    @Mock
    List<Dish> listDish;
    @Mock
    Page<DishEntity> dishEntityPage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        dishEntity = new DishEntity(Long.valueOf(1), "name", null, "description", Double.valueOf(0), null, "imageUrl", Boolean.TRUE);

        dish = new Dish();
        dish.setId(Long.valueOf(1));
        dish.setName("name");
        dish.setDescription("description");
        dish.setPrice(Double.valueOf(0));
        dish.setImageUrl("imageUrl");
        dish.setActive(Boolean.TRUE);

        listDishEntity = List.of(dishEntity);

        listDish = List.of(dish);

        dishEntityPage = new PageImpl<>(listDishEntity);
    }

    @Test
    void testSaveDish() {
        when(dishRepository.save(any())).thenReturn(dishEntity);
        when(dishEntityMapper.toEntity(any(Dish.class))).thenReturn(dishEntity);

        dishJpaAdapter.saveDish(dish);
        verify(dishRepository).save(any(DishEntity.class));
    }

    @Test
    void testGetAllDish() {
        when(dishRepository.findAll()).thenReturn(listDishEntity);
        when(dishEntityMapper.toDishList(any(List.class))).thenReturn(listDish);

        List<Dish> result = dishJpaAdapter.getAllDish();
        Assertions.assertEquals(listDish, result);
    }

    @Test
    void testGetDishById() {
        when(dishRepository.findById(anyLong())).thenReturn(Optional.ofNullable(dishEntity));
        when(dishEntityMapper.toDish(any(DishEntity.class))).thenReturn(dish);

        Dish result = dishJpaAdapter.getDishById(1L);
        Assertions.assertEquals(dish, result);
    }

    @Test
    void testUpdateDish() {
        when(dishEntityMapper.toEntity(any(Dish.class))).thenReturn(dishEntity);

        dishJpaAdapter.updateDish(dish);
        verify(dishRepository).updateDish(any(DishEntity.class));
    }

    @Test
    void testDeleteDish() {
        dishJpaAdapter.deleteDish(Long.valueOf(1));
        verify(dishRepository).deleteById(anyLong());
    }

    @Test
    void testGetDishs2() {
        when(dishRepository.findByCategoryId(anyLong(), any(Pageable.class))).thenReturn(dishEntityPage);
        when(dishRepository.findAll(any(Pageable.class))).thenReturn(dishEntityPage);

        dishJpaAdapter.getDishs(0, 10, "asc", 1L);
        verify(dishRepository).findByCategoryId(anyLong(), any(Pageable.class));
    }

}