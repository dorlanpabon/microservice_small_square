package com.pragma.powerup.application.dto;

import com.pragma.powerup.domain.model.OrderDish;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderResponse {

    private Long orderId;

    private Long orderClientId;

    private LocalDateTime orderDate;

    private String orderStatus;

    private RestaurantResponse orderRestaurant;

    private Long orderChefId;

    private List<OrderDish> orderOrderDishes;

}
