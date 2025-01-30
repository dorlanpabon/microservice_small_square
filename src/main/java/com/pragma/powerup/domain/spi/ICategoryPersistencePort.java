package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface ICategoryPersistencePort {

    void saveCategory(Category category);

    List<Category> getAllCategory();

    Category getCategoryById(Long categoryId);

    void updateCategory(Category category);

    void deleteCategory(Long categoryId);

    Page<Category> getCategorys(int page, int size, boolean ascending);

    Page<Category> getCategorys(int pageNumber, int pageSize, String sortDirection);

    Page<Category> getCategorys(PageRequest pageRequest);

    Page<Category> findAll(Pageable pageable);
}
