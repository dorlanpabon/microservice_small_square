package com.pragma.powerup.application.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class OrderDishRequest {

    private Long dishId;

    private Integer quantity;

}
