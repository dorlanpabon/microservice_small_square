package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.OrderDishRequest;
import com.pragma.powerup.application.dto.OrderDishResponse;
import com.pragma.powerup.application.handler.IOrderDishHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "OrderDish API")
@RestController
@RequestMapping("/orderdishs")
@RequiredArgsConstructor
public class OrderDishRestController {

    private final IOrderDishHandler orderdishHandler;

    @Operation(summary = "Get all orderdishs", description = "Retrieve a list of orderdishs")
    @GetMapping
    public ResponseEntity<Page<OrderDishResponse>> getOrderDishs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        Page<OrderDishResponse> orderdishs = orderdishHandler.getOrderDishs(page, size, sortDirection);
        return ResponseEntity.ok(orderdishs);
    }

    @Operation(summary = "Create a new orderdish", description = "Add a new orderdish to the system")
    @PostMapping("/")
    public ResponseEntity<Void> saveOrderDishInOrderDish(@RequestBody OrderDishRequest orderdishRequest) {
        orderdishHandler.saveOrderDishInOrderDish(orderdishRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Retrieve OrderDish list", description = "Get all orderdishs")
    @GetMapping("/")
    public ResponseEntity<List<OrderDishResponse>> getOrderDishFromOrderDish() {
        return ResponseEntity.ok(orderdishHandler.getOrderDishFromOrderDish());
    }

    @Operation(summary = "Retrieve OrderDish by ID", description = "Get a single orderdish by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<OrderDishResponse> getOrderDishFromOrderDish(@PathVariable(name = "id") Long orderdishId) {
        return ResponseEntity.ok(orderdishHandler.getOrderDishFromOrderDish(orderdishId));
    }

    @Operation(summary = "Update orderdish", description = "Update an existing orderdish in the system")
    @PutMapping("/")
    public ResponseEntity<Void> updateOrderDishInOrderDish(@RequestBody OrderDishRequest orderdishRequest) {
        orderdishHandler.updateOrderDishInOrderDish(orderdishRequest);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete orderdish by ID", description = "Remove an existing orderdish from the system")
    @DeleteMapping("/{orderdishId}")
    public ResponseEntity<Void> deleteOrderDishFromOrderDish(@PathVariable Long orderdishId) {
        orderdishHandler.deleteOrderDishFromOrderDish(orderdishId);
        return ResponseEntity.noContent().build();
    }

}
