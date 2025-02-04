package com.pragma.powerup.infrastructure.output.jpa.adapter;

import com.pragma.powerup.domain.dto.PaginatedModel;
import com.pragma.powerup.domain.enums.OrderStatusEnum;
import com.pragma.powerup.domain.model.Order;
import com.pragma.powerup.infrastructure.output.jpa.entity.OrderEntity;
import com.pragma.powerup.infrastructure.output.jpa.mapper.OrderEntityMapper;
import com.pragma.powerup.infrastructure.output.jpa.repository.IOrderRepository;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OrderJpaAdapterTest {

    @Mock
    private IOrderRepository orderRepository;

    @Mock
    private OrderEntityMapper orderEntityMapper;

    @InjectMocks
    private OrderJpaAdapter orderJpaAdapter;

    private Order order;
    private OrderEntity orderEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        order = new Order();
        order.setId(1L);
        order.setStatus("PENDING");

        orderEntity = new OrderEntity();
        orderEntity.setId(1L);
        orderEntity.setStatus(OrderStatusEnum.PENDING);
    }

    @Test
    void testSaveOrder() {
        when(orderEntityMapper.toEntity(order)).thenReturn(orderEntity);
        when(orderRepository.save(orderEntity)).thenReturn(orderEntity);
        when(orderEntityMapper.toOrder(orderEntity)).thenReturn(order);

        Order result = orderJpaAdapter.saveOrder(order);

        assertNotNull(result);
        assertEquals(order.getId(), result.getId());
        verify(orderRepository, times(1)).save(orderEntity);
    }

    @Test
    void testGetAllOrder() {
        List<OrderEntity> orderEntities = Arrays.asList(orderEntity);
        when(orderRepository.findAll()).thenReturn(orderEntities);
        when(orderEntityMapper.toOrderList(orderEntities)).thenReturn(Arrays.asList(order));

        List<Order> result = orderJpaAdapter.getAllOrder();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void testGetOrderById() {
        Long orderId = 1L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(orderEntity));
        when(orderEntityMapper.toOrder(orderEntity)).thenReturn(order);

        Order result = orderJpaAdapter.getOrderById(orderId);

        assertNotNull(result);
        assertEquals(orderId, result.getId());
        verify(orderRepository, times(1)).findById(orderId);
    }

    @Test
    void testUpdateOrder() {
        when(orderEntityMapper.toEntity(order)).thenReturn(orderEntity);
        when(orderRepository.save(orderEntity)).thenReturn(orderEntity);

        orderJpaAdapter.updateOrder(order);

        verify(orderRepository, times(1)).save(orderEntity);
    }

    @Test
    void testDeleteOrder() {
        Long orderId = 1L;

        orderJpaAdapter.deleteOrder(orderId);

        verify(orderRepository, times(1)).deleteById(orderId);
    }

    @Test
    void testGetOrdersByStatusAndRestaurantId() {
        int pageNumber = 0;
        int pageSize = 10;
        OrderStatusEnum status = OrderStatusEnum.PENDING;
        Long restaurantId = 1L;

        List<OrderEntity> orderEntities = Arrays.asList(orderEntity);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("status").ascending());

        Page<OrderEntity> orderPage = new PageImpl<>(orderEntities, pageable, orderEntities.size());
        when(orderRepository.findAllByStatusAndRestaurantId(status, restaurantId, pageable)).thenReturn(orderPage);
        when(orderEntityMapper.toOrder(any(OrderEntity.class))).thenReturn(order);

        PaginatedModel<Order> result = orderJpaAdapter.getOrders(pageNumber, pageSize, "ASC", status, restaurantId);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(orderRepository, times(1)).findAllByStatusAndRestaurantId(status, restaurantId, pageable);
    }

    @Test
    void testCountByClientIdAndStatus() {
        Long clientId = 1L;
        List<OrderStatusEnum> pendingStatuses = List.of(OrderStatusEnum.PENDING);

        when(orderRepository.countByClientIdAndStatusIn(clientId, pendingStatuses)).thenReturn(3L);

        Long result = orderJpaAdapter.countByClientIdAndStatus(clientId, pendingStatuses);

        assertEquals(3L, result);
        verify(orderRepository, times(1)).countByClientIdAndStatusIn(clientId, pendingStatuses);
    }

    @Test
    void testGetOrderByIdAndRestaurantId() {
        Long orderId = 1L;
        Long restaurantId = 2L;

        when(orderRepository.findByIdAndRestaurantId(orderId, restaurantId)).thenReturn(Optional.of(orderEntity));
        when(orderEntityMapper.toOrder(orderEntity)).thenReturn(order);

        Optional<Order> result = orderJpaAdapter.getOrderByIdAndRestaurantId(orderId, restaurantId);

        assertTrue(result.isPresent());
        assertEquals(orderId, result.get().getId());
        verify(orderRepository, times(1)).findByIdAndRestaurantId(orderId, restaurantId);
    }

    @Test
    void testGetOrderIdsByRestaurantIdAndStatus() {
        Long restaurantId = 1L;
        OrderStatusEnum status = OrderStatusEnum.PENDING;

        List<OrderEntity> orderEntities = Arrays.asList(orderEntity);
        when(orderRepository.findIdsByRestaurantIdAndStatus(restaurantId, status)).thenReturn(orderEntities);

        List<Long> result = orderJpaAdapter.getOrderIdsByRestaurantIdAndStatus(restaurantId, status);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(orderRepository, times(1)).findIdsByRestaurantIdAndStatus(restaurantId, status);
    }

    @Test
    void testGetOrderIdsByChefIdAndStatus() {
        Long chefId = 5L;
        OrderStatusEnum status = OrderStatusEnum.PENDING;

        when(orderRepository.findIdsByChefIdAndStatus(chefId, status)).thenReturn(Optional.ofNullable(orderEntity));

        List<Long> result = orderJpaAdapter.getOrderIdsByChefIdAndStatus(chefId, status);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(orderRepository, times(1)).findIdsByChefIdAndStatus(chefId, status);
    }
}
