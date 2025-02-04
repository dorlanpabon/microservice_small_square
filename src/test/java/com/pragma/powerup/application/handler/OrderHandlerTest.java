package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.LogEmployeeResponse;
import com.pragma.powerup.application.dto.LogResponse;
import com.pragma.powerup.application.dto.LogTimeResponse;
import com.pragma.powerup.application.dto.OrderAssignRequest;
import com.pragma.powerup.application.dto.OrderDishRequest;
import com.pragma.powerup.application.dto.OrderRequest;
import com.pragma.powerup.application.dto.OrderResponse;
import com.pragma.powerup.application.dto.PaginatedResponse;
import com.pragma.powerup.application.mapper.LogResponseMapper;
import com.pragma.powerup.application.mapper.OrderRequestMapper;
import com.pragma.powerup.application.mapper.OrderResponseMapper;
import com.pragma.powerup.domain.api.IOrderServicePort;
import com.pragma.powerup.domain.dto.PaginatedModel;
import com.pragma.powerup.domain.enums.OrderStatusEnum;
import com.pragma.powerup.domain.model.Category;
import com.pragma.powerup.domain.model.Dish;
import com.pragma.powerup.domain.model.Log;
import com.pragma.powerup.domain.model.Order;
import com.pragma.powerup.domain.model.Restaurant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OrderHandlerTest {
    @Mock
    OrderRequestMapper orderRequestMapper;
    @Mock
    OrderResponseMapper orderResponseMapper;
    @Mock
    IOrderServicePort orderServicePort;
    @Mock
    LogResponseMapper logResponseMapper;
    @InjectMocks
    OrderHandler orderHandler;
    @Mock
    Order order;
    @Mock
    Restaurant restaurant;
    @Mock
    Log log;
    @Mock
    Dish dish;
    @Mock
    OrderDishRequest orderDishRequest;
    @Mock
    Category category;
    @Mock
    OrderRequest orderRequest;
    @Mock
    OrderResponse orderResponse;
    @Mock
    LogResponse logResponse;
    @Mock
    PaginatedModel<Order> paginatedModel;
    @Mock
    PaginatedResponse<OrderResponse> paginatedResponse;
    @Mock
    List<Order> listOrder;
    @Mock
    List<Log> listLog;
    @Mock
    List<OrderResponse> listOrderResponse;
    @Mock
    PaginatedModel<Log> paginatedModelLog;
    @Mock
    PaginatedResponse<LogResponse> paginatedResponseLog;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setOwnerId(1L);
        restaurant.setName("name");
        restaurant.setNit("123456789");
        restaurant.setAddress("address");
        restaurant.setLogoUrl("http://example.com/logo.png");

        order = new Order();
        order.setId(1L);
        order.setChefId(1L);
        order.setClientId(1L);
        order.setStatus(OrderStatusEnum.PENDING.toString());
        order.setDate(LocalDateTime.now());
        order.setRestaurant(restaurant);

        listOrder = List.of(order);

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
        dish.setRestaurant(restaurant);

        orderDishRequest = new OrderDishRequest();
        orderDishRequest.setDishId(1L);
        orderDishRequest.setQuantity(1);

        orderRequest = new OrderRequest();
        orderRequest.setRestaurantId(1L);
        orderRequest.setOrderDishes(List.of(orderDishRequest));

        orderResponse = new OrderResponse();
        orderResponse.setOrderId(1L);

        listOrderResponse = List.of(orderResponse);

        paginatedResponse = new PaginatedResponse<>(listOrderResponse, 0, 0, 0L);

        paginatedModel = new PaginatedModel<Order>(listOrder, 0, 0, 0L);

        log = new Log();
        log.setId("logId");
        log.setType("logType");
        log.setOrderId(1L);
        log.setChefId(1L);
        log.setRestaurantId(1L);

        listLog = List.of(log);

        paginatedModelLog = new PaginatedModel<Log>(listLog, 0, 0, 0L);

        logResponse = new LogResponse();
        logResponse.setLogId("logId");
        logResponse.setLogType("logType");
        logResponse.setLogTimestamp(LocalDateTime.of(2025, Month.FEBRUARY, 4, 10, 44, 35));
        logResponse.setLogClientId(1L);
        logResponse.setLogOrderId(1L);

        paginatedResponseLog = new PaginatedResponse<>(List.of(logResponse), 0, 0, 0L);

    }

    @Test
    void testGetOrders() {
        when(orderServicePort.getOrders(anyInt(), anyInt(), anyString(), any(OrderStatusEnum.class))).thenReturn(paginatedModel);

        PaginatedResponse<OrderResponse> result = orderHandler.getOrders(0, 0, "sortDirection", OrderStatusEnum.PENDING);
        Assertions.assertNotNull(result);
    }

    @Test
    void testSaveOrderInOrder() {
        when(orderRequestMapper.toOrder(any(OrderRequest.class))).thenReturn(new Order());

        orderHandler.saveOrderInOrder(new OrderRequest());
        verify(orderServicePort).saveOrder(any(Order.class));
    }

    @Test
    void testGetOrderFromOrder() {
        when(orderServicePort.getAllOrder()).thenReturn(listOrder);
        when(orderResponseMapper.toOrderResponse(any(Order.class))).thenReturn(orderResponse);

        List<OrderResponse> result = orderHandler.getOrderFromOrder();
        Assertions.assertEquals(listOrderResponse, result);
    }

    @Test
    void testGetOrderFromOrder2() {
        when(orderResponseMapper.toOrderResponse(any(Order.class))).thenReturn(orderResponse);
        when(orderServicePort.getOrderById(anyLong())).thenReturn(new Order());

        OrderResponse result = orderHandler.getOrderFromOrder(Long.valueOf(1));
        Assertions.assertEquals(orderResponse, result);
    }

    @Test
    void testUpdateOrderInOrder() {
        when(orderRequestMapper.toOrder(any(OrderAssignRequest.class))).thenReturn(new Order());

        orderHandler.updateOrderInOrder(new OrderAssignRequest());
        verify(orderServicePort).updateOrder(any(Order.class));
    }

    @Test
    void testDeleteOrderFromOrder() {
        orderHandler.deleteOrderFromOrder(1L);
        verify(orderServicePort).deleteOrder(anyLong());
    }

    @Test
    void testCancelOrder() {
        orderHandler.cancelOrder(1L);
        verify(orderServicePort).cancelOrder(anyLong());
    }

    @Test
    void testGetLogsByOrderId() {
        when(orderServicePort.getLogsByOrderId(anyLong())).thenReturn(List.of(new Log()));

        List<LogResponse> result = orderHandler.getLogsByOrderId(1L);
        Assertions.assertNotNull(result);
    }

    @Test
    void testGetLogsTimeByOrderId() {
        when(orderServicePort.getLogsTimeByOrderId(anyLong())).thenReturn(Long.valueOf(1));

        LogTimeResponse result = orderHandler.getLogsTimeByOrderId(1L);
        Assertions.assertNotNull(result);
    }

    @Test
    void testGetLogsTimeByRestaurant() {
        LogTimeResponse logTimeResponse = new LogTimeResponse(1L, 1L);
        when(orderServicePort.getLogsTimeByRestaurant()).thenReturn(listLog);
        when(logResponseMapper.toLogTimeResponse(any(Log.class))).thenReturn(logTimeResponse);

        List<LogTimeResponse> result = orderHandler.getLogsTimeByRestaurant();
        Assertions.assertEquals(List.of(logTimeResponse), result);
    }

    @Test
    void testGetAverageTimeByEmployee() {
        LogEmployeeResponse logEmployeeResponse = new LogEmployeeResponse(1L,1L);
        List<LogEmployeeResponse> listLogEmployeeResponse = List.of(logEmployeeResponse);
        when(orderServicePort.getAverageTimeByEmployee()).thenReturn(listLog);
        when(logResponseMapper.toLogEmployeeResponse(any(Log.class))).thenReturn(logEmployeeResponse);

        List<LogEmployeeResponse> result = orderHandler.getAverageTimeByEmployee();
        Assertions.assertEquals(listLogEmployeeResponse, result);
    }
}