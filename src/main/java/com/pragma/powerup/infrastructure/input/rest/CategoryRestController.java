package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.CategoryRequest;
import com.pragma.powerup.application.dto.CategoryResponse;
import com.pragma.powerup.application.handler.ICategoryHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Category API")
@RestController
@RequestMapping("/categorys")
@RequiredArgsConstructor
public class CategoryRestController {

    private final ICategoryHandler categoryHandler;

    @Operation(summary = "Get all categorys", description = "Retrieve a list of categorys")
    @GetMapping
    public ResponseEntity<Page<CategoryResponse>> getCategorys(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        Page<CategoryResponse> categorys = categoryHandler.getCategorys(page, size, sortDirection);
        return ResponseEntity.ok(categorys);
    }

    @Operation(summary = "Create a new category", description = "Add a new category to the system")
    @PostMapping("/")
    public ResponseEntity<Void> saveCategoryInCategory(@RequestBody CategoryRequest categoryRequest) {
        categoryHandler.saveCategoryInCategory(categoryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Retrieve Category list", description = "Get all categorys")
    @GetMapping("/")
    public ResponseEntity<List<CategoryResponse>> getCategoryFromCategory() {
        return ResponseEntity.ok(categoryHandler.getCategoryFromCategory());
    }

    @Operation(summary = "Retrieve Category by ID", description = "Get a single category by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryFromCategory(@PathVariable(name = "id") Long categoryId) {
        return ResponseEntity.ok(categoryHandler.getCategoryFromCategory(categoryId));
    }

    @Operation(summary = "Update category", description = "Update an existing category in the system")
    @PutMapping("/")
    public ResponseEntity<Void> updateCategoryInCategory(@RequestBody CategoryRequest categoryRequest) {
        categoryHandler.updateCategoryInCategory(categoryRequest);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete category by ID", description = "Remove an existing category from the system")
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategoryFromCategory(@PathVariable Long categoryId) {
        categoryHandler.deleteCategoryFromCategory(categoryId);
        return ResponseEntity.noContent().build();
    }

}
