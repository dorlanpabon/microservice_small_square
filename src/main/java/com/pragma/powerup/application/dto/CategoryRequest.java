package com.pragma.powerup.application.dto;

import com.pragma.powerup.domain.model.Dish;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class CategoryRequest {

    private Long id;

    private String name;

    private String description;

    private List<Dish> dishes;

}
