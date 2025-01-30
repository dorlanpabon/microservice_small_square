package com.pragma.powerup.infrastructure.output.jpa.mapper;

import com.pragma.powerup.domain.model.Category;
import com.pragma.powerup.infrastructure.output.jpa.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CategoryEntityMapper {

    CategoryEntity toEntity(Category category);

    Category toCategory(CategoryEntity categoryEntity);

    List<Category> toCategoryList(List<CategoryEntity> categoryEntityList);

}
