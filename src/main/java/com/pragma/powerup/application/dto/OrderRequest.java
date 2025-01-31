package com.pragma.powerup.application.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class OrderRequest {

    private Long restaurantId;

    private List<OrderDishRequest> orderDishes;

}

