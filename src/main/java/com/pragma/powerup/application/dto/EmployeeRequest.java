package com.pragma.powerup.application.dto;

import com.pragma.powerup.infrastructure.output.jpa.entity.RestaurantEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class EmployeeRequest {

    private Long employeeId;

    private Long ownerId;

}
