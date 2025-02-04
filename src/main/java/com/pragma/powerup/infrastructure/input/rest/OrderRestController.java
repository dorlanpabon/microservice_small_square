package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.LogEmployeeResponse;
import com.pragma.powerup.application.dto.LogResponse;
import com.pragma.powerup.application.dto.LogTimeResponse;
import com.pragma.powerup.application.dto.OrderAssignRequest;
import com.pragma.powerup.application.dto.OrderRequest;
import com.pragma.powerup.application.dto.OrderResponse;
import com.pragma.powerup.application.dto.PaginatedResponse;
import com.pragma.powerup.application.handler.IOrderHandler;
import com.pragma.powerup.domain.enums.OrderStatusEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Order API")
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderRestController {

    private final IOrderHandler orderHandler;

    @Operation(summary = "Get all orders", description = "Retrieve a list of orders")
    @GetMapping
    public ResponseEntity<PaginatedResponse<OrderResponse>> getOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "asc") String sortDirection,
            @RequestParam(defaultValue = "PENDING") OrderStatusEnum status
    ) {
        PaginatedResponse<OrderResponse> orders = orderHandler.getOrders(page, size, sortDirection, status);
        return ResponseEntity.ok(orders);
    }

    @Operation(summary = "Create a new order", description = "Add a new order to the system")
    @PostMapping("/")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Void> saveOrderInOrder(@RequestBody OrderRequest orderRequest) {
        orderHandler.saveOrderInOrder(orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Update order", description = "Update an existing order in the system")
    @PutMapping("/")
    public ResponseEntity<Void> updateOrderInOrder(@RequestBody OrderAssignRequest orderAssignRequest) {
        orderHandler.updateOrderInOrder(orderAssignRequest);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Cancel order", description = "Cancel an existing order in the system")
    @PutMapping("/cancel/{orderId}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId) {
        orderHandler.cancelOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get all logs by order id", description = "Retrieve a list of logs by order id")
    @GetMapping("/logs/{orderId}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<LogResponse>> getLogsByOrderId(@PathVariable Long orderId) {
        List<LogResponse> logs = orderHandler.getLogsByOrderId(orderId);
        return ResponseEntity.ok(logs);
    }

    @Operation(summary = "Get time in INIT and END by order ID", description = "Retrieve the time in INIT and END by order ID")
    @GetMapping("/order/{orderId}/time")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<LogTimeResponse> getLogsTimeByOrderId(@PathVariable Long orderId) {
        LogTimeResponse logTime = orderHandler.getLogsTimeByOrderId(orderId);
        return ResponseEntity.ok(logTime);
    }

    @Operation(summary = "Get time in INIT and END by orders of the restaurant", description = "Retrieve the time in INIT and END by orders of the restaurant")
    @GetMapping("/order/time")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<List<LogTimeResponse>> getLogsTimeByOrders() {
        List<LogTimeResponse> logTimes = orderHandler.getLogsTimeByRestaurant();
        return ResponseEntity.ok(logTimes);
    }

    // Get average time by employee
    @Operation(summary = "Get average time by employee", description = "Retrieve the average time by employee")
    @GetMapping("/order/average")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<List<LogEmployeeResponse>> getAverageTimeByEmployee() {
        List<LogEmployeeResponse> logTimes = orderHandler.getAverageTimeByEmployee();
        return ResponseEntity.ok(logTimes);
    }
}
