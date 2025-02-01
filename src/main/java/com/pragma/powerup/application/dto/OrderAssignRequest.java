package com.pragma.powerup.application.dto;

import com.pragma.powerup.domain.enums.OrderStatusEnum;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class OrderAssignRequest {

    private Long orderId;

    private OrderStatusEnum status;

    private String code;

}

