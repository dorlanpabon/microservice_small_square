package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.CategoryRequest;
import com.pragma.powerup.application.dto.CategoryResponse;
import com.pragma.powerup.application.mapper.CategoryRequestMapper;
import com.pragma.powerup.application.mapper.CategoryResponseMapper;
import com.pragma.powerup.domain.api.ICategoryServicePort;
import com.pragma.powerup.domain.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryHandler implements ICategoryHandler {

    private final CategoryRequestMapper categoryRequestMapper;
    private final CategoryResponseMapper categoryResponseMapper;
    private final ICategoryServicePort categoryServicePort;

    @Override
    public Page<CategoryResponse> getCategorys(int page, int size, String sortDirection) {
        return categoryServicePort.getCategorys(page, size, sortDirection)
                .map(categoryResponseMapper::toCategoryResponse);
    }

    @Override
    public void saveCategoryInCategory(CategoryRequest categoryRequest) {
        Category category = categoryRequestMapper.toCategory(categoryRequest);
        categoryServicePort.saveCategory(category);
    }

    @Override
    public List<CategoryResponse> getCategoryFromCategory() {
        return categoryServicePort.getAllCategory().stream()
                .map(categoryResponseMapper::toCategoryResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse getCategoryFromCategory(Long categoryId) {
        Category category = categoryServicePort.getCategoryById(categoryId);
        return categoryResponseMapper.toCategoryResponse(category);
    }

    @Override
    public void updateCategoryInCategory(CategoryRequest categoryRequest) {
        Category category = categoryRequestMapper.toCategory(categoryRequest);
        categoryServicePort.updateCategory(category);
    }

    @Override
    public void deleteCategoryFromCategory(Long categoryId) {
        categoryServicePort.deleteCategory(categoryId);
    }

}
