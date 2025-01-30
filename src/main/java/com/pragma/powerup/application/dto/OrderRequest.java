package com.pragma.powerup.application.dto;

import com.pragma.powerup.domain.model.OrderDish;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
@Setter
public class OrderRequest {

    private Long id;

    private Long clientId;

    private LocalDateTime date;

    private String status;

    private RestaurantRequest restaurant;

    private Long chefId;

    private List<OrderDish> orderDishes;

}
