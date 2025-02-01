package com.pragma.powerup.infrastructure.input.rest;

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
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "Retrieve Order list", description = "Get all orders")
    @GetMapping("/")
    public ResponseEntity<List<OrderResponse>> getOrderFromOrder() {
        return ResponseEntity.ok(orderHandler.getOrderFromOrder());
    }

    @Operation(summary = "Retrieve Order by ID", description = "Get a single order by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderFromOrder(@PathVariable(name = "id") Long orderId) {
        return ResponseEntity.ok(orderHandler.getOrderFromOrder(orderId));
    }

    @Operation(summary = "Update order", description = "Update an existing order in the system")
    @PutMapping("/")
    public ResponseEntity<Void> updateOrderInOrder(@RequestBody OrderAssignRequest orderAssignRequest) {
        orderHandler.updateOrderInOrder(orderAssignRequest);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete order by ID", description = "Remove an existing order from the system")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderFromOrder(@PathVariable Long orderId) {
        orderHandler.deleteOrderFromOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Cancel order", description = "Cancel an existing order in the system")
    @PutMapping("/cancel/{id}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId) {
        orderHandler.cancelOrder(orderId);
        return ResponseEntity.noContent().build();
    }

}
