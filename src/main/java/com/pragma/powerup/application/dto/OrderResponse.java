package com.pragma.powerup.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrderResponse {

    private Long orderId;

    private Long orderClientId;

    private LocalDateTime orderDate;

    private String orderStatus;

//    private RestaurantResponse orderRestaurant;

    private Long orderChefId;

//    private List<OrderDish> orderOrderDishes;

}
