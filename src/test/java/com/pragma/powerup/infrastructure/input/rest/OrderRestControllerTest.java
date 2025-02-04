package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.*;
import com.pragma.powerup.application.handler.IOrderHandler;
import com.pragma.powerup.domain.enums.OrderStatusEnum;
import com.pragma.powerup.infrastructure.security.JwtFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderRestController.class)
@AutoConfigureMockMvc(addFilters = false)
class OrderRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtFilter jwtFilter;

    @MockBean
    private IOrderHandler orderHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetOrders() throws Exception {
        List<OrderResponse> orders = Collections.singletonList(new OrderResponse());
        PaginatedResponse<OrderResponse> mockResponse = new PaginatedResponse<>(orders, 0, 1, 1);

        when(orderHandler.getOrders(anyInt(), anyInt(), anyString(), any(OrderStatusEnum.class)))
                .thenReturn(mockResponse);

        mockMvc.perform(get("/orders")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortDirection", "asc")
                        .param("status", "PENDING"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());

        verify(orderHandler, times(1)).getOrders(anyInt(), anyInt(), anyString(), any(OrderStatusEnum.class));
    }

    @Test
    void testSaveOrderInOrder() throws Exception {
        doNothing().when(orderHandler).saveOrderInOrder(any(OrderRequest.class));

        mockMvc.perform(post("/orders/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"clientId\":1,\"restaurantId\":2,\"orderDishes\":[]}"))
                .andExpect(status().isCreated());

        verify(orderHandler, times(1)).saveOrderInOrder(any(OrderRequest.class));
    }

    @Test
    void testUpdateOrderInOrder() throws Exception {
        doNothing().when(orderHandler).updateOrderInOrder(any(OrderAssignRequest.class));

        mockMvc.perform(put("/orders/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"orderId\":1,\"chefId\":2}"))
                .andExpect(status().isNoContent());

        verify(orderHandler, times(1)).updateOrderInOrder(any(OrderAssignRequest.class));
    }

    @Test
    void testCancelOrder() throws Exception {
        doNothing().when(orderHandler).cancelOrder(anyLong());

        mockMvc.perform(put("/orders/cancel/1"))
                .andExpect(status().isNoContent());

        verify(orderHandler, times(1)).cancelOrder(anyLong());
    }

    @Test
    void testGetLogsByOrderId() throws Exception {
        List<LogResponse> logs = Collections.singletonList(new LogResponse());
        when(orderHandler.getLogsByOrderId(anyLong())).thenReturn(logs);

        mockMvc.perform(get("/orders/logs/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        verify(orderHandler, times(1)).getLogsByOrderId(anyLong());
    }

    @Test
    void testGetLogsTimeByOrderId() throws Exception {
        LogTimeResponse logTime = new LogTimeResponse();
        when(orderHandler.getLogsTimeByOrderId(anyLong())).thenReturn(logTime);

        mockMvc.perform(get("/orders/order/1/time"))
                .andExpect(status().isOk());

        verify(orderHandler, times(1)).getLogsTimeByOrderId(anyLong());
    }

    @Test
    void testGetLogsTimeByOrders() throws Exception {
        List<LogTimeResponse> logTimes = Collections.singletonList(new LogTimeResponse());
        when(orderHandler.getLogsTimeByRestaurant()).thenReturn(logTimes);

        mockMvc.perform(get("/orders/order/time"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        verify(orderHandler, times(1)).getLogsTimeByRestaurant();
    }

    @Test
    void testGetAverageTimeByEmployee() throws Exception {
        List<LogEmployeeResponse> logTimes = Collections.singletonList(new LogEmployeeResponse());
        when(orderHandler.getAverageTimeByEmployee()).thenReturn(logTimes);

        mockMvc.perform(get("/orders/order/average"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        verify(orderHandler, times(1)).getAverageTimeByEmployee();
    }
}
