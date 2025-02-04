package com.pragma.powerup.infrastructure.output.jpa.mapper;

import com.pragma.powerup.domain.model.Dish;
import com.pragma.powerup.infrastructure.output.jpa.entity.DishEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DishEntityMapperTest {

    private DishEntityMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(DishEntityMapper.class);
    }

    @Test
    void testToEntity() {
        Dish dish = new Dish();
        dish.setId(1L);
        dish.setName("Pizza");
        dish.setDescription("Delicious pizza");
        dish.setPrice(9.99);
        dish.setImageUrl("http://example.com/pizza.jpg");
        dish.setActive(true);

        DishEntity entity = mapper.toEntity(dish);

        assertNotNull(entity, "La entidad no debe ser nula");
        assertEquals(dish.getId(), entity.getId(), "El id no coincide");
        assertEquals(dish.getName(), entity.getName(), "El name no coincide");
        assertEquals(dish.getDescription(), entity.getDescription(), "La descripción no coincide");
        assertEquals(dish.getPrice(), entity.getPrice(), "El precio no coincide");
        assertEquals(dish.getImageUrl(), entity.getImageUrl(), "La url de imagen no coincide");
        assertEquals(dish.getActive(), entity.getActive(), "El flag active no coincide");

        assertNull(entity.getCategory(), "La categoría debería ser null");
        assertNull(entity.getRestaurant(), "El restaurante debería ser null");
    }

    @Test
    void testToDish() {
        DishEntity entity = new DishEntity();
        entity.setId(2L);
        entity.setName("Burger");
        entity.setDescription("Tasty burger");
        entity.setPrice(5.99);
        entity.setImageUrl("http://example.com/burger.jpg");
        entity.setActive(false);

        Dish dish = mapper.toDish(entity);

        assertNotNull(dish, "El objeto Dish no debe ser nulo");
        assertEquals(entity.getId(), dish.getId(), "El id no coincide");
        assertEquals(entity.getName(), dish.getName(), "El name no coincide");
        assertEquals(entity.getDescription(), dish.getDescription(), "La descripción no coincide");
        assertEquals(entity.getPrice(), dish.getPrice(), "El precio no coincide");
        assertEquals(entity.getImageUrl(), dish.getImageUrl(), "La url de imagen no coincide");
        assertEquals(entity.getActive(), dish.getActive(), "El flag active no coincide");

        assertNull(dish.getCategory(), "La categoría debería ser null");
        assertNull(dish.getRestaurant(), "El restaurante debería ser null");
    }

    @Test
    void testToDishList() {
        DishEntity entity1 = new DishEntity();
        entity1.setId(3L);
        entity1.setName("Pasta");
        entity1.setDescription("Italian pasta");
        entity1.setPrice(7.99);
        entity1.setImageUrl("http://example.com/pasta.jpg");
        entity1.setActive(true);
        entity1.setCategory(null);
        entity1.setRestaurant(null);

        DishEntity entity2 = new DishEntity();
        entity2.setId(4L);
        entity2.setName("Salad");
        entity2.setDescription("Fresh salad");
        entity2.setPrice(4.99);
        entity2.setImageUrl("http://example.com/salad.jpg");
        entity2.setActive(true);
        entity2.setCategory(null);
        entity2.setRestaurant(null);

        List<DishEntity> entityList = List.of(entity1, entity2);

        List<Dish> dishes = mapper.toDishList(entityList);

        assertNotNull(dishes, "La lista resultante no debe ser nula");
        assertEquals(2, dishes.size(), "El tamaño de la lista no coincide");

        Dish dish1 = dishes.get(0);
        assertEquals(entity1.getId(), dish1.getId());
        assertEquals(entity1.getName(), dish1.getName());
        assertEquals(entity1.getDescription(), dish1.getDescription());
        assertEquals(entity1.getPrice(), dish1.getPrice());
        assertEquals(entity1.getImageUrl(), dish1.getImageUrl());
        assertEquals(entity1.getActive(), dish1.getActive());

        Dish dish2 = dishes.get(1);
        assertEquals(entity2.getId(), dish2.getId());
        assertEquals(entity2.getName(), dish2.getName());
        assertEquals(entity2.getDescription(), dish2.getDescription());
        assertEquals(entity2.getPrice(), dish2.getPrice());
        assertEquals(entity2.getImageUrl(), dish2.getImageUrl());
        assertEquals(entity2.getActive(), dish2.getActive());
    }
}
