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
@Schema(description = "Object that represents the request to create a new restaurant")
public class RestaurantRequest {

    @NotBlank(message = ValidationConstants.NAME_REQUIRED)
    @Pattern(regexp = ValidationConstants.NAME_REGEX, message = ValidationConstants.NAME_INVALID_FORMAT)
    @Schema(description = "Restaurant name (can contain numbers but not be only numbers)", example = "Gourmet 360")
    private String name;

    @NotBlank(message = ValidationConstants.ADDRESS_REQUIRED)
    @Schema(description = "Restaurant address", example = "Calle 123 #45-67, Bogotá")
    private String address;

    @NotNull(message = ValidationConstants.OWNER_ID_REQUIRED)
    @Schema(description = "Owner ID (must correspond to a user with the owner role)", example = "1")
    private Long ownerId;

    @NotBlank(message = ValidationConstants.PHONE_REQUIRED)
    @Size(max = 13, message = ValidationConstants.PHONE_MAX_LENGTH)
    @Pattern(regexp = ValidationConstants.PHONE_REGEX, message = ValidationConstants.PHONE_INVALID_FORMAT)
    @Schema(description = "Phone number of the restaurant, must include country code", example = "+573005698325")
    private String phone;

    @NotBlank(message = ValidationConstants.LOGO_URL_REQUIRED)
    @Schema(description = "URL of the restaurant logo", example = "https://example.com/logo.jpg")
    private String logoUrl;

    @NotBlank(message = ValidationConstants.NIT_REQUIRED)
    @Pattern(regexp = ValidationConstants.NUMERIC_REGEX, message = ValidationConstants.NIT_INVALID_FORMAT)
    @Schema(description = "Tax Identification Number (NIT) of the restaurant, must be numeric", example = "9001234567")
    private String nit;
}
