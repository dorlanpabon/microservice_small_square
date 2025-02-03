package com.pragma.powerup.application.dto;

import lombok.Getter;
import lombok.Setter;

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
