package com.pragma.powerup.infrastructure.output.jpa.mapper;

import com.pragma.powerup.domain.enums.OrderStatusEnum;
import com.pragma.powerup.domain.model.Order;
import com.pragma.powerup.infrastructure.output.jpa.entity.OrderEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OrderEntityMapperTest {

    private OrderEntityMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(OrderEntityMapper.class);
    }

    @Test
    void testToEntity() {
        Order order = new Order();
        order.setId(1L);
        order.setClientId(100L);
        order.setDate(LocalDateTime.now());
        order.setStatus("PENDING");
        order.setChefId(200L);
        order.setOrderDishes(Collections.emptyList());

        OrderEntity entity = mapper.toEntity(order);

        assertNotNull(entity, "El objeto OrderEntity no debe ser nulo");
        assertEquals(order.getId(), entity.getId(), "El ID debe coincidir");
        assertEquals(order.getClientId(), entity.getClientId(), "El clientId debe coincidir");
        assertEquals(order.getDate(), entity.getDate(), "La fecha debe coincidir");
        assertEquals(OrderStatusEnum.valueOf(order.getStatus()), entity.getStatus(), "El estado debe coincidir");
        assertEquals(order.getChefId(), entity.getChefId(), "El chefId debe coincidir");
        assertTrue(entity.getOrderDishes().isEmpty(), "La lista de orderDishes debe estar vacía");
    }

    @Test
    void testToOrder() {
        OrderEntity entity = new OrderEntity();
        entity.setId(2L);
        entity.setClientId(101L);
        entity.setDate(LocalDateTime.now());
        entity.setStatus(OrderStatusEnum.IN_PREPARATION);
        entity.setChefId(201L);
        entity.setOrderDishes(Collections.emptyList());

        Order order = mapper.toOrder(entity);

        assertNotNull(order, "El objeto Order no debe ser nulo");
        assertEquals(entity.getId(), order.getId(), "El ID debe coincidir");
        assertEquals(entity.getClientId(), order.getClientId(), "El clientId debe coincidir");
        assertEquals(entity.getDate(), order.getDate(), "La fecha debe coincidir");
        assertEquals(entity.getStatus().name(), order.getStatus(), "El estado debe coincidir");
        assertEquals(entity.getChefId(), order.getChefId(), "El chefId debe coincidir");
        assertNull(order.getOrderDishes(), "La lista de orderDishes debe ser null debido a la configuración de ignore");
    }

    @Test
    void testToOrderList() {
        OrderEntity entity = new OrderEntity();
        entity.setId(3L);
        entity.setClientId(102L);
        entity.setDate(LocalDateTime.now());
        entity.setStatus(OrderStatusEnum.READY);
        entity.setChefId(202L);
        entity.setOrderDishes(Collections.emptyList());

        List<OrderEntity> entityList = List.of(entity);

        List<Order> orderList = mapper.toOrderList(entityList);

        assertNotNull(orderList, "La lista de órdenes no debe ser nula");
        assertEquals(1, orderList.size(), "La lista debe contener un solo elemento");
        Order order = orderList.get(0);
        assertEquals(entity.getId(), order.getId(), "El ID debe coincidir");
        assertEquals(entity.getClientId(), order.getClientId(), "El clientId debe coincidir");
        assertEquals(entity.getDate(), order.getDate(), "La fecha debe coincidir");
        assertEquals(entity.getStatus().name(), order.getStatus(), "El estado debe coincidir");
        assertEquals(entity.getChefId(), order.getChefId(), "El chefId debe coincidir");
    }
}
