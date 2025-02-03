package com.pragma.powerup.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class LogResponse {

    private String logId;

    private String logType;

    private Long logClientId;

    private Long logOrderId;

    private Long logRestaurantId;

    private LocalDateTime logTimestamp;

}
