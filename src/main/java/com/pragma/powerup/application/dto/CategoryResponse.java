package com.pragma.powerup.application.dto;

import com.pragma.powerup.domain.model.Dish;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoryResponse {

    private Long categoryId;

    private String categoryName;

    private String categoryDescription;

    private List<Dish> categoryDishes;

}
