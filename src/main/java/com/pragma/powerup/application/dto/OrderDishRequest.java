package com.pragma.powerup.application.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class OrderDishRequest {

    private Long id;

    private OrderRequest order;

    private DishRequest dish;

    private Integer quantity;

}
