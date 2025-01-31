package com.pragma.powerup.application.dto;

import com.pragma.powerup.application.constants.ValidationConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Object that represents the request to create a new dish")
public class DishRequest {

    @NotBlank(message = ValidationConstants.NAME_DISH_REQUIRED)
    @Size(max = 100, message = ValidationConstants.NAME_DISH_MAX_LENGTH)
    @Schema(description = "Name of the dish", example = "Grilled Chicken")
    private String name;

    @NotNull(message = ValidationConstants.CATEGORY_ID_REQUIRED)
    @Schema(description = "Category ID to which the dish belongs", example = "1")
    private Long categoryId;

    @NotBlank(message = ValidationConstants.DESCRIPTION_REQUIRED)
    @Size(max = 255, message = ValidationConstants.DESCRIPTION_MAX_LENGTH)
    @Schema(description = "Description of the dish", example = "A delicious grilled chicken with herbs and spices")
    private String description;

    @NotNull(message = ValidationConstants.PRICE_REQUIRED)
    @Min(value = 1, message = ValidationConstants.PRICE_POSITIVE)
    @Schema(description = "Price of the dish", example = "15000")
    private Integer price;

    @NotBlank(message = ValidationConstants.IMAGE_URL_REQUIRED)
    @Schema(description = "URL of the dish image", example = "https://example.com/image.jpg")
    private String imageUrl;

    @NotNull(message = ValidationConstants.RESTAURANT_ID_REQUIRED)
    @Schema(description = "Restaurant ID to which the dish belongs", example = "1")
    private Long restaurantId;
}
