package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.DishRequest;
import com.pragma.powerup.application.dto.DishResponse;
import com.pragma.powerup.application.handler.DishHandler;
import com.pragma.powerup.application.handler.IDishHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Dish API")
@RestController
@RequestMapping("/dishs")
@RequiredArgsConstructor
public class DishRestController {

    private final IDishHandler dishHandler;

    @Operation(summary = "Get all dishs", description = "Retrieve a list of dishs")
    @GetMapping
    public ResponseEntity<Page<DishResponse>> getDishs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        Page<DishResponse> dishs = dishHandler.getDishs(page, size, sortDirection);
        return ResponseEntity.ok(dishs);
    }

    @Operation(summary = "Create a new dish", description = "Add a new dish to the system")
    @PostMapping("/")
    public ResponseEntity<Void> saveDishInDish(@RequestBody DishRequest dishRequest) {
        dishHandler.saveDishInDish(dishRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Retrieve Dish list", description = "Get all dishs")
    @GetMapping("/")
    public ResponseEntity<List<DishResponse>> getDishFromDish() {
        return ResponseEntity.ok(dishHandler.getDishFromDish());
    }

    @Operation(summary = "Retrieve Dish by ID", description = "Get a single dish by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<DishResponse> getDishFromDish(@PathVariable(name = "id") Long dishId) {
        return ResponseEntity.ok(dishHandler.getDishFromDish(dishId));
    }

    @Operation(summary = "Update dish", description = "Update an existing dish in the system")
    @PutMapping("/")
    public ResponseEntity<Void> updateDishInDish(@RequestBody DishRequest dishRequest) {
        dishHandler.updateDishInDish(dishRequest);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete dish by ID", description = "Remove an existing dish from the system")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDishFromDish(@PathVariable Long dishId) {
        dishHandler.deleteDishFromDish(dishId);
        return ResponseEntity.noContent().build();
    }

}
