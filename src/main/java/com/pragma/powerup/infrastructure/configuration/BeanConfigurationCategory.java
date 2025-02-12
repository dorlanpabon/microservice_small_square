package com.pragma.powerup.infrastructure.configuration;

import com.pragma.powerup.domain.api.ICategoryServicePort;
import com.pragma.powerup.domain.model.Category;
import com.pragma.powerup.domain.spi.ICategoryPersistencePort;
import com.pragma.powerup.domain.usecase.CategoryUseCase;
import com.pragma.powerup.infrastructure.output.jpa.adapter.CategoryJpaAdapter;
import com.pragma.powerup.infrastructure.output.jpa.mapper.CategoryEntityMapper;
import com.pragma.powerup.infrastructure.output.jpa.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;

@Configuration
@RequiredArgsConstructor
public class BeanConfigurationCategory {

    private final ICategoryRepository categoryRepository;
    private final CategoryEntityMapper categoryEntityMapper;

    @Bean
    public ICategoryPersistencePort categoryPersistencePort() {
        return new CategoryJpaAdapter(categoryRepository, categoryEntityMapper);
    }

    @Bean
    public ICategoryServicePort categoryServicePort() {
        return new CategoryUseCase(categoryPersistencePort()) {
            @Override
            public Page<Category> getCategorys(int page, int size, boolean ascending) {
                return null;
            }
        };
    }
}
