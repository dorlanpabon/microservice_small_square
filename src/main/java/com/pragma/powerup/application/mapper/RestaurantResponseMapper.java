package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.RestaurantResponse;
import com.pragma.powerup.domain.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface RestaurantResponseMapper {
    @Mapping(target = "restaurantId", source = "id")
    @Mapping(target = "restaurantName", source = "name")
    @Mapping(target = "restaurantAddress", source = "address")
    @Mapping(target = "restaurantOwnerId", source = "ownerId")
    @Mapping(target = "restaurantPhone", source = "phone")
    @Mapping(target = "restaurantLogoUrl", source = "logoUrl")
    @Mapping(target = "restaurantNit", source = "nit")
    RestaurantResponse toRestaurantResponse(Restaurant entity);
}
