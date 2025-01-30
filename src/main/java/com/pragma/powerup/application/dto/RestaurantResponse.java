package com.pragma.powerup.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantResponse {

    private Long restaurantId;

    private String restaurantName;

    private String restaurantAddress;

    private Long restaurantOwnerId;

    private String restaurantPhone;

    private String restaurantLogoUrl;

    private String restaurantNit;

}
