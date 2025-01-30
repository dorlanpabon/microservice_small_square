package com.pragma.powerup.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDishResponse {

    private Long orderdishId;

    private OrderResponse orderdishOrder;

    private DishResponse orderdishDish;

    private Integer orderdishQuantity;

}
