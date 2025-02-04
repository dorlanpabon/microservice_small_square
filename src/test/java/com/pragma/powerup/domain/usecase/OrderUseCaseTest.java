package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IMessageServicePort;
import com.pragma.powerup.domain.api.ITraceabilityServicePort;
import com.pragma.powerup.domain.api.IUserServicePort;
import com.pragma.powerup.domain.constants.DomainConstants;
import com.pragma.powerup.domain.dto.PaginatedModel;
import com.pragma.powerup.domain.enums.OrderStatusEnum;
import com.pragma.powerup.domain.exception.DomainException;
import com.pragma.powerup.domain.model.Employee;
import com.pragma.powerup.domain.model.Log;
import com.pragma.powerup.domain.model.Order;
import com.pragma.powerup.domain.model.Restaurant;
import com.pragma.powerup.domain.spi.IEmployeePersistencePort;
import com.pragma.powerup.domain.spi.IOrderPersistencePort;
import com.pragma.powerup.domain.spi.IRestaurantPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OrderUseCaseTest {

    @Mock
    private IOrderPersistencePort orderPersistencePort;

    @Mock
    private IUserServicePort userServicePort;

    @Mock
    private IEmployeePersistencePort employeePersistencePort;

    @Mock
    private IMessageServicePort messageServicePort;

    @Mock
    private ITraceabilityServicePort traceabilityServicePort;

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;

    @InjectMocks
    private OrderUseCase orderUseCase;

    private Order order;
    private Employee employee;
    private Restaurant restaurant;
    private Log log;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setOwnerId(1L);

        employee = new Employee();
        employee.setId(1L);
        employee.setRestaurant(restaurant);

        order = new Order();
        order.setId(1L);
        order.setClientId(1L);
        order.setRestaurant(restaurant);
        order.setStatus(OrderStatusEnum.PENDING.toString());
        order.setDate(LocalDateTime.now());

        log = new Log();
        log.setTime(10L);
        log.setOrderId(1L);
        log.setClientId(1L);
        log.setRestaurantId(1L);
        log.setType(OrderStatusEnum.PENDING.toString());
    }

    @Test
    void testSaveOrder_Success() {
        when(userServicePort.getUserId()).thenReturn(1L);
        when(orderPersistencePort.countByClientIdAndStatus(anyLong(), anyList())).thenReturn(0L);
        when(orderPersistencePort.saveOrder(any(Order.class))).thenReturn(order);
        doNothing().when(traceabilityServicePort).createLog(any(Log.class));

        assertDoesNotThrow(() -> orderUseCase.saveOrder(order));

        verify(orderPersistencePort, times(1)).saveOrder(order);
        verify(traceabilityServicePort, times(1)).createLog(any(Log.class));
    }

    @Test
    void testSaveOrder_Failure_AlreadyExists() {
        when(userServicePort.getUserId()).thenReturn(1L);
        when(orderPersistencePort.countByClientIdAndStatus(anyLong(), anyList())).thenReturn(1L);

        DomainException exception = assertThrows(DomainException.class, () -> orderUseCase.saveOrder(order));

        assertEquals(DomainConstants.ORDER_ALREADY_EXISTS, exception.getMessage());
        verify(orderPersistencePort, never()).saveOrder(any(Order.class));
    }

    @Test
    void testGetOrderById() {
        when(orderPersistencePort.getOrderById(1L)).thenReturn(order);

        Order result = orderUseCase.getOrderById(1L);

        assertNotNull(result);
        assertEquals(order.getId(), result.getId());
        verify(orderPersistencePort, times(1)).getOrderById(1L);
    }

    @Test
    void testUpdateOrder_Success() {
        when(userServicePort.getUserId()).thenReturn(1L);
        when(employeePersistencePort.getByEmployeeId(1L)).thenReturn(employee);
        when(orderPersistencePort.getOrderByIdAndRestaurantId(anyLong(), anyLong())).thenReturn(Optional.of(order));

        Order orderUpdated = new Order();
        orderUpdated.setId(1L);
        orderUpdated.setClientId(1L);
        orderUpdated.setRestaurant(restaurant);
        orderUpdated.setDate(LocalDateTime.now());
        orderUpdated.setStatus(OrderStatusEnum.IN_PREPARATION.toString());

        assertDoesNotThrow(() -> orderUseCase.updateOrder(orderUpdated));

        verify(orderPersistencePort, times(1)).updateOrder(order);
        verify(traceabilityServicePort, times(1)).createLog(any(Log.class));
    }

    @Test
    void testUpdateOrder_Failure_InvalidStatus() {
        when(userServicePort.getUserId()).thenReturn(1L);
        when(employeePersistencePort.getByEmployeeId(1L)).thenReturn(employee);
        when(orderPersistencePort.getOrderByIdAndRestaurantId(anyLong(), anyLong())).thenReturn(Optional.of(order));

        order.setStatus("INVALID_STATUS");

        DomainException exception = assertThrows(DomainException.class, () -> orderUseCase.updateOrder(order));

        assertEquals(DomainConstants.INVALID_ORDER_STATUS, exception.getMessage());
        verify(orderPersistencePort, never()).updateOrder(any(Order.class));
    }

    @Test
    void testUpdateOrder_Failure_NotOwner() {
        when(userServicePort.getUserId()).thenReturn(2L);
        when(employeePersistencePort.getByEmployeeId(1L)).thenReturn(employee);
        when(orderPersistencePort.getOrderByIdAndRestaurantId(anyLong(), anyLong())).thenReturn(Optional.of(order));

        DomainException exception = assertThrows(DomainException.class, () -> orderUseCase.updateOrder(order));

        assertEquals(DomainConstants.EMPLOYEE_NOT_FOUND, exception.getMessage());
        verify(orderPersistencePort, never()).updateOrder(any(Order.class));
    }

    @Test
    void testUpdateOrder_Failure_Restaurant_Not_Found() {
        employee.setRestaurant(null);
        when(userServicePort.getUserId()).thenReturn(1L);
        when(employeePersistencePort.getByEmployeeId(1L)).thenReturn(employee);

        DomainException exception = assertThrows(DomainException.class, () -> orderUseCase.updateOrder(order));

        assertEquals(DomainConstants.RESTAURANT_NOT_FOUND, exception.getMessage());
        verify(orderPersistencePort, never()).updateOrder(any(Order.class));
    }

    @Test
    void testUpdateOrder_Success_Canceled() {
        when(userServicePort.getUserId()).thenReturn(1L);
        when(employeePersistencePort.getByEmployeeId(1L)).thenReturn(employee);
        when(orderPersistencePort.getOrderByIdAndRestaurantId(anyLong(), anyLong())).thenReturn(Optional.of(order));

        Order orderUpdated = new Order();
        orderUpdated.setId(1L);
        orderUpdated.setClientId(1L);
        orderUpdated.setRestaurant(restaurant);
        orderUpdated.setDate(LocalDateTime.now());
        orderUpdated.setStatus(OrderStatusEnum.CANCELED.toString());

        assertDoesNotThrow(() -> orderUseCase.updateOrder(orderUpdated));

        verify(orderPersistencePort, times(1)).updateOrder(order);
        verify(traceabilityServicePort, times(1)).createLog(any(Log.class));
    }

    @Test
    void testUpdateOrder_Success_Ready() {
        order.setStatus(OrderStatusEnum.IN_PREPARATION.toString());
        when(userServicePort.getUserId()).thenReturn(1L);
        when(employeePersistencePort.getByEmployeeId(1L)).thenReturn(employee);
        when(orderPersistencePort.getOrderByIdAndRestaurantId(anyLong(), anyLong())).thenReturn(Optional.of(order));
        when(messageServicePort.sendCode(any())).thenReturn(true);

        Order orderUpdated = new Order();
        orderUpdated.setId(1L);
        orderUpdated.setClientId(1L);
        orderUpdated.setRestaurant(restaurant);
        orderUpdated.setDate(LocalDateTime.now());
        orderUpdated.setStatus(OrderStatusEnum.READY.toString());

        assertDoesNotThrow(() -> orderUseCase.updateOrder(orderUpdated));

        verify(orderPersistencePort, times(1)).updateOrder(order);
        verify(traceabilityServicePort, times(1)).createLog(any(Log.class));
    }

    @Test
    void testUpdateOrder_Failure_SendCode() {
        order.setStatus(OrderStatusEnum.IN_PREPARATION.toString());
        when(userServicePort.getUserId()).thenReturn(1L);
        when(employeePersistencePort.getByEmployeeId(1L)).thenReturn(employee);
        when(orderPersistencePort.getOrderByIdAndRestaurantId(anyLong(), anyLong())).thenReturn(Optional.of(order));
        when(messageServicePort.sendCode(any())).thenReturn(false);

        Order orderUpdated = new Order();
        orderUpdated.setId(1L);
        orderUpdated.setClientId(1L);
        orderUpdated.setRestaurant(restaurant);
        orderUpdated.setDate(LocalDateTime.now());
        orderUpdated.setStatus(OrderStatusEnum.READY.toString());

        DomainException exception = assertThrows(DomainException.class, () -> orderUseCase.updateOrder(orderUpdated));

        assertEquals(DomainConstants.MESSAGE_NOT_SENT, exception.getMessage());
        verify(orderPersistencePort, never()).updateOrder(any(Order.class));
    }

    @Test
    void testUpdateOrder_Success_Delivered() {
        order.setStatus(OrderStatusEnum.READY.toString());
        when(userServicePort.getUserId()).thenReturn(1L);
        when(employeePersistencePort.getByEmployeeId(1L)).thenReturn(employee);
        when(orderPersistencePort.getOrderByIdAndRestaurantId(anyLong(), anyLong())).thenReturn(Optional.of(order));
        when(messageServicePort.verifyCode(any(), any())).thenReturn(true);

        Order orderUpdated = new Order();
        orderUpdated.setId(1L);
        orderUpdated.setClientId(1L);
        orderUpdated.setRestaurant(restaurant);
        orderUpdated.setDate(LocalDateTime.now());
        orderUpdated.setStatus(OrderStatusEnum.DELIVERED.toString());

        assertDoesNotThrow(() -> orderUseCase.updateOrder(orderUpdated));

        verify(orderPersistencePort, times(1)).updateOrder(order);
        verify(traceabilityServicePort, times(1)).createLog(any(Log.class));
    }

    @Test
    void testUpdateOrder_Failure_InvalidCode() {
        order.setStatus(OrderStatusEnum.READY.toString());
        when(userServicePort.getUserId()).thenReturn(1L);
        when(employeePersistencePort.getByEmployeeId(1L)).thenReturn(employee);
        when(orderPersistencePort.getOrderByIdAndRestaurantId(anyLong(), anyLong())).thenReturn(Optional.of(order));
        when(messageServicePort.verifyCode(any(), any())).thenReturn(false);

        Order orderUpdated = new Order();
        orderUpdated.setId(1L);
        orderUpdated.setClientId(1L);
        orderUpdated.setRestaurant(restaurant);
        orderUpdated.setDate(LocalDateTime.now());
        orderUpdated.setStatus(OrderStatusEnum.DELIVERED.toString());

        DomainException exception = assertThrows(DomainException.class, () -> orderUseCase.updateOrder(orderUpdated));

        assertEquals(DomainConstants.CODE_NOT_VERIFIED, exception.getMessage());
        verify(orderPersistencePort, never()).updateOrder(any(Order.class));
    }

    @Test
    void testCancelOrder_Success() {
        when(orderPersistencePort.getOrderById(1L)).thenReturn(order);
        when(userServicePort.getUserId()).thenReturn(1L);

        assertDoesNotThrow(() -> orderUseCase.cancelOrder(1L));

        verify(orderPersistencePort, times(1)).updateOrder(order);
    }

    @Test
    void testCancelOrder_Failure_NotOwner() {
        when(orderPersistencePort.getOrderById(1L)).thenReturn(order);
        when(userServicePort.getUserId()).thenReturn(2L);

        DomainException exception = assertThrows(DomainException.class, () -> orderUseCase.cancelOrder(1L));

        assertEquals(DomainConstants.NOT_OWNER_MESSAGE, exception.getMessage());
        verify(orderPersistencePort, never()).updateOrder(any(Order.class));
    }

    @Test
    void testGetLogsByOrderId_Success() {
        when(orderPersistencePort.getOrderById(1L)).thenReturn(order);
        when(userServicePort.getUserId()).thenReturn(1L);
        when(traceabilityServicePort.getLogsByOrderId(1L)).thenReturn(List.of(log));

        List<Log> logs = orderUseCase.getLogsByOrderId(1L);

        assertNotNull(logs);
        assertFalse(logs.isEmpty());
        verify(traceabilityServicePort, times(1)).getLogsByOrderId(1L);
    }

    @Test
    void testGetLogsByOrderId_Failure_NotOwner() {
        when(orderPersistencePort.getOrderById(1L)).thenReturn(order);
        when(userServicePort.getUserId()).thenReturn(2L);

        DomainException exception = assertThrows(DomainException.class, () -> orderUseCase.getLogsByOrderId(1L));

        assertEquals(DomainConstants.NOT_OWNER_MESSAGE, exception.getMessage());
        verify(traceabilityServicePort, never()).getLogsByOrderId(anyLong());
    }

    @Test
    void testGetLogsTimeByOrderId_Success() {
        when(userServicePort.getUserId()).thenReturn(1L);
        when(restaurantPersistencePort.getRestaurantIdByOwnerId(1L)).thenReturn(restaurant);
        when(restaurantPersistencePort.isOwnerOfRestaurant(1L, 1L)).thenReturn(true);
        when(traceabilityServicePort.getLogsTimeByOrderId(1L)).thenReturn(10L);

        Long result = orderUseCase.getLogsTimeByOrderId(1L);

        assertEquals(10L, result);
        verify(traceabilityServicePort, times(1)).getLogsTimeByOrderId(1L);
    }

    @Test
    void testGetLogsTimeByOrders() {
        when(userServicePort.getUserId()).thenReturn(1L);
        when(restaurantPersistencePort.getRestaurantIdByOwnerId(1L)).thenReturn(restaurant);
        when(restaurantPersistencePort.isOwnerOfRestaurant(1L, 1L)).thenReturn(true);
        when(orderPersistencePort.getOrderIdsByRestaurantIdAndStatus(1L, OrderStatusEnum.DELIVERED)).thenReturn(List.of(1L));
        when(traceabilityServicePort.getLogsTimeByOrders(List.of(1L))).thenReturn(List.of(log));

        List<Log> logs = orderUseCase.getLogsTimeByRestaurant();

        assertNotNull(logs);
        assertFalse(logs.isEmpty());
        verify(traceabilityServicePort, times(1)).getLogsTimeByOrders(anyList());
    }

    @Test
    void testGetLogsTimeByOrders_Failure_NotOwner() {
        when(userServicePort.getUserId()).thenReturn(1L);
        when(restaurantPersistencePort.getRestaurantIdByOwnerId(1L)).thenReturn(restaurant);
        when(restaurantPersistencePort.isOwnerOfRestaurant(1L, 1L)).thenReturn(false);

        DomainException exception = assertThrows(DomainException.class, () -> orderUseCase.getLogsTimeByRestaurant());

        assertEquals(DomainConstants.NOT_OWNER_MESSAGE, exception.getMessage());
        verify(traceabilityServicePort, never()).getLogsTimeByOrders(anyList());
    }

    @Test
    void testGetAverageTimeByEmployee() {
        when(userServicePort.getUserId()).thenReturn(1L);
        when(restaurantPersistencePort.getRestaurantIdByOwnerId(1L)).thenReturn(restaurant);
        when(restaurantPersistencePort.isOwnerOfRestaurant(1L, 1L)).thenReturn(true);
        when(employeePersistencePort.getEmployeeIdsByRestaurantId(1L)).thenReturn(List.of(1L));
        when(orderPersistencePort.getOrderIdsByChefIdAndStatus(1L, OrderStatusEnum.DELIVERED)).thenReturn(List.of(1L));
        when(traceabilityServicePort.getLogsTimeByOrders(List.of(1L))).thenReturn(List.of(log));

        List<Log> logs = orderUseCase.getAverageTimeByEmployee();

        assertNotNull(logs);
        assertFalse(logs.isEmpty());
        verify(traceabilityServicePort, times(1)).getLogsTimeByOrders(anyList());
    }

    @Test
    void testGetAverageTimeByEmployee_Failure_NotOwner() {
        when(userServicePort.getUserId()).thenReturn(1L);
        when(restaurantPersistencePort.getRestaurantIdByOwnerId(1L)).thenReturn(restaurant);
        when(restaurantPersistencePort.isOwnerOfRestaurant(1L, 1L)).thenReturn(false);

        DomainException exception = assertThrows(DomainException.class, () -> orderUseCase.getAverageTimeByEmployee());

        assertEquals(DomainConstants.NOT_OWNER_MESSAGE, exception.getMessage());
        verify(traceabilityServicePort, never()).getLogsTimeByOrders(anyList());
    }

    @Test
    void testGetLogsTimeByOrderId_Failure_NotOwner() {
        when(userServicePort.getUserId()).thenReturn(1L);
        when(restaurantPersistencePort.getRestaurantIdByOwnerId(1L)).thenReturn(restaurant);
        when(restaurantPersistencePort.isOwnerOfRestaurant(1L, 1L)).thenReturn(false);

        DomainException exception = assertThrows(DomainException.class, () -> orderUseCase.getLogsTimeByOrderId(1L));

        assertEquals(DomainConstants.NOT_OWNER_MESSAGE, exception.getMessage());
        verify(traceabilityServicePort, never()).getLogsTimeByOrderId(anyLong());
    }

    @Test
    void testGetLogsByOrderId_Failure_OrderNotFound() {
        when(orderPersistencePort.getOrderById(1L)).thenReturn(null);

        DomainException exception = assertThrows(DomainException.class, () -> orderUseCase.getLogsByOrderId(1L));

        assertEquals(DomainConstants.ORDER_NOT_FOUND, exception.getMessage());
        verify(traceabilityServicePort, never()).getLogsByOrderId(anyLong());
    }

    @Test
    void testCancelOrder_Failure_OrderNotFound() {
        when(orderPersistencePort.getOrderById(1L)).thenReturn(null);

        DomainException exception = assertThrows(DomainException.class, () -> orderUseCase.cancelOrder(1L));

        assertEquals(DomainConstants.ORDER_NOT_FOUND, exception.getMessage());
        verify(orderPersistencePort, never()).updateOrder(any(Order.class));
    }

    @Test
    void testCancelOrder_Failure_NotCancelable() {
        order.setStatus(OrderStatusEnum.DELIVERED.toString());
        when(orderPersistencePort.getOrderById(1L)).thenReturn(order);
        when(userServicePort.getUserId()).thenReturn(1L);

        DomainException exception = assertThrows(DomainException.class, () -> orderUseCase.cancelOrder(1L));

        assertEquals(DomainConstants.NOT_CANCELABLE_ORDER, exception.getMessage());
        verify(orderPersistencePort, never()).updateOrder(any(Order.class));
    }

    @Test
    void testGetOrders_Success() {
        when(userServicePort.getUserId()).thenReturn(1L);
        when(employeePersistencePort.getByEmployeeId(1L)).thenReturn(employee);
        when(orderPersistencePort.getOrders(1, 10, "ASC", OrderStatusEnum.PENDING, 1L)).thenReturn(new PaginatedModel<Order>(List.of(order), 1, 1, 1));

        PaginatedModel<Order> result = orderUseCase.getOrders(1, 10, "ASC", OrderStatusEnum.PENDING);

        assertNotNull(result);
        verify(orderPersistencePort, times(1)).getOrders(1, 10, "ASC", OrderStatusEnum.PENDING, 1L);
    }

    @Test
    void testGetOrders_Failure_Restaurant_Not_Found() {
        when(userServicePort.getUserId()).thenReturn(1L);
        when(employeePersistencePort.getByEmployeeId(1L)).thenReturn(null);

        DomainException exception = assertThrows(DomainException.class, () -> orderUseCase.getOrders(1, 10, "ASC", OrderStatusEnum.PENDING));

        assertEquals(DomainConstants.RESTAURANT_NOT_FOUND, exception.getMessage());
        verify(orderPersistencePort, never()).getOrders(anyInt(), anyInt(), any(), any(), anyLong());
    }

    @Test
    void testGetAllOrder_Success() {
        when(orderPersistencePort.getAllOrder()).thenReturn(List.of(order));

        List<Order> result = orderUseCase.getAllOrder();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(orderPersistencePort, times(1)).getAllOrder();
    }

    @Test
    void testGetAllOrder_Failure() {
        when(orderPersistencePort.getAllOrder()).thenReturn(List.of());

        DomainException exception = assertThrows(DomainException.class, () -> orderUseCase.getAllOrder());

        assertEquals(DomainConstants.ORDER_NOT_FOUND, exception.getMessage());
        verify(orderPersistencePort, times(1)).getAllOrder();
    }

    @Test
    void testDeleteOrder_Success() {
        when(orderPersistencePort.getOrderById(1L)).thenReturn(order);

        assertDoesNotThrow(() -> orderUseCase.deleteOrder(1L));

        verify(orderPersistencePort, times(1)).deleteOrder(1L);
    }


}
