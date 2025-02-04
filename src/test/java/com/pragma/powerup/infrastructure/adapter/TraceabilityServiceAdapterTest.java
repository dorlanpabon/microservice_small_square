package com.pragma.powerup.infrastructure.adapter;

import com.pragma.powerup.application.dto.LogResponse;
import com.pragma.powerup.application.dto.LogTimeResponse;
import com.pragma.powerup.application.mapper.LogResponseMapper;
import com.pragma.powerup.domain.model.Log;
import com.pragma.powerup.infrastructure.client.TraceabilityFeignClient;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TraceabilityServiceAdapterTest {

    @Mock
    private TraceabilityFeignClient traceabilityFeignClient;

    @Mock
    private LogResponseMapper logResponseMapper;

    @InjectMocks
    private TraceabilityServiceAdapter traceabilityServiceAdapter;

    private Log log;
    private LogResponse logResponse;
    private LogTimeResponse logTimeResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        log = new Log();
        log.setOrderId(1L);
        log.setClientId(1L);
        log.setRestaurantId(1L);
        log.setTime(120L);

        logResponse = new LogResponse();
        logResponse.setLogOrderId(1L);
        logResponse.setLogClientId(1L);
        logResponse.setLogRestaurantId(1L);

        logTimeResponse = new LogTimeResponse();
        logTimeResponse.setTime(120L);
    }

    @Test
    void testCreateLog_Success() {
        doNothing().when(traceabilityFeignClient).createLog(any(Log.class));

        assertDoesNotThrow(() -> traceabilityServiceAdapter.createLog(log));

        verify(traceabilityFeignClient, times(1)).createLog(log);
    }

    @Test
    void testCreateLog_Failure() {
        doThrow(FeignException.class).when(traceabilityFeignClient).createLog(any(Log.class));

        assertDoesNotThrow(() -> traceabilityServiceAdapter.createLog(log));

        verify(traceabilityFeignClient, times(1)).createLog(log);
    }

    @Test
    void testGetLogs_Success() {
        doNothing().when(traceabilityFeignClient).getLogs(1L, 1L);

        assertDoesNotThrow(() -> traceabilityServiceAdapter.getLogs(1L, 1L));

        verify(traceabilityFeignClient, times(1)).getLogs(1L, 1L);
    }

    @Test
    void testGetLogs_Failure() {
        doThrow(FeignException.class).when(traceabilityFeignClient).getLogs(anyLong(), anyLong());

        assertDoesNotThrow(() -> traceabilityServiceAdapter.getLogs(1L, 1L));

        verify(traceabilityFeignClient, times(1)).getLogs(1L, 1L);
    }

    @Test
    void testGetLogsByOrderId_Success() {
        when(traceabilityFeignClient.getLogsByOrderId(1L)).thenReturn(Collections.singletonList(logResponse));
        when(logResponseMapper.toLog(any(LogResponse.class))).thenReturn(log);

        List<Log> logs = traceabilityServiceAdapter.getLogsByOrderId(1L);

        assertNotNull(logs);
        assertFalse(logs.isEmpty());
        verify(traceabilityFeignClient, times(1)).getLogsByOrderId(1L);
        verify(logResponseMapper, times(1)).toLog(any(LogResponse.class));
    }

    @Test
    void testGetLogsByOrderId_Failure() {
        when(traceabilityFeignClient.getLogsByOrderId(anyLong())).thenThrow(FeignException.class);

        List<Log> logs = traceabilityServiceAdapter.getLogsByOrderId(1L);

        assertNotNull(logs);
        assertTrue(logs.isEmpty());
    }

    @Test
    void testGetLogsByRestaurant_Success() {
        doNothing().when(traceabilityFeignClient).getLogsByRestaurant(1L);

        assertDoesNotThrow(() -> traceabilityServiceAdapter.getLogsByRestaurant(1L));

        verify(traceabilityFeignClient, times(1)).getLogsByRestaurant(1L);
    }

    @Test
    void testGetLogsByRestaurant_Failure() {
        doThrow(FeignException.class).when(traceabilityFeignClient).getLogsByRestaurant(anyLong());

        assertDoesNotThrow(() -> traceabilityServiceAdapter.getLogsByRestaurant(1L));

        verify(traceabilityFeignClient, times(1)).getLogsByRestaurant(1L);
    }

    @Test
    void testGetLogsTimeByOrderId_Success() {
        when(traceabilityFeignClient.getLogsTimeByOrderId(1L)).thenReturn(logTimeResponse);

        Long result = traceabilityServiceAdapter.getLogsTimeByOrderId(1L);

        assertEquals(120L, result);
        verify(traceabilityFeignClient, times(1)).getLogsTimeByOrderId(1L);
    }

    @Test
    void testGetLogsTimeByOrderId_Failure() {
        when(traceabilityFeignClient.getLogsTimeByOrderId(anyLong())).thenThrow(FeignException.class);

        Long result = traceabilityServiceAdapter.getLogsTimeByOrderId(1L);

        assertEquals(0L, result);
    }

    @Test
    void testGetLogsTimeByOrders_Success() {
        when(traceabilityFeignClient.getLogsTimeByOrders(anyList())).thenReturn(Collections.singletonList(logTimeResponse));
        when(logResponseMapper.toLog(any(LogTimeResponse.class))).thenReturn(log);

        List<Log> logs = traceabilityServiceAdapter.getLogsTimeByOrders(Collections.singletonList(1L));

        assertNotNull(logs);
        assertFalse(logs.isEmpty());
        verify(traceabilityFeignClient, times(1)).getLogsTimeByOrders(anyList());
    }

    @Test
    void testGetLogsTimeByOrders_Failure() {
        when(traceabilityFeignClient.getLogsTimeByOrders(anyList())).thenThrow(FeignException.class);

        List<Log> logs = traceabilityServiceAdapter.getLogsTimeByOrders(Collections.singletonList(1L));

        assertNotNull(logs);
        assertTrue(logs.isEmpty());
    }
}
