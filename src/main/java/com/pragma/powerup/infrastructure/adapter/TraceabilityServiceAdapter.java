package com.pragma.powerup.infrastructure.adapter;

import com.pragma.powerup.application.dto.LogResponse;
import com.pragma.powerup.application.dto.LogTimeResponse;
import com.pragma.powerup.application.mapper.LogResponseMapper;
import com.pragma.powerup.domain.api.ITraceabilityServicePort;
import com.pragma.powerup.domain.model.Log;
import com.pragma.powerup.infrastructure.client.TraceabilityFeignClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TraceabilityServiceAdapter  implements ITraceabilityServicePort {

    private final TraceabilityFeignClient traceabilityFeignClient;
    private final LogResponseMapper logResponseMapper;

    @Override
    public void createLog(Log log) {
        try {
            traceabilityFeignClient.createLog(log);
        } catch (FeignException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getLogs(Long clientId, Long orderId) {
        try {
            traceabilityFeignClient.getLogs(clientId, orderId);
        } catch (FeignException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Log> getLogsByOrderId(Long orderId) {
        try {
            List<LogResponse> logResponses = traceabilityFeignClient.getLogsByOrderId(orderId);
            return logResponses.stream().map(logResponseMapper::toLog).collect(Collectors.toList());
        } catch (FeignException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public void getLogsByRestaurant(Long restaurantId) {
        try {
            traceabilityFeignClient.getLogsByRestaurant(restaurantId);
        } catch (FeignException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Long getLogsTimeByOrderId(Long orderId) {
        try {
            LogTimeResponse logTime = traceabilityFeignClient.getLogsTimeByOrderId(orderId);
            return logTime.getTime();
        } catch (FeignException e) {
            e.printStackTrace();
            return 0L;
        }
    }

    //getLogsTimeByOrders
    @Override
    public List<Log> getLogsTimeByOrders(List<Long> orderIds) {
        try {
            List<LogTimeResponse> logTimeResponses = traceabilityFeignClient.getLogsTimeByOrders(orderIds);
            return logTimeResponses.stream().map(logResponseMapper::toLog).collect(Collectors.toList());
        } catch (FeignException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
