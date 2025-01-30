package com.pragma.powerup.application.dto;

import com.pragma.powerup.domain.model.OrderDish;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Locale.Category;

@Getter
@Setter
public class DishResponse {

    private Long dishId;

    private String dishName;

    private CategoryResponse dishCategory;

    private String dishDescription;

    private Double dishPrice;

    private RestaurantResponse dishRestaurant;

    private String dishImageUrl;

    private Boolean dishActive;

    private List<OrderDish> dishOrderDishes;

}
