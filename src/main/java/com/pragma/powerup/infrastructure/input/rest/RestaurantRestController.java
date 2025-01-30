package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.RestaurantRequest;
import com.pragma.powerup.application.handler.IRestaurantHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Restaurant API")
@RestController
@RequestMapping("/restaurants")
@RequiredArgsConstructor
public class
RestaurantRestController {

    private final IRestaurantHandler restaurantHandler;

    @Operation(summary = "Create a new restaurant", description = "Add a new restaurant to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Restaurant created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PostMapping("/")
    public ResponseEntity<Void> saveRestaurantInRestaurant(@RequestBody RestaurantRequest restaurantRequest) {
        restaurantHandler.saveRestaurantInRestaurant(restaurantRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
