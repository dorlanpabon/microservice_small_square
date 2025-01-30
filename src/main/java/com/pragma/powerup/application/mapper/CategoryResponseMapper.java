package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.CategoryResponse;
import com.pragma.powerup.domain.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CategoryResponseMapper {
    @Mapping(target = "categoryId", source = "id")
    @Mapping(target = "categoryName", source = "name")
    @Mapping(target = "categoryDescription", source = "description")
    @Mapping(target = "categoryDishes", source = "dishes")
    CategoryResponse toCategoryResponse(Category entity);
}
