package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.CategoryRequest;
import com.pragma.powerup.domain.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CategoryRequestMapper {
//    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
//    @Mapping(target = "dishes", source = "dishes")
    Category toCategory(CategoryRequest categoryRequest);
}
