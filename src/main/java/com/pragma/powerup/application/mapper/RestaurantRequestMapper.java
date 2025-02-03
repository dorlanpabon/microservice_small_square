package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.RestaurantRequest;
import com.pragma.powerup.domain.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface RestaurantRequestMapper {
    @Mapping(target = "name", source = "name")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "ownerId", source = "ownerId")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "logoUrl", source = "logoUrl")
    @Mapping(target = "nit", source = "nit")
    Restaurant toRestaurant(RestaurantRequest restaurantRequest);
}
