package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.CategoryRequest;
import com.pragma.powerup.application.dto.CategoryResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ICategoryHandler {

    Page<CategoryResponse> getCategorys(int page, int size, String sortDirection);

    void saveCategoryInCategory(CategoryRequest categoryRequest);

    List<CategoryResponse> getCategoryFromCategory();

    CategoryResponse getCategoryFromCategory(Long categoryId);

    void updateCategoryInCategory(CategoryRequest categoryRequest);

    void deleteCategoryFromCategory(Long categoryId);

}
