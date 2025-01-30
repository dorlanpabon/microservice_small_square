package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.Category;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ICategoryServicePort {

    void saveCategory(Category category);

    List<Category> getAllCategory();

    Category getCategoryById(Long categoryId);

    void updateCategory(Category category);

    void deleteCategory(Long categoryId);

    Page<Category> getCategorys(int page, int size, boolean ascending);

    Page<Category> getCategorys(int pageNumber, int pageSize, String sortDirection);
}
