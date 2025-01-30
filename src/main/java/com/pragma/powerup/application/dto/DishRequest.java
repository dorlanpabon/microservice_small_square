package com.pragma.powerup.application.dto;

import com.pragma.powerup.domain.model.OrderDish;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class DishRequest {

    private Long id;

    private String name;

    private CategoryRequest category;

    private String description;

    private Double price;

    private RestaurantRequest restaurant;

    private String imageUrl;

    private Boolean active;

    private List<OrderDish> orderDishes;

}
