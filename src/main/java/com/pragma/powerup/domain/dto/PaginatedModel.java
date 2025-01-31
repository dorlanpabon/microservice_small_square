package com.pragma.powerup.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PaginatedModel<T> {
    private List<T> content;
    private int currentPage;
    private int totalPages;
    private long totalElements;

    public PaginatedModel(List<T> content, int currentPage, int totalPages, long totalElements) {
        this.content = content;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }
}