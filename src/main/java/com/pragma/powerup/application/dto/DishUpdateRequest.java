package com.pragma.powerup.application.dto;

import com.pragma.powerup.application.constants.ValidationConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Object that represents the request to update a dish")
public class DishUpdateRequest {

    @NotBlank(message = ValidationConstants.DESCRIPTION_REQUIRED)
    @Size(max = 255, message = ValidationConstants.DESCRIPTION_MAX_LENGTH)
    @Schema(description = "Updated description of the dish", example = "Updated description of grilled chicken")
    private String description;

    @NotNull(message = ValidationConstants.PRICE_REQUIRED)
    @Min(value = 1, message = ValidationConstants.PRICE_POSITIVE)
    @Schema(description = "Updated price of the dish", example = "16000")
    private Double price;
}
