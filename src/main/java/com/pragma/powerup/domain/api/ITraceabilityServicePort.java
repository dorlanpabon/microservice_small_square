package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.Log;

import java.util.List;

public interface ITraceabilityServicePort {

    void createLog(Log log);

    void getLogs(Long clientId, Long orderId);

    List<Log> getLogsByOrderId(Long orderId);

    void getLogsByRestaurant(Long restaurantId);

    Long getLogsTimeByOrderId(Long orderId);

    //getLogsTimeByOrders
    List<Log> getLogsTimeByOrders(List<Long> orderIds);
}
