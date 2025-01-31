package com.pragma.powerup.application.dto;

import com.pragma.powerup.infrastructure.output.jpa.entity.RestaurantEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeResponse {

    private Long employeeId;

    private RestaurantEntity employeeRestaurant;

    private Long employeeEmployeeId;

    private Long employeeOwnerId;

}
