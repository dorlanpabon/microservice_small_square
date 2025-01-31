package com.pragma.powerup.application.dto;

import com.pragma.powerup.domain.model.OrderDish;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DishResponse {

    private Long dishId;

    private String dishName;

    private String dishDescription;

    private Double dishPrice;

    private String dishImageUrl;

    private Boolean dishActive;

}
