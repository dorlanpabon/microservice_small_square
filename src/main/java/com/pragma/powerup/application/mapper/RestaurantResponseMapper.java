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
    @Mapping(target = "restaurantName", source = "name")
    @Mapping(target = "restaurantLogoUrl", source = "logoUrl")
    RestaurantResponse toRestaurantResponse(Restaurant entity);
}
