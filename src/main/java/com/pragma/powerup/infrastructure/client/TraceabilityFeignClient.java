package com.pragma.powerup.infrastructure.client;

import com.pragma.powerup.application.dto.LogResponse;
import com.pragma.powerup.application.dto.LogTimeResponse;
import com.pragma.powerup.domain.model.Log;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "traceability-service", url = "http://localhost:8084/")
public interface TraceabilityFeignClient {

    @PostMapping("/logs/")
    void createLog(@RequestBody Log log);

    @GetMapping("/logs/order/{orderId}")
    List<LogResponse> getLogsByOrderId(@PathVariable("orderId") Long orderId);

    @GetMapping("/logs/")
    void getLogs(@RequestParam("clientId") Long clientId, @RequestParam("orderId") Long orderId);

    @GetMapping("/logs/restaurant")
    void getLogsByRestaurant(@RequestParam("restaurantId") Long restaurantId);

    @GetMapping("/logs/order/{orderId}/time")
    LogTimeResponse getLogsTimeByOrderId(@PathVariable("orderId") Long orderId);

    //getLogsTimeByOrders get
    @GetMapping("/logs/order/time")
    List<LogTimeResponse> getLogsTimeByOrders(@RequestParam("orderIds") List<Long> orderIds);

}
