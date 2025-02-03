package com.pragma.powerup.infrastructure.output.jpa.adapter;

import com.pragma.powerup.domain.model.Category;
import com.pragma.powerup.domain.spi.ICategoryPersistencePort;
import com.pragma.powerup.infrastructure.output.jpa.entity.CategoryEntity;
import com.pragma.powerup.infrastructure.output.jpa.mapper.CategoryEntityMapper;
import com.pragma.powerup.infrastructure.output.jpa.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@RequiredArgsConstructor
public class CategoryJpaAdapter implements ICategoryPersistencePort {

    private final ICategoryRepository categoryRepository;

    private final CategoryEntityMapper categoryEntityMapper;

    @Override
    public void saveCategory(Category category) {
        categoryRepository.save(categoryEntityMapper.toEntity(category));
    }

    @Override
    public List<Category> getAllCategory() {
        List<CategoryEntity> entityList = categoryRepository.findAll();
        if (entityList.isEmpty()) {
            throw new IllegalArgumentException("No Categorys found");
        }
        return categoryEntityMapper.toCategoryList(entityList);
    }

    @Override
    public Category getCategoryById(Long categoryId) {
        return categoryEntityMapper.toCategory(categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found")));
    }

    @Override
    public void updateCategory(Category category) {
        categoryRepository.save(categoryEntityMapper.toEntity(category));
    }

    @Override
    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    @Override
    public Page<Category> getCategorys(PageRequest pageRequest) {
        Page<CategoryEntity> entityPage = categoryRepository.findAll(pageRequest);
        if (entityPage.isEmpty()) {
            throw new IllegalArgumentException("No Categorys found");
        }
        return entityPage.map(categoryEntityMapper::toCategory);
    }

    @Override
    public Page<Category> getCategorys(int page, int size, boolean ascending) {
        Pageable pageable = PageRequest.of(page, size, ascending ? Sort.by("id").ascending() : Sort.by("id").descending());
        return categoryRepository.findAll(pageable).map(categoryEntityMapper::toCategory);
    }

    @Override
    public Page<Category> getCategorys(int pageNumber, int pageSize, String sortDirection) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortDirection.equals("asc") ? Sort.by("id").ascending() : Sort.by("id").descending());
        return categoryRepository.findAll(pageable).map(categoryEntityMapper::toCategory);
    }

    @Override
    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable).map(categoryEntityMapper::toCategory);
    }
}
